package com.farmers_plaza.farmersplaza.farmer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.farmers_plaza.farmersplaza.R;
import com.github.mikephil.charting.charts.LineChart;

public class IncomeActivity extends AppCompatActivity {
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        setUp();
    }

    private void setUp() {
       lineChart = (LineChart) findViewById(R.id.line_chart_income);
//        May mga lalagay pa rito
    }
}
