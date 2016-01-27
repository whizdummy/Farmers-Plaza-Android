package com.farmers_plaza.farmersplaza.service;

import android.util.Log;

import com.farmers_plaza.farmersplaza.business.AgriculturistBusiness;
import com.farmers_plaza.farmersplaza.dal.AgriculturistDao;
import com.farmers_plaza.farmersplaza.models.Agriculturist;
import com.parse.ParseUser;

import java.util.concurrent.Callable;

public class AgriculturistService implements Callable{

    private String strThreadMethod;
    private AgriculturistDao agriculturistDao;
    private AgriculturistBusiness agriculturistBusiness;
    private Object object;

    public AgriculturistService(String strThreadMethod, Object object){

        this.strThreadMethod = strThreadMethod;
        agriculturistBusiness = new AgriculturistBusiness();
        agriculturistDao = new AgriculturistDao();
        this.object = object;

    }//end FarmerService

    public Object call(){

        switch (this.strThreadMethod){
            case "registerAgriculturist":{

                Log.e("THREAD:", "registerAgriculturist");
                return registerAgriculturist((Agriculturist)object);

            }//end registerFarmer
            case "retrieveAgriculturist":{
                Log.e("THREAD", "retrieveAgriculturist");
                return retrieveAgriculturist((ParseUser)object);
            }
        }//end switch
        return null;
    }//end run

    private String registerAgriculturist(Agriculturist agriculturist){
        return agriculturistBusiness.validateAgriculturist(agriculturist);
    }

    private Agriculturist retrieveAgriculturist(ParseUser user){
        return agriculturistDao.retrieveAgriculturist(user);
    }

}//end AgriculturistService
