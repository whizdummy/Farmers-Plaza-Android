package com.farmers_plaza.farmersplaza.controllers.general;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.agriculturist.AgriHomeScreenActivity;
import com.farmers_plaza.farmersplaza.farmer.HomeScreenActivity;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    Button btnRegister;
    Intent intent;
    EditText username;
    EditText password;
    Button btnLogin;
    ProgressDialogPrompt progressDialogPrompt;

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

    public void setUp() {

        username = (EditText) findViewById(R.id.edit_text_email_username);
        password = (EditText) findViewById(R.id.edit_text_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        progressDialogPrompt = new ProgressDialogPrompt(this);

    }//end setUp()

    private void btnLoginOnClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialogPrompt.showProgress(getString(R.string.logging_in_text));
                ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (e == null) {
                                    if (!user.getBoolean("isAdmin")) {
                                        System.out.println("LOGIN AS FARMER");
                                        showIntent(HomeScreenActivity.class);
                                    } else {
                                        //show Agri Home
                                        showIntent(AgriHomeScreenActivity.class);
                                    }


                                }//end if e == null
                                else {

                                    //show Error message
                                    Toast.makeText(MainActivity.this, getString(R.string.invalid_user_text), Toast.LENGTH_LONG)
                                            .show();
                                }//end else
                                progressDialogPrompt.stopProgress();
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
        finish();
    }
}
