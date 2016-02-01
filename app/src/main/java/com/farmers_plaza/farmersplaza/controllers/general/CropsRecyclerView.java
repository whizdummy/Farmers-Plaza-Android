package com.farmers_plaza.farmersplaza.controllers.general;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.models.CropAdapter;
import com.farmers_plaza.farmersplaza.models.FarmAdapter;

import java.util.List;

public class CropsRecyclerView extends
        RecyclerView.Adapter<CropsRecyclerView.FarmerViewHolder> {
    List<CropAdapter> cropList;
    Context context;

    public CropsRecyclerView (List<CropAdapter> farmList, Context context) {
        this.cropList = farmList;
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
        holder.textViewFarmName.setText(cropList.get(position).getCropName());
    }

    @Override
    public int getItemCount() {
        return cropList.size();
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