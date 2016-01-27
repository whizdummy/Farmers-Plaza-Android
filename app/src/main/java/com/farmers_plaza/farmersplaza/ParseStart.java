package com.farmers_plaza.farmersplaza;

import android.app.Application;

import com.farmers_plaza.farmersplaza.models.Agriculturist;
import com.farmers_plaza.farmersplaza.models.Farm;
import com.farmers_plaza.farmersplaza.models.Farmer;
import com.farmers_plaza.farmersplaza.models.Person;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseStart extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Farm.class);
        ParseObject.registerSubclass(Farmer.class);
        ParseObject.registerSubclass(Agriculturist.class);
        Parse.initialize(this);
    }
}
