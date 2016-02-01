package com.farmers_plaza.farmersplaza.farmer;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.ToolbarSetup;
import com.farmers_plaza.farmersplaza.models.Farm;
import com.farmers_plaza.farmersplaza.models.Farmer;
import com.farmers_plaza.farmersplaza.service.FarmerService;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.IOException;
import java.util.List;
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
    private ParseObject farmerObjRef;
    private ParseObject farmerObject;

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
                fetchFarmer();
            }
        });

    }

    private ParseGeoPoint addressToGeopoint(String address) {
        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList;
        Address currentLocation;

        try {
            addressList = geocoder.getFromLocationName(address, 1);
            if(addressList == null) {
                return null;
            }
            currentLocation = addressList.get(0);

            return (new ParseGeoPoint(currentLocation.getLatitude(),currentLocation.getLongitude()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
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

    private void fetchFarmer () {
            ParseQuery<ParseObject> farmerQuery = ParseQuery.getQuery("Farmer");
            farmerQuery.whereEqualTo("user", ParseUser.getCurrentUser());
            farmerQuery.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
//                        farmerObjRef = object;
                        Log.e("Hello", object.getObjectId());
                        farmerObject = ParseObject.create("Farm");
                        farmerObject.put("farmName", txtFarmName.getText().toString());
                        farmerObject.put("farmer", ParseObject.createWithoutData("Farmer", object.getObjectId()));
                        farmerObject.put("farmSize",
                                Double.toString(Double.parseDouble(txtFarmSizeLength.getText().toString())
                                        * Double.parseDouble(txtFarmSizeWidth.getText().toString())));
                        farmerObject.put("farmerAddress", txtFarmLocation.getText().toString());
                        farmerObject.put("farmerGeoPoint",
                                addressToGeopoint(txtFarmLocation.getText().toString()));
                        farmerObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(AddFarmActivity.this,
                                            "Successfully Saved!", Toast.LENGTH_LONG)
                                            .show();
                                } else {
                                    Toast.makeText(AddFarmActivity.this,
                                            "Save Failed!", Toast.LENGTH_LONG)
                                            .show();
                                }
                            }
                        });
                    }
                }
            });
    }
}
