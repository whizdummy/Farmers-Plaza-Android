package com.farmers_plaza.farmersplaza.business;

import android.location.Address;
import android.location.Geocoder;
import android.text.TextUtils;

import com.farmers_plaza.farmersplaza.dal.FarmDao;
import com.farmers_plaza.farmersplaza.dal.FarmerDao;
import com.farmers_plaza.farmersplaza.farm.FarmAddActivity;
import com.farmers_plaza.farmersplaza.models.Farm;
import com.farmers_plaza.farmersplaza.models.Farmer;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.List;

public class FarmBusiness {

    private FarmDao                         farmDao;
    private FarmerDao                       farmerDao;

    public FarmBusiness(){
        farmDao = new FarmDao();
    }//end FarmBusiness

    public List<Farm> getAllFarm(ParseUser user){

        farmerDao = new FarmerDao();
        Farmer farmer = farmerDao.retrieveFarmer(user);
        return farmDao.getAllFarm(farmer);

    }//end getAllFarm

    public String registerFarm(Farm farm, Geocoder geocoder) throws IOException {
        if(isFarmNull(farm)){
            return "error-validation";
        }//end isFarmNull

        String regExpression;
        regExpression = "([A-z ,-]){2,}";
        if(!farm.getFarmAddress().matches(regExpression)){
            return "error-validation";
        }//end farmAddress

        Address farmAddress = geocoder.getFromLocationName(farm.getFarmAddress(), 1).get(0);
        if (farmAddress == null){
            return "error-validation";
        }//end Address validation

        ParseGeoPoint geoPoint = new ParseGeoPoint();
        geoPoint.setLatitude(farmAddress.getLatitude());
        geoPoint.setLongitude(farmAddress.getLongitude());
        farm.setGeoPoint(geoPoint);

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
