package com.farmers_plaza.farmersplaza.farmer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.ToolbarSetup;

public class AddFarmActivity extends AppCompatActivity {
    private ToolbarSetup toolbarSetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farm);
        setupToolbar();
    }

    private void setupToolbar() {
        toolbarSetup = new ToolbarSetup(findViewById(R.id.toolbar), this, this);
        toolbarSetup.initialize(getString(R.string.add_farm_toolbar_title), Color.WHITE);
    }
}
