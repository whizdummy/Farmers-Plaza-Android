package com.farmers_plaza.farmersplaza.controllers.general;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.farmer.TasksActivity;
import com.farmers_plaza.farmersplaza.models.FarmAdapter;

import java.util.List;

public class FarmListExpenseRecyclerViewAdapter extends
        RecyclerView.Adapter<FarmListExpenseRecyclerViewAdapter.FarmerViewHolder> {
    List<FarmAdapter> farmList;
    Context context;

    public FarmListExpenseRecyclerViewAdapter(List<FarmAdapter> farmList, Context context) {
        this.farmList = farmList;
        this.context = context;
    }

    @Override
    public FarmerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_farm_list_expense, parent, false);
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
                Intent intent = new Intent(v.getContext(), TasksActivity.class);
                intent.putExtra("farm", farmList.get(position).getFarmName());
                context.startActivity(intent);
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
            cardView = (CardView) itemView.findViewById(R.id.card_view_farm_list_expense);
            textViewFarmName = (TextView) itemView.findViewById(R.id.text_view_farm_list_expense);
        }
    }
}
