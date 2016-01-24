package com.farmers_plaza.farmersplaza.models;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

@ParseClassName("Farm")
public class Farm extends ParseObject{

    public void setFarmer(Farmer farmer){
        put("farmer", farmer);
    }

    public Farmer getFarmer(){
        return (Farmer)getParseObject("farmer");
    }

    public void setFarmName(String farmName){
        put("farmName", farmName);
    }

    public String getFarmName(){
        return getString("farmName");
    }

    public void setFarmSize(double dblFarmSize){
        put("farmSize", dblFarmSize);
    }

    public double getFarmSize(){
        return getDouble("farmSize");
    }

    public void setFarmAddress(String strAddress){
        put("farmAddress", strAddress);
    }

    public String getFarmAddress(){
        return getString("farmAddress");
    }

    public void setGeoPoint(ParseGeoPoint geoPoint){
        put("geopoint", geoPoint);
    }

    public ParseGeoPoint getGeoPoint(){
        return getParseGeoPoint("geopoint");
    }

}//end Farm
