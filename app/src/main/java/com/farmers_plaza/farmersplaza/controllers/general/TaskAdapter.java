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

public class TaskAdapter extends
        RecyclerView.Adapter<TaskAdapter.FarmerViewHolder> {
    ArrayList<String> cropList;
    Context context;

    public TaskAdapter (ArrayList<String> cropList, Context context) {
        this.cropList = cropList;
        this.context = context;
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

    }

    @Override
    public int getItemCount() {
        return cropList.size();
    }

    public static class FarmerViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textViewTaskName;
        Button btnEnterExpense;

        FarmerViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view_browse_crop);
            textViewTaskName = (TextView) itemView.findViewById(R.id.text_view_task_name);
            btnEnterExpense = (Button) itemView.findViewById(R.id.btn_enter_expense);
        }
    }
}
