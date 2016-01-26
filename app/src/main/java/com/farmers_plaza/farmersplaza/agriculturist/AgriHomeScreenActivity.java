package com.farmers_plaza.farmersplaza.agriculturist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.MainActivity;
import com.farmers_plaza.farmersplaza.dal.AgriculturistDao;
import com.farmers_plaza.farmersplaza.models.Agriculturist;
import com.farmers_plaza.farmersplaza.service.AgriculturistService;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agri_main);
        setUp();
        clickSignOut();
    }

    public void setUp(){

        name =(TextView)findViewById(R.id.text_view_name);
        try {
            agriculturist = (Agriculturist) runThread(new AgriculturistService("retrieveAgriculturist", ParseUser.getCurrentUser())).get();
            name.setText(agriculturist.getName());
        }catch(Exception e){
            e.printStackTrace();
        }

    }//end setUp

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
        finish();
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
