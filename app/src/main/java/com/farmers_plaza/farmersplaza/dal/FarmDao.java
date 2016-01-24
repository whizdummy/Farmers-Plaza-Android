package com.farmers_plaza.farmersplaza.dal;

import com.farmers_plaza.farmersplaza.models.Farm;
import com.parse.ParseQuery;

public class FarmDao {

    public String registerFarm(Farm farm){

        try{

            ParseQuery queryFarm = ParseQuery.getQuery(Farm.class);
            queryFarm.whereEqualTo("farmName", farm.getFarmName());
            queryFarm.whereEqualTo("geopoint", farm.getGeoPoint());
            if (queryFarm.count() > 0){
                return "error-name-exist";
            }
            farm.save();
            return "success";

        }catch(Exception e){
            e.printStackTrace();
        }//end try catch
        return "error-database";

    }//end registerFarm

}//end public class FarmDao
