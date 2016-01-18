package com.farmers_plaza.farmersplaza;

import android.app.Application;
import com.parse.Parse;

public class ParseStart extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this);
    }
}
