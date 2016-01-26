package com.farmers_plaza.farmersplaza.service;

import android.util.Log;

import com.farmers_plaza.farmersplaza.business.FarmerBusiness;
import com.farmers_plaza.farmersplaza.dal.FarmerDao;
import com.farmers_plaza.farmersplaza.models.Farmer;
import com.parse.ParseUser;

import java.util.concurrent.Callable;

public class FarmerService implements Callable<Object>{

    private String strThreadMethod;
    private FarmerDao farmerDao;
    private FarmerBusiness farmerBusiness;
    private Object object;

    public FarmerService(String strThreadMethod, Object object){

        this.strThreadMethod = strThreadMethod;
        farmerBusiness = new FarmerBusiness();
        farmerDao = new FarmerDao();
        this.object = object;

    }//end FarmerService

    public Object call(){

        switch (this.strThreadMethod){
            case "registerFarmer":{

                Log.e("THREAD:", "registerFarmer");
                return registerFarmer((Farmer)object);

            }//end registerFarmer
            case "retrieveFarmer":{
                Log.e("THREAD", "retrieveFarmer");
                return retrieveFarmer((ParseUser)object);
            }
        }//end switch
        return null;
    }//end run

    private String registerFarmer(Farmer farmer){
        return farmerBusiness.validateFarmer(farmer);
    }

    private Farmer retrieveFarmer(ParseUser user){
        return farmerDao.retrieveFarmer(user);
    }

}//end FarmerService
