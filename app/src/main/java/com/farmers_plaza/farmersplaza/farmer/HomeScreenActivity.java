package com.farmers_plaza.farmersplaza.farmer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUp();
        clickSignOut();
    }

    public void setUp(){

        name = (TextView)findViewById(R.id.text_view_name);
        signOut = (TextView)findViewById(R.id.btnSignOut);
        ExecutorService es = Executors.newSingleThreadExecutor();
        try {
            Future<Object> futureFarmer = es.submit(new FarmerService("retrieveFarmer", ParseUser.getCurrentUser()));
            farmer = (Farmer)futureFarmer.get();
            name.setText(farmer.getName());
        }catch(Exception e){
            e.printStackTrace();
        }//try catch

    }//end setUp

    public void clickSignOut(){
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                showIntent(MainActivity.class);
            }
        });
    }

    private void showIntent(Class className) {
        intent = new Intent(HomeScreenActivity.this, className);
        startActivity(intent);
    }

}//end HomeScreenActivity
