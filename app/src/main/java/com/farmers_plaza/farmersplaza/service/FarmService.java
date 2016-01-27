package com.farmers_plaza.farmersplaza.service;

import android.location.Geocoder;
import android.util.Log;

import com.farmers_plaza.farmersplaza.business.FarmBusiness;
import com.farmers_plaza.farmersplaza.business.FarmerBusiness;
import com.farmers_plaza.farmersplaza.dal.FarmDao;
import com.farmers_plaza.farmersplaza.dal.FarmerDao;
import com.farmers_plaza.farmersplaza.models.Farm;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

public class FarmService implements Callable{

    private String                              strThreadMethod;
    private FarmBusiness                        farmBusiness;
    private FarmDao                             farmDao;
    private Object                              object;
    private Geocoder                            geocoder;

    public FarmService(String strThreadMethod, Object object){

        this.strThreadMethod = strThreadMethod;
        farmBusiness = new FarmBusiness();
        farmDao = new FarmDao();
        this.object = object;

    }

    public FarmService(String strThreadMethod){
        this.strThreadMethod = strThreadMethod;
        farmBusiness = new FarmBusiness();
        farmDao = new FarmDao();
    }

    public FarmService(String strThreadMethod, Object object, Geocoder geocoder){

        this.strThreadMethod = strThreadMethod;
        farmBusiness = new FarmBusiness();
        farmDao = new FarmDao();
        this.object = object;
        this.geocoder = geocoder;

    }

    public Object call() throws IOException {

        switch (this.strThreadMethod){
            case "registerFarm":
                Log.e("THREAD", "registerFarm");
                return registerFarm((Farm)object);
            case "getAllFarm":
                Log.e("THREAD", "getAllFarm");
                return getAllFarm((ParseUser)object);
        }//end switch
        return null;

    }//end call

    public String registerFarm(Farm farm) throws IOException {
        return farmBusiness.registerFarm(farm, geocoder);
    }

    public List<Farm>getAllFarm(ParseUser user){
        return farmBusiness.getAllFarm(user);
    }

}//end FarmService
