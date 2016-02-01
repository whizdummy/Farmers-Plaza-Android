package com.farmers_plaza.farmersplaza.farmer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.ToolbarSetup;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class IncomeActivity extends AppCompatActivity implements View.OnClickListener {
    BarChart chart;
    BarData data;
    ToolbarSetup toolbarSetup;
    EditText editTxtactualIncome;
    Button btnCheckResult;
    static double CROP_PRICE = 50000;
    static double ESTIMATED_CROPS = 10;
    static double TOTAL_EXPENSE = 5000;
    static double dblEstimatedIncome;
//    List<Entry> estimatedIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        setUp();
        clickCheckResult();
        setUpToolbar();
        setUpChart();
    }

    private void setUpChart() {
        chart = (BarChart) findViewById(R.id.bar_chart_income);

        data = new BarData(getXAxisValues(), getDataSet(0f, 0f));
        chart.setData(data);
        chart.setDescription(null);
        chart.invalidate();
    }

    private ArrayList<IBarDataSet> getDataSet(float estimatedIncome, float actualIncome) {
        ArrayList<IBarDataSet> dataSets;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry barEntryEstimatedIncome = new BarEntry(estimatedIncome, 0);
        valueSet1.add(barEntryEstimatedIncome);

        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        BarEntry barEntryActualIncome = new BarEntry(actualIncome, 0);
        valueSet2.add(barEntryActualIncome);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Estimated Income");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Actual Income");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("INCOME SUMMARY (in PhP)");
        return xAxis;
    }


    private void clickCheckResult() {
        btnCheckResult.setOnClickListener(this);
    }

    private void setUpToolbar() {
        toolbarSetup = new ToolbarSetup(findViewById(R.id.toolbar), this, this);
        toolbarSetup.initialize(getString(R.string.income_toolbar_title), Color.WHITE);
    }

    private void setUp() {
        chart = (BarChart) findViewById(R.id.bar_chart_income);
//        label = new ArrayList<>();
//        estimatedIncome = new ArrayList<>();
        editTxtactualIncome = (EditText) findViewById(R.id.edit_text_actual_income);
        btnCheckResult = (Button) findViewById(R.id.btn_check_result);

//        May mga lalagay pa rito
    }

    @Override
    public void onClick(View v) {
        if(!TextUtils.isEmpty(editTxtactualIncome.getText().toString())) {
            dblEstimatedIncome = (CROP_PRICE * ESTIMATED_CROPS) - TOTAL_EXPENSE;
            data = new BarData(getXAxisValues(), getDataSet((float) dblEstimatedIncome,
                    Float.parseFloat(editTxtactualIncome.getText().toString())));
            chart.setData(data);
            chart.animateXY(2000, 2000);
            chart.invalidate();
        } else {
            Toast.makeText(this, "You didn't input anything!", Toast.LENGTH_LONG)
            .show();
        }
    }
}
