package com.farmers_plaza.farmersplaza.controllers.general;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.models.FarmAdapter;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class SoilAnalysisRecyclerViewAdapter extends
        RecyclerView.Adapter<SoilAnalysisRecyclerViewAdapter.FarmerViewHolder> {
    List<FarmAdapter> farmList;
    Context context;
String agriId;
    String farmId;
    BluetoothDevice bluetoothDevice;
    BluetoothConnector bluetoothConnector;

    public SoilAnalysisRecyclerViewAdapter(List<FarmAdapter> farmList, Context context, BluetoothDevice bluetoothDevice) {
        this.farmList = farmList;
        this.context = context;
        this.bluetoothDevice = bluetoothDevice;
    }

    @Override
    public FarmerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_farm_list_analysis, parent, false);
        return (new FarmerViewHolder(view));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(FarmerViewHolder holder, final int position) {
        holder.textViewFarmName.setText(String.format("Farm name: %s", farmList.get(position).getFarmName()));
        holder.textViewFarmOwner.setText(String.format("Owner: %s", farmList.get(position).getOwner()));

        holder.btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryAgriculturistId();
                farmId = farmList.get(position).getObjectId();
                bluetoothConnector = new BluetoothConnector(v.getContext());
                bluetoothConnector.execute(bluetoothDevice);
                saveToDB();
            }
        });
    }

    private void saveToDB() {
        ParseObject parseObject = ParseObject.create("SoilTest");
        parseObject.put("tester", ParseObject.createWithoutData("Agriculturist", agriId));
        parseObject.put("farm", ParseObject.createWithoutData("Farm", farmId));
        parseObject.put("soilPH", bluetoothConnector.getConvertedPH());
        parseObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null) {
                    Log.e("ERROR", e.getMessage());
                }
            }
        });
    }

    private void queryAgriculturistId() {
        ParseQuery<ParseObject> agriQuery = ParseQuery.getQuery("Agriculturist");
        agriQuery.whereEqualTo("user", ParseUser.getCurrentUser());
        agriQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                agriId = object.getObjectId();
            }
        });
    }

    @Override
    public int getItemCount() {
        return farmList.size();
    }

    public static class FarmerViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textViewFarmName;
        TextView textViewFarmOwner;
        Button btnTest;

        FarmerViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view_farm_list_analysis);
            textViewFarmName = (TextView) itemView.findViewById(R.id.text_view_farm_name_analysis);
            textViewFarmOwner = (TextView) itemView.findViewById(R.id.text_view_farm_owner_analysis);
            btnTest = (Button) itemView.findViewById(R.id.btn_test);
        }
    }
}
