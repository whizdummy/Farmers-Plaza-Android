package com.farmers_plaza.farmersplaza.dal;

import com.farmers_plaza.farmersplaza.models.Farm;
import com.farmers_plaza.farmersplaza.models.Farmer;
import com.parse.ParseQuery;

import java.util.List;

public class FarmDao {

    public String registerFarm(Farm farm){

        try{

            ParseQuery<Farm> queryFarm = ParseQuery.getQuery(Farm.class);
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

    public List<Farm> getAllFarm(Farmer farmer){

        try{

            ParseQuery<Farm> queryFarm = ParseQuery.getQuery(Farm.class);
            queryFarm.whereEqualTo("farmer", farmer);
            return queryFarm.find();

        }catch(Exception e){
            e.printStackTrace();
        }//end try catch
        return null;

    }//end getAllFarm

}//end public class FarmDao
