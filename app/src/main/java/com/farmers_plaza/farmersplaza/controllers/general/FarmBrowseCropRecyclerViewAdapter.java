package com.farmers_plaza.farmersplaza.controllers.general;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.models.FarmAdapter;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FarmBrowseCropRecyclerViewAdapter extends
        RecyclerView.Adapter<FarmBrowseCropRecyclerViewAdapter.FarmerViewHolder> {
    List<FarmAdapter> farmList;
    Context context;
    ParseObject farm;
    ParseObject soilTest;
    List<ParseObject> compatibleCrops;

    public FarmBrowseCropRecyclerViewAdapter(List<FarmAdapter> farmList, Context context) {
        this.farmList = farmList;
        this.context = context;
    }

    @Override
    public FarmerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_browse_crop_farm, parent, false);
        return (new FarmerViewHolder(view));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final FarmerViewHolder holder, final int position) {
        holder.textViewFarmName.setText(farmList.get(position).getFarmName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String farmName = farmList.get(position).getFarmName();
                System.out.println(farmName);
                ParseQuery queryFarm = new ParseQuery("Farm");
                queryFarm.whereEqualTo("farmName", farmName);
                try{
                    farm = queryFarm.getFirst();
                }catch(Exception e){
                    e.printStackTrace();
                }
                ParseQuery querySoilTest = new ParseQuery("SoilTest");
                querySoilTest.whereEqualTo("farm", farm);
                querySoilTest.orderByDescending("createdAt");
                try {
                    soilTest = querySoilTest.getFirst();
                }catch(Exception e){
                    e.printStackTrace();
                }
                compatibleCrops = new ArrayList<ParseObject>();
                ParseQuery queryCrop = new ParseQuery("Crop");
//                queryCrop.whereGreaterThanOrEqualTo("minPh", soilTest.get("soilPh"));
//                queryCrop.whereLessThanOrEqualTo("maxPh", soilTest.get("soilPh"));
                try {
                    List<ParseObject> crops = queryCrop.find();
                    for (ParseObject crop:crops) {
                        if ((Double.parseDouble((String)crop.get("minPh")) <= Double.parseDouble((String)soilTest.get("soilPH")))
                                && (Double.parseDouble((String)crop.get("maxPh")) >= Double.parseDouble((String)soilTest.get("soilPH")))){
                            System.out.println("Compatible: " +crop.get("cropName"));
                            compatibleCrops.add(crop);
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }



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

        FarmerViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view_farm_browse_crop);
            textViewFarmName = (TextView) itemView.findViewById(R.id.text_view_farm_name_browse_crop);
        }
    }
}