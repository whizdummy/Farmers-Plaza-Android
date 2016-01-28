package com.farmers_plaza.farmersplaza.agriculturist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.MainActivity;
import com.farmers_plaza.farmersplaza.dal.AgriculturistDao;
import com.farmers_plaza.farmersplaza.models.Agriculturist;
import com.farmers_plaza.farmersplaza.service.AgriculturistService;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AgriHomeScreenActivity  extends AppCompatActivity{

    private Intent                                      intent;
    private Agriculturist                               agriculturist;
    private TextView                                    name;
    private AgriculturistDao                            agriculturistDao;
    private Button                                      signOut;
    private ImageButton                                 imgBtnSoilTester;
    private ImageButton                                 imgBtnProfile;
    private ImageView pictureView;
    ParseQuery<ParseObject> query;
    ParseFile parseFile;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agri_main);
        setUp();
        clickSoilTester();
        clickProfile();
        clickSignOut();
    }

    private void clickSoilTester() {
        imgBtnSoilTester = (ImageButton) findViewById(R.id.img_btn_soil_tester);
        imgBtnSoilTester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIntent(SoilAnalysisActivity.class);
            }
        });
    }

    private void clickProfile() {
        imgBtnProfile = (ImageButton) findViewById(R.id.img_btn_profile_agri);
        imgBtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIntent(AgriProfileActivity.class);
            }
        });
    }

    public void setUp(){

        name =(TextView)findViewById(R.id.text_view_name);
        setPictureToSmallView();
        try {
            agriculturist = (Agriculturist) runThread(new AgriculturistService("retrieveAgriculturist", ParseUser.getCurrentUser())).get();
            name.setText(agriculturist.getName());
        }catch(Exception e){
            e.printStackTrace();
        }

    }//end setUp

    private void setPictureToSmallView() {
        pictureView = (ImageView) findViewById(R.id.img_view_profile_pic_agri);
        query = ParseQuery.getQuery("Agriculturist");
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

    public void clickSignOut(){

        signOut = (Button)findViewById(R.id.btnLogOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                showIntent(MainActivity.class);
            }
        });

    }//end clickSignOut

    private void showIntent(Class className) {
        intent = new Intent(AgriHomeScreenActivity.this, className);
        startActivity(intent);
//        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private Future<Object> runThread(Callable object){
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<Object> futureObject = es.submit(object);

        return futureObject;
    }//end runThread

}//end AgriHomeScreenActivity
