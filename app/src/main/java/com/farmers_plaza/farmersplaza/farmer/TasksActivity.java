package com.farmers_plaza.farmersplaza.farmer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.farmers_plaza.farmersplaza.R;

public class TasksActivity extends AppCompatActivity {
    Button btnViewAllTask;


    String farmName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        btnViewAllTask = (Button) findViewById(R.id.btn_view_all_task);
        farmName = getIntent().getExtras().getString("farm");
        btnViewAllTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TasksActivity.this, ViewAllTaskActivity.class);
                intent.putExtra("farm", farmName);
                startActivity(intent);
            }
        });
    }
}
