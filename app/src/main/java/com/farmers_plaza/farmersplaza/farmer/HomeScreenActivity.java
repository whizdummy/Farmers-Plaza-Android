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
import com.parse.ParseUser;

public class HomeScreenActivity extends AppCompatActivity {

    private Intent                              intent;
    private Farmer                              farmer;
    private FarmerDao                           farmerDao;
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

        farmerDao = new FarmerDao();
        farmer = farmerDao.retrieveFarmer(ParseUser.getCurrentUser());
        name = (TextView)findViewById(R.id.text_view_name);
        name.setText(farmer.getName());
        signOut = (TextView)findViewById(R.id.btnSignOut);

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
