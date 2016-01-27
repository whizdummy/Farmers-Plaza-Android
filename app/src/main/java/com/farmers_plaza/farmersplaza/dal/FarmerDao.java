package com.farmers_plaza.farmersplaza.dal;

import android.util.Log;

import com.farmers_plaza.farmersplaza.models.Farmer;
import com.farmers_plaza.farmersplaza.models.Person;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import bolts.Task;

public class FarmerDao extends Thread{
    private static String strStatus = null;
    private static Farmer farmer;

    public Farmer retrieveFarmer(ParseUser user){

        try{
            ParseQuery<Farmer> queryFarmer = ParseQuery.getQuery(Farmer.class);
            queryFarmer.whereEqualTo("user", user);
            return queryFarmer.getFirst();

        }catch(Exception e){
            e.printStackTrace();
            Log.e("TAG", e.getMessage());
        }//try catch
        return null;

    }

    public String registerFarmer(final Farmer farmer){

        try{

            ParseQuery<Farmer> queryFarmer = ParseQuery.getQuery(Farmer.class);
            queryFarmer.whereEqualTo("firstName", farmer.getFirstName());
            queryFarmer.whereEqualTo("middleName", farmer.getMiddleName());
            queryFarmer.whereEqualTo("lastName", farmer.getLastName());
            if (queryFarmer.count() > 0){
//                return "error-existing";
                strStatus = "error-existing";
            }//end if(queryFarmer.count()>0)
            farmer.getUser().signUp();
            farmer.save();
            return "success";
        }catch(ParseException pe){
            pe.printStackTrace();
            return "error-existing";
        }catch (Exception e){
            e.printStackTrace();
        }//try catch
        return "error-database";

    }//end public String registerFarmer()

}//end public class FarmerDao
