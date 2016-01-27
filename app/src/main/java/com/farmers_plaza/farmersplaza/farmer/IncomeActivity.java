package com.farmers_plaza.farmersplaza.farmer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.ToolbarSetup;
import com.github.mikephil.charting.charts.LineChart;

public class IncomeActivity extends AppCompatActivity {
    LineChart lineChart;
    ToolbarSetup toolbarSetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        setUp();
        setUpToolbar();
    }

    private void setUpToolbar() {
        toolbarSetup = new ToolbarSetup(findViewById(R.id.toolbar), this, this);
        toolbarSetup.initialize(getString(R.string.income_toolbar_title), Color.WHITE);
    }

    private void setUp() {
       lineChart = (LineChart) findViewById(R.id.line_chart_income);
//        May mga lalagay pa rito
    }
}
