package com.farmers_plaza.farmersplaza.controllers.general;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.farmers_plaza.farmersplaza.R;
import com.farmers_plaza.farmersplaza.dal.AgriculturistDao;
import com.farmers_plaza.farmersplaza.dal.FarmerDao;
import com.farmers_plaza.farmersplaza.models.Agriculturist;
import com.farmers_plaza.farmersplaza.models.Farmer;
import com.farmers_plaza.farmersplaza.models.Person;
import com.parse.ParseUser;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setUp();
        clickBtnRegister();
    }//end onCreate()

    public void setUp(){

        firstName = (EditText)findViewById(R.id.edit_text_fname);
        middleName = (EditText)findViewById(R.id.edit_text_mname);
        lastName = (EditText)findViewById(R.id.edit_text_lname);
        phoneNo = (EditText)findViewById(R.id.edit_text_phone_num);
        address = (EditText)findViewById(R.id.edit_text_address);
        email = (EditText)findViewById(R.id.edit_text_email);
        username = (EditText)findViewById(R.id.edit_text_username);
        password = (EditText)findViewById(R.id.edit_text_password);
        retype = (EditText)findViewById(R.id.edit_text_confirm_pass);
        userType = (Spinner)findViewById(R.id.spinner_user_type);
        btnRegister = (Button)findViewById(R.id.btn_register);

    }//end public void setUp()

    public void clickBtnRegister(){

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                String strStatus;
                if (userType.getSelectedItem().toString().equals("Farmer")){

                    FarmerDao farmerDao = new FarmerDao();
                    strStatus = farmerDao.registerFarmer((Farmer)person);

                }//end if
                else{

                    AgriculturistDao agriculturistDao = new AgriculturistDao();
                    strStatus = agriculturistDao.registerAgriculturist((Agriculturist)person);

                }//end else

                if (strStatus.equals("success")){

                    //display success view

                }//end if success
                else if (strStatus.equals("error-database")){

                    //display error-database

                }//end else if error-database
                else if (strStatus.equals("error-existing")){

                    //display error-existing

                }//end else if error-existing

            }//end onClick
        });

    }//end clickBtnRegister()

    public void getData(){

        ParseUser user = new ParseUser();
        user.setEmail(email.getText().toString());
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        if (userType.getSelectedItem().toString().equals("Farmer")){

            user.put("isAdmin", false);
            person = new Farmer();
            person.setUser(user);
            person.setFirstName(firstName.getText().toString());
            person.setMiddleName(middleName.getText().toString());
            person.setLastName(lastName.getText().toString());
            person.setAddress(address.getText().toString());
            person.setPhoneNo(phoneNo.getText().toString());

        }//end if (userType.getSelectedItem().toString().equals("Farmer"))
        else if (userType.getSelectedItem().toString().equals("Agriculturist")) {

            user.put("isAdmin", true);
            person = new Agriculturist();
            person.setUser(user);
            person.setFirstName(firstName.getText().toString());
            person.setMiddleName(middleName.getText().toString());
            person.setLastName(lastName.getText().toString());
            person.setAddress(address.getText().toString());
            person.setPhoneNo(phoneNo.getText().toString());

        }//end else if (userType.getSelectedItem().toString().equals("Agriculturist"))

    }//end getData()

}//end RegistrationActivity
