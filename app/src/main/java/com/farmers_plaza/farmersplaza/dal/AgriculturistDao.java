package com.farmers_plaza.farmersplaza.dal;

import com.farmers_plaza.farmersplaza.models.Agriculturist;
import com.farmers_plaza.farmersplaza.models.Farmer;
import com.parse.ParseQuery;

public class AgriculturistDao {

    public String registerAgriculturist(Agriculturist agri){

        try{

            ParseQuery queryAgriculturist = ParseQuery.getQuery(Agriculturist.class);
            queryAgriculturist.whereEqualTo("firstName", agri.getFirstName());
            queryAgriculturist.whereEqualTo("middleName", agri.getMiddleName());
            queryAgriculturist.whereEqualTo("lastName", agri.getLastName());
            queryAgriculturist.whereEqualTo("user", agri.getUser());
            if (queryAgriculturist.count() > 0){
                return "error-existing";
            }//end if(queryFarmer.count()>0)
            agri.save();
            return "success";

        }catch (Exception e){
            e.printStackTrace();
        }//try catch
        return "error-database";

    }//end public String registerAgriculturist(Agriculturist agri)

}//end public class AgriculturistDao
