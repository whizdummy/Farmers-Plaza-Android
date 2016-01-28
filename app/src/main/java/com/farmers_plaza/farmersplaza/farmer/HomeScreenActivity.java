package com.farmers_plaza.farmersplaza.farmer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.MainActivity;
import com.farmers_plaza.farmersplaza.controllers.general.RetrieveFarmerPicture;
import com.farmers_plaza.farmersplaza.models.Farmer;
import com.farmers_plaza.farmersplaza.service.FarmerService;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
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
    private ImageView pictureView;
    private ParseQuery<ParseObject> query;
    private ParseFile parseFile;
    private Bitmap bitmap;

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
//        clickBrowseFarm();
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
        txtViewName = (TextView) findViewById(R.id.text_view_name_farmer);
        setImageToSmallView();
        try {
            farmer = (Farmer)runThread("retrieveFarmer", ParseUser.getCurrentUser()).get();
        }catch(Exception e){
            e.printStackTrace();
        }//try catch
        txtViewName.setText(farmer.getName());
    }//end setUp

    private void setImageToSmallView() {
        pictureView = (ImageView) findViewById(R.id.small_farmer_profile);
        query = ParseQuery.getQuery("Farmer");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.addDescendingOrder("updatedAt");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    parseFile = object.getParseFile("photo");
                    try {
                        parseFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (e == null) {
                                    bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                                    pictureView.setImageBitmap(Bitmap.createScaledBitmap(
                                            bitmap, 250, 250, false
                                    ));
                                    pictureView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                                    pictureView.setAdjustViewBounds(true);
                                } else {
                                    Log.e("IMG RETRIEVAL ERROR", e.getMessage());
                                }
                            }
                        });
                    } catch (NullPointerException ex) {
                        Log.e("NO FILE", ex.getMessage());
                    }
                } else {
                    Log.e("QUERY ERROR", e.getMessage());
                }
            }
        });
    }

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
