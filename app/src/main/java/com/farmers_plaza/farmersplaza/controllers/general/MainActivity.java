package com.farmers_plaza.farmersplaza.controllers.general;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.agriculturist.AgriHomeScreenActivity;
import com.farmers_plaza.farmersplaza.farmer.HomeScreenActivity;
import com.farmers_plaza.farmersplaza.models.Person;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    Button                      btnRegister;
    Intent                      intent;
    EditText                    username;
    EditText                    password;
    Button                      btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUp();
        btnLoginOnClick();
        btnSignupOnClick();
    }

    public void setUp(){

        username = (EditText)findViewById(R.id.edit_text_email_username);
        password = (EditText)findViewById(R.id.edit_text_password);
        btnLogin = (Button)findViewById(R.id.btn_login);

    }//end setUp()

    private void btnLoginOnClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person.logInInBackground(username.getText().toString(), password.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (e == null) {

                                    if (Person.getCurrentUser().getBoolean("isAdmin") == false) {
                                        showIntent(HomeScreenActivity.class);
                                    }else{
                                        //show Agri Home
                                        showIntent(AgriHomeScreenActivity.class);
                                    }

                                }//end if e == null
                                else {

                                    //show Error message
                                    Log.e("TAG", e.getMessage());

                                }//end else
                            }//end done
                        });//end loginBackground
            }
        });
    }

    private void btnSignupOnClick() {
        btnRegister = (Button) findViewById(R.id.btnSignUp);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIntent(RegistrationActivity.class);
            }
        });
    }

    private void showIntent(Class className) {
        intent = new Intent(MainActivity.this, className);
        startActivity(intent);
    }
}
