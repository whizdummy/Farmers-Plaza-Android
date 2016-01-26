package com.farmers_plaza.farmersplaza.farm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.farmers_plaza.farmersplaza.business.FarmerBusiness;
import com.farmers_plaza.farmersplaza.farmer.HomeScreenActivity;
import com.farmers_plaza.farmersplaza.service.FarmService;
import com.farmers_plaza.farmersplaza.service.FarmerService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FarmAddActivity extends AppCompatActivity{

    private FarmService                         farmService;
    private Intent                              intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void showIntent(Class className) {
        intent = new Intent(FarmAddActivity.this, className);
        startActivity(intent);
        finish();
    }

    private Future<Object> runThread(String methodName, Object param){
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<Object> futureObject = es.submit(new FarmerService(methodName, param));
        return futureObject;
    }//end runThread

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            showIntent(HomeScreenActivity.class);
        }
        return super.onKeyDown(keyCode, event);
    }

}
