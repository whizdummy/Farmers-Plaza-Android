package com.farmers_plaza.farmersplaza.farmer;

import android.content.Intent;
import android.graphics.Color;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.business.FarmBusiness;
import com.farmers_plaza.farmersplaza.business.FarmerBusiness;
import com.farmers_plaza.farmersplaza.controllers.general.ToolbarSetup;
import com.farmers_plaza.farmersplaza.models.Farm;
import com.farmers_plaza.farmersplaza.models.Farmer;
import com.farmers_plaza.farmersplaza.service.FarmerService;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AddFarmActivity extends AppCompatActivity {
    private ToolbarSetup toolbarSetup;
    private Intent intent;
    private EditText txtFarmName;
    private EditText txtFarmSizeLength;
    private EditText txtFarmSizeWidth;
    private EditText txtFarmLocation;
    private Button btnSubmit;
    private Farm farm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farm);
        setupToolbar();
        setUp();
        clickSubmit();
    }

    private void setUp(){

        txtFarmName = (EditText)findViewById(R.id.txtFarmName);
        txtFarmSizeLength = (EditText)findViewById(R.id.txtFarmSizeLength);
        txtFarmSizeWidth = (EditText)findViewById(R.id.txtFarmSizeWidth);
        txtFarmLocation = (EditText)findViewById(R.id.txtFarmAddress);

    }

    private void clickSubmit(){

        getData();
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Geocoder geocoder = new Geocoder(AddFarmActivity.this);
                FarmBusiness farmerBusiness = new FarmBusiness();
                try{
                    farm = farmerBusiness.validate(farm, geocoder);
                }catch(Exception e){
                    e.printStackTrace();
                }
                if (farm == null){
                    //display error in validation
                }else{

                    farm.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e==null){
                                //display success
                                Log.e("SUCCESS", "farm saved.");
                                showIntent(HomeScreenActivity.class);
                            }else{
                                Log.e("ERROR", e.getMessage());
                            }
                        }
                    });

                }
            }
        });

    }

    public void getData(){
        try {

            ParseQuery<Farmer> queryFarmer = new ParseQuery<Farmer>("Farmer");
            queryFarmer.whereEqualTo("user", ParseUser.getCurrentUser());
            Farmer farmer = queryFarmer.getFirst();
            farm = new Farm();
            farm.setFarmName(txtFarmName.getText().toString());
            farm.setFarmSizeLength(Double.parseDouble(txtFarmSizeLength.getText().toString()));
            farm.setFarmSizeWidth(Double.parseDouble(txtFarmSizeLength.getText().toString()));
            farm.setFarmAddress(txtFarmLocation.getText().toString());
            farm.setFarmer(farmer);

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void setupToolbar() {
        toolbarSetup = new ToolbarSetup(findViewById(R.id.toolbar), this, this);
        toolbarSetup.initialize(getString(R.string.add_farm_toolbar_title), Color.WHITE);
    }

    private void showIntent(Class className) {
        intent = new Intent(AddFarmActivity.this, className);
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
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

}
