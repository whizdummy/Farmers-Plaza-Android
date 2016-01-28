package com.farmers_plaza.farmersplaza.farmer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.MainActivity;
import com.farmers_plaza.farmersplaza.models.Farmer;
import com.farmers_plaza.farmersplaza.service.FarmerService;
import com.parse.ParseUser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HomeScreenActivity extends AppCompatActivity {

    private Farmer                      farmer;
    private Intent                      intent;
    private TextView                    txtViewName;
    private TextView                    txtViewSignOut;
    private ImageButton                 imgBtnMap;
    private ImageButton                 imgBtnCrops;
    private ImageButton                 imgBtnIncome;
    private ImageButton                 imgBtnFarm;
    private ImageButton                 imgBtnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_main);
        setUp();
        clickMap();
        clickCrops();
        clickIncome();
        clickFarm();
        clickSignOut();
        clickProfile();
        clickBrowseFarm();
    }

    private void clickMap() {
        imgBtnMap = (ImageButton) findViewById(R.id.img_btn_maps);
        imgBtnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIntent(FarmLocationActivity.class);
            }
        });
    }

    private void clickIncome() {
        imgBtnIncome = (ImageButton) findViewById(R.id.img_btn_income);
        imgBtnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIntent(IncomeActivity.class);
            }
        });
    }

    private void clickCrops() {
        imgBtnCrops = (ImageButton) findViewById(R.id.img_btn_crops);
        imgBtnCrops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIntent(CropBrowseActivity.class);
            }
        });
    }

    public void setUp() {
        txtViewName = (TextView)findViewById(R.id.text_view_name_farmer);

        try {
            farmer = (Farmer)runThread("retrieveFarmer", ParseUser.getCurrentUser()).get();
        }catch(Exception e){
            e.printStackTrace();
        }//try catch
        txtViewName.setText(farmer.getName());
    }//end setUp

    private void clickFarm() {
        imgBtnFarm = (ImageButton) findViewById(R.id.img_btn_farm);
        imgBtnFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIntent(FarmBrowseActivity.class);
            }
        });
    } // end clickFarm

    public void clickSignOut() {
        txtViewSignOut = (TextView)findViewById(R.id.btnSignOut);

        txtViewSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                showIntent(MainActivity.class);
            }
        });
    }//end clickSignOut

    public void clickProfile(){
        imgBtnProfile = (ImageButton)findViewById(R.id.img_btn_profile);
        imgBtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIntent(FarmerProfileActivity.class);
            }//end onClick
        });
    }

    public void clickBrowseFarm(){
        imgBtnFarm = (ImageButton) findViewById(R.id.btn_browse_farm);
        imgBtnFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIntent(FarmActivity.class);
            }
        });

    }

    private void showIntent(Class className) {
        intent = new Intent(HomeScreenActivity.this, className);
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
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}//end HomeScreenActivity
