package com.farmers_plaza.farmersplaza.farmer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.MainActivity;
import com.farmers_plaza.farmersplaza.dal.FarmerDao;
import com.farmers_plaza.farmersplaza.models.Farmer;
import com.farmers_plaza.farmersplaza.models.Person;
import com.farmers_plaza.farmersplaza.service.FarmerService;
import com.parse.ParseUser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HomeScreenActivity extends AppCompatActivity {

    private Intent                              intent;
    private Farmer                              farmer;
    private TextView                            name;
    private TextView                            signOut;
    private ImageButton                         profile;
    private ImageButton                         browseFarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp();
        clickSignOut();
        clickProfile();
        clickBrowseFarm();
    }

    public void setUp(){

        name = (TextView)findViewById(R.id.text_view_name);
        signOut = (TextView)findViewById(R.id.btnSignOut);
        try {
            farmer = (Farmer)runThread("retrieveFarmer", ParseUser.getCurrentUser()).get();
        }catch(Exception e){
            e.printStackTrace();
        }//try catch
        name.setText(farmer.getName());

    }//end setUp

    public void clickSignOut(){
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                showIntent(MainActivity.class);
            }
        });
    }//end clickSignOut

    public void clickProfile(){
        profile = (ImageButton)findViewById(R.id.btn_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIntent(FarmerProfileActivity.class);
            }//end onClick
        });
    }

    public void clickBrowseFarm(){
        browseFarm = (ImageButton)findViewById(R.id.btnBrowseFarm);
        browseFarm.setOnClickListener(new View.OnClickListener() {
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
