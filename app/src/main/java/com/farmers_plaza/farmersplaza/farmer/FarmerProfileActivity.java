package com.farmers_plaza.farmersplaza.farmer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.farmers_plaza.farmersplaza.R;

public class FarmerProfileActivity extends AppCompatActivity{

    private Intent                                  intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
