package com.farmers_plaza.farmersplaza.controllers.general;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    ArrayList<String> taskList;
    Context context;
    String farmName;
    ParseObject task;
    ParseObject farm;

    public TaskAdapter (ArrayList<String> taskList, Context context, String farmName) {
        this.taskList = taskList;
        this.context = context;
        this.farmName = farmName;
    }

    @Override
    public FarmerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_all_tasks, parent, false);
        return (new FarmerViewHolder(view));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final FarmerViewHolder holder, final int position) {
        holder.textViewTaskName.setText(taskList.get(position));
        holder.btnEnterExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double dblExpense = Double.parseDouble(holder.tfExpense.getText().toString());
                ParseQuery queryTask = new ParseQuery("Task");
                queryTask.whereEqualTo("taskDesc", taskList.get(position));
                try{
                    task = queryTask.getFirst();
                }catch(Exception e){
                    e.printStackTrace();
                }
                ParseQuery queryFarm = new ParseQuery("Farm");
                queryFarm.whereEqualTo("farmName", farmName);
                try{
                    farm = queryFarm.getFirst();
                }catch(Exception e){
                    e.printStackTrace();
                }
                ParseObject taskExpense = new ParseObject("TaskExpense");
                taskExpense.put("farm", farm);
                taskExpense.put("task", task);
                taskExpense.put("expense", dblExpense);
                try{
                    taskExpense.save();
                    System.out.println("Success in saving the expense.");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class FarmerViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView textViewTaskName;
        Button btnEnterExpense;
        EditText tfExpense;

        FarmerViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view_all_task);
            textViewTaskName = (TextView) itemView.findViewById(R.id.text_view_task_name);
            tfExpense = (EditText)itemView.findViewById(R.id.editText_expense);
            btnEnterExpense = (Button) itemView.findViewById(R.id.btn_enter_expense);
        }
    }
}
