package com.farmers_plaza.farmersplaza.dal;

import android.util.Log;

import com.farmers_plaza.farmersplaza.models.Farmer;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class FarmerDao {
    private static String strStatus;


    public String registerFarmer(Farmer farmer){

        try{

            ParseQuery<Farmer> queryFarmer = ParseQuery.getQuery(Farmer.class);
            queryFarmer.whereEqualTo("firstName", farmer.getFirstName());
            queryFarmer.whereEqualTo("middleName", farmer.getMiddleName());
            queryFarmer.whereEqualTo("lastName", farmer.getLastName());
            queryFarmer.whereEqualTo("username", farmer.getUser());
            if (queryFarmer.count() > 0){
//                return "error-existing";
                strStatus = "error-existing";
            }//end if(queryFarmer.count()>0)
            farmer.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e != null) {
                        Log.e("TAG", e.getMessage());
                    } else {
                        strStatus = "success";
                    }
                }
            });
            Log.e("SAVE STATUS", strStatus);
            return strStatus;
        }catch (Exception e){
            e.printStackTrace();
        }//try catch
        return "error-database";

    }//end public String registerFarmer()

}//end public class FarmerDao
