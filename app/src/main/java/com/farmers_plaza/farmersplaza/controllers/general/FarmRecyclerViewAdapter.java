package com.farmers_plaza.farmersplaza.controllers.general;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.models.Farm;
import com.farmers_plaza.farmersplaza.models.FarmAdapter;

import java.util.List;

public class FarmRecyclerViewAdapter extends
        RecyclerView.Adapter<FarmRecyclerViewAdapter.FarmerViewHolder> {
    List<FarmAdapter> farmList;
    Context context;

    public FarmRecyclerViewAdapter(List<FarmAdapter> farmList, Context context) {
        this.farmList = farmList;
        this.context = context;
    }

    @Override
    public FarmerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_farm_list, parent, false);
        return (new FarmerViewHolder(view));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final FarmerViewHolder holder, final int position) {
//        holder.textViewStudentName.setText(farmList.get(position).getStrName());
//        holder.textViewContactNum.setText(farmList.get(position).getStrContactNum());
//        holder.textViewTotalFee.setText(farmList.get(position).getStrRequestedHr());
        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cardView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return farmList.size();
    }

    public static class FarmerViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textViewStudentName;
        TextView textViewTotalFee;
        TextView textViewContactNum;
        Button btnReject;

        FarmerViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view_farm);
//            cardView = (CardView)itemView.findViewById(R.id.card_view_student_request);
//            textViewStudentName = (TextView) itemView.findViewById(R.id.text_view_student_name_request);
//            textViewTotalFee = (TextView)itemView.findViewById(R.id.text_view_total_fee);
//            textViewContactNum = (TextView) itemView.findViewById(R.id.text_view_contact_num);
//            btnReject = (Button) itemView.findViewById(R.id.btnReject);
        }
    }
}
