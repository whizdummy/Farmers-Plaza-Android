package com.farmers_plaza.farmersplaza.business;

import android.location.Geocoder;

import com.farmers_plaza.farmersplaza.dal.FarmDao;
import com.farmers_plaza.farmersplaza.models.Farm;

public class FarmBusiness {

    private FarmDao farmDao;

    public FarmBusiness(){
        farmDao = new FarmDao();
    }//end FarmBusiness

    public String registerFarm(Farm farm){

        return "error-validation";

    }//end registerFarm

}//end FarmBusiness
