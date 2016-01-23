package com.farmers_plaza.farmersplaza.dal;

import com.farmers_plaza.farmersplaza.models.Farmer;
import com.parse.ParseQuery;

public class FarmerDao {

    public String registerFarmer(Farmer farmer){

        try{

            ParseQuery queryFarmer = ParseQuery.getQuery(Farmer.class);
            queryFarmer.whereEqualTo("firstName", farmer.getFirstName());
            queryFarmer.whereEqualTo("middleName", farmer.getMiddleName());
            queryFarmer.whereEqualTo("lastName", farmer.getLastName());
            queryFarmer.whereEqualTo("user", farmer.getUser());
            if (queryFarmer.count() > 0){
                return "error-existing";
            }//end if(queryFarmer.count()>0)
            farmer.saveInBackground();
            return "success";

        }catch (Exception e){
            e.printStackTrace();
        }//try catch
        return "error-database";

    }//end public String registerFarmer()

}//end public class FarmerDao
