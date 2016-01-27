package com.farmers_plaza.farmersplaza.agriculturist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.controllers.general.MainActivity;
import com.farmers_plaza.farmersplaza.dal.AgriculturistDao;
import com.farmers_plaza.farmersplaza.models.Agriculturist;
import com.parse.ParseUser;

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

        agriculturistDao = new AgriculturistDao();
        agriculturist = agriculturistDao.retrieveAgriculturist(ParseUser.getCurrentUser());
        name =(TextView)findViewById(R.id.text_view_name);
        name.setText(agriculturist.getName());

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
    }

}//end AgriHomeScreenActivity
