package com.farmers_plaza.farmersplaza.farmer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.ToolbarSetup;

public class FarmBrowseActivity extends AppCompatActivity {
    ToolbarSetup toolbarSetup;
    Button btnAddFarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_farms);
        setUp();
        addFarmClick();
    }

    private void addFarmClick() {
        btnAddFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FarmBrowseActivity.this, AddFarmActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUp() {
        btnAddFarm = (Button) findViewById(R.id.btn_add_farm);
        setupToolbar();
    }

    private void setupToolbar() {
        toolbarSetup = new ToolbarSetup(findViewById(R.id.toolbar_farm), this, this);
        toolbarSetup.initialize(getString(R.string.browse_farm_toolbar_title), Color.WHITE);
    }
}
