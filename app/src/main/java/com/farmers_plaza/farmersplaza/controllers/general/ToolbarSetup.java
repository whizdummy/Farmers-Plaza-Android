package com.farmers_plaza.farmersplaza.controllers.general;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ToolbarSetup {
    private Toolbar toolbar;
    private Context context;
    private AppCompatActivity activity;

    public ToolbarSetup(View view, Context context, AppCompatActivity activity) {
        this.toolbar = (Toolbar) view;
        this.context = context;
        this.activity = activity;
    }

    public void initialize(String title, int color) {
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(color);
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }
}
