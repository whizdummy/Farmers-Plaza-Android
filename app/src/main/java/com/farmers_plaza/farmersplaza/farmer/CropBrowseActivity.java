package com.farmers_plaza.farmersplaza.farmer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.ToolbarSetup;

public class CropBrowseActivity extends AppCompatActivity {
    ToolbarSetup toolbarSetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_crops);
        setupToolbar();
    }

    private void setupToolbar() {
        toolbarSetup = new ToolbarSetup(findViewById(R.id.toolbar), this, this);
        toolbarSetup.initialize(getString(R.string.browse_crops_toolbar_title), Color.WHITE);
    }
}
