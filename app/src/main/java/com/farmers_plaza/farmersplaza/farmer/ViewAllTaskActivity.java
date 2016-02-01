package com.farmers_plaza.farmersplaza.farmer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.CropsRecyclerView;
import com.farmers_plaza.farmersplaza.controllers.general.TaskAdapter;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ViewAllTaskActivity extends AppCompatActivity {
    ArrayList<String> taskName = new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    TaskAdapter taskAdapter;
    String farmName;
    ParseObject farm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_task);
        farmName = getIntent().getExtras().getString("farm");

        ParseQuery queryFarm = new ParseQuery("Farm");
        queryFarm.whereEqualTo("farmName", farmName);
        try{
            farm = queryFarm.getFirst();
        }catch(Exception e){
            e.printStackTrace();
        }

        ParseQuery queryTask = new ParseQuery("Task");
        queryTask.whereEqualTo("cropName", farm.get("cropPlanted"));
        queryTask.addAscendingOrder("taskStart");
        List<ParseObject>listTask = null;
        try{
            listTask = queryTask.find();
        }catch(Exception e){
            e.printStackTrace();
        }
        for (ParseObject task:listTask) {
            taskName.add((String)task.get("taskDesc"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpView();
    }

    private void setUpView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyler_view_tasks);
        linearLayoutManager = new LinearLayoutManager(this);
        taskAdapter = new TaskAdapter(taskName, this, farmName);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(taskAdapter);
    }

}
