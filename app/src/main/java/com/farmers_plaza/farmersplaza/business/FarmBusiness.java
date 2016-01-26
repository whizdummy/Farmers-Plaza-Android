package com.farmers_plaza.farmersplaza.business;

import android.location.Geocoder;
import android.text.TextUtils;

import com.farmers_plaza.farmersplaza.dal.FarmDao;
import com.farmers_plaza.farmersplaza.models.Farm;

public class FarmBusiness {

    private FarmDao farmDao;

    public FarmBusiness(){
        farmDao = new FarmDao();
    }//end FarmBusiness

    public String registerFarm(Farm farm){
        if(isFarmNull(farm)){
            return "error-validation";
        }//end isFarmNull

        String regExpression;
        regExpression = "([A-z ,-]){2,}";
        if(!farm.getFarmAddress().matches(regExpression)){
            return "error-validation";
        }//end farmAddress

        return farmDao.registerFarm(farm);

    }//end registerFarm

    private boolean isFarmNull(Farm farm){
        if(farm.getFarmer()==null){
            return true;
        }
        else if(farm.getFarmSize() < 0){
            return true;
        }
        else if(TextUtils.isEmpty(farm.getFarmAddress())){
            return true;
        }
        else return false;
    }//end isFarmNull

}//end FarmBusiness
