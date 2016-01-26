package com.farmers_plaza.farmersplaza.controllers.general;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.agriculturist.AgriHomeScreenActivity;
import com.farmers_plaza.farmersplaza.business.AgriculturistBusiness;
import com.farmers_plaza.farmersplaza.farmer.HomeScreenActivity;
import com.farmers_plaza.farmersplaza.models.Agriculturist;
import com.farmers_plaza.farmersplaza.models.Farmer;
import com.farmers_plaza.farmersplaza.models.Person;
import com.farmers_plaza.farmersplaza.service.FarmerService;
import com.parse.ParseUser;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RegistrationActivity extends AppCompatActivity {

    EditText                firstName;
    EditText                middleName;
    EditText                lastName;
    EditText                phoneNo;
    EditText                address;
    EditText                email;
    EditText                username;
    EditText                password;
    EditText                retype;
    Spinner                 userType;
    Button                  btnRegister;
    Person                  person;
    Intent                  intent;
    ProgressDialogPrompt    progressDialogPrompt;
    Toolbar                 toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setUp();
        setUpToolbar();
        clickBtnRegister();
    }//end onCreate()

    public void setUp(){

        progressDialogPrompt = new ProgressDialogPrompt(this);
        firstName = (EditText) findViewById(R.id.edit_text_fname);
        middleName = (EditText) findViewById(R.id.edit_text_mname);
        lastName = (EditText) findViewById(R.id.edit_text_lname);
        phoneNo = (EditText) findViewById(R.id.edit_text_phone_num);
        address = (EditText) findViewById(R.id.edit_text_address);
        email = (EditText) findViewById(R.id.edit_text_email);
        username = (EditText) findViewById(R.id.edit_text_username);
        password = (EditText) findViewById(R.id.edit_text_password);
        retype = (EditText) findViewById(R.id.edit_text_confirm_pass);
        userType = (Spinner) findViewById(R.id.spinner_user_type);
        btnRegister = (Button) findViewById(R.id.btn_register);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

    }//end public void setUp()

    private void setUpToolbar() {
        toolbar.setTitle(getString(R.string.register_toolbar_title));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void clickBtnRegister(){

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                String strStatus;
                ExecutorService es = Executors.newSingleThreadExecutor();
                progressDialogPrompt.showProgress(getString(R.string.signing_up));
                try {
                    if (userType.getSelectedItem().toString().equals("Farmer")) {

                        Future<Object> futureObject = es.submit(new FarmerService("registerFarmer", person));
                        strStatus = futureObject.get().toString();
                        progressDialogPrompt.stopProgress();

                    }//end if
                    else {

                        AgriculturistBusiness agriculturistBusiness = new AgriculturistBusiness();
                        strStatus = agriculturistBusiness.validateAgriculturist((Agriculturist) person);
                        progressDialogPrompt.stopProgress();

                    }//end else
                    switch (strStatus) {
                        case "success":
                            //display success view
                            if (!ParseUser.getCurrentUser().getBoolean("isAdmin")) {
                                showIntent(HomeScreenActivity.class);
                            } else {
                                //show Agri Home
                                showIntent(AgriHomeScreenActivity.class);
                            }
                            break;
                        case "error-database":
                            break;
                        case "error-existing":
                            break;
                        case "error-validate":
                            //display error-validate
                            Log.e("TAG", "ERROR-VALIDATE");
                            break;
                    }//end switch

                }catch(InterruptedException e){
                    e.printStackTrace();
                }catch(ExecutionException e) {
                    e.printStackTrace();
                }
            }//end onClick
        });

    }//end clickBtnRegister()

    public void getData(){

        ParseUser user = new ParseUser();
        user.setEmail(email.getText().toString());
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        if (userType.getSelectedItem().toString().equals("Farmer")){

            person = new Farmer();
            user.put("isAdmin",false);
            person.setUser(user);
            person.setFirstName(firstName.getText().toString());
            person.setMiddleName(middleName.getText().toString());
            person.setLastName(lastName.getText().toString());
            person.setAddress(address.getText().toString());
            person.setPhoneNo(phoneNo.getText().toString());

        }//end if (userType.getSelectedItem().toString().equals("Farmer"))
        else if (userType.getSelectedItem().toString().equals("Agriculturist")) {

            person = new Agriculturist();
            user.put("isAdmin", true);
            person.setUser(user);
            person.setFirstName(firstName.getText().toString());
            person.setMiddleName(middleName.getText().toString());
            person.setLastName(lastName.getText().toString());
            person.setAddress(address.getText().toString());
            person.setPhoneNo(phoneNo.getText().toString());

        }//end else if (userType.getSelectedItem().toString().equals("Agriculturist"))

    }//end getData()

    private void showIntent(Class className) {
        intent = new Intent(RegistrationActivity.this, className);
        startActivity(intent);
    }

}//end RegistrationActivity
