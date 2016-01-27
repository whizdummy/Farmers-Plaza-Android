package com.farmers_plaza.farmersplaza.farmer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.ToolbarSetup;

public class FarmerProfileActivity extends AppCompatActivity{

    Intent intent;
    ToolbarSetup toolbarSetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setUpToolbar();
    }

    private void setUpToolbar() {
        toolbarSetup = new ToolbarSetup(findViewById(R.id.toolbar), this, this);
        toolbarSetup.initialize(getString(R.string.profile_toolbar_title), Color.WHITE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            showIntent(HomeScreenActivity.class);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showIntent(Class className) {
        intent = new Intent(FarmerProfileActivity.this, className);
        startActivity(intent);
        finish();
    }

}//end FarmerProfileActivity
