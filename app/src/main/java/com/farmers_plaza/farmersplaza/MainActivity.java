package com.farmers_plaza.farmersplaza;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.farmers_plaza.farmersplaza.controllers.general.RegistrationActivity;
import com.farmers_plaza.farmersplaza.farmer.HomeScreenActivity;

public class MainActivity extends AppCompatActivity {
    Button btnLogin, btnRegister;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnLoginOnClick();
        btnSignupOnClick();
    }

    private void btnLoginOnClick() {
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic goes here
                showIntent(HomeScreenActivity.class);
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
