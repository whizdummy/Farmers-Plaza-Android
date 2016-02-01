package com.farmers_plaza.farmersplaza.controllers.general;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.farmer.CropDetailsActivity;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;

public class CropsRecyclerView extends
        RecyclerView.Adapter<CropsRecyclerView.FarmerViewHolder> {
    ArrayList<String> cropList;
    Context context;
    String farmName;
    ParseObject farm;

    public CropsRecyclerView (ArrayList<String> cropList, Context context, String farmName) {
        this.cropList = cropList;
        this.context = context;
        this.farmName = farmName;
    }

    @Override
    public FarmerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_browse_crop_result, parent, false);
        return (new FarmerViewHolder(view));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final FarmerViewHolder holder, final int position) {
        holder.textViewFarmName.setText(cropList.get(position));
        holder.btnCropDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CropDetailsActivity.class);
                intent.putExtra("cropName", cropList.get(position));
                context.startActivity(intent);
            }
        });
        holder.btnCropSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveDataToDb(cropList.get(position));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void saveDataToDb(String cropName) throws ParseException {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Crop");
        ParseObject cropObject;
        query.whereEqualTo("cropName", cropName);
        cropObject = query.getFirst();
        ParseQuery<ParseObject>farmQuery = new ParseQuery<ParseObject>("Farm");
        farmQuery.whereEqualTo("farmName", farmName);
        farm = farmQuery.getFirst();
        farm.put("cropPlanted", cropObject);
        farm.put("cropPlantedDate", new Date());
        farm.save();
        System.out.println("Successfully planted the crop.");
    }

    @Override
    public int getItemCount() {
        return cropList.size();
    }

    public static class FarmerViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textViewFarmName;
        Button btnCropDetails;
        Button btnCropSave;

        FarmerViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view_browse_crop);
            textViewFarmName = (TextView) itemView.findViewById(R.id.text_view_crop);
            btnCropDetails = (Button) itemView.findViewById(R.id.btn_view_crop_details);
            btnCropSave = (Button) itemView.findViewById(R.id.btn_save_crop);
        }
    }
}