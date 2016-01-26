package com.farmers_plaza.farmersplaza.dal;

import android.util.Log;

import com.farmers_plaza.farmersplaza.models.Farmer;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

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
            farmer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e==null){
                        Log.e("TAG", e.getMessage());
                    }
                }
            });
            return "success";

        }catch (Exception e){
            e.printStackTrace();
        }//try catch
        return "error-database";

    }//end public String registerFarmer()

}//end public class FarmerDao
