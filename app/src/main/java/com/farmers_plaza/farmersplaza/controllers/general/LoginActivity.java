package com.farmers_plaza.farmersplaza.controllers.general;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.farmers_plaza.farmersplaza.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    EditText                    username;
    EditText                    password;
    Button                      btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUp();
        clickBtnLogin();
    }//end onCreate

    public void setUp(){

        username = (EditText)findViewById(R.id.edit_text_email_username);
        password = (EditText)findViewById(R.id.edit_text_password);
        btnLogin = (Button)findViewById(R.id.btn_login);

    }//end setUp()

    public void clickBtnLogin(){

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if (e==null){

                                    //show Menu View

                                }//end if e == null
                                else{

                                    //show Error message

                                }//end else
                            }//end done
                        });//end loginBackground

            }//end onClick
        });//end setOnClickListener

    }//end clickBtnLogin()

}//end LoginActivity
