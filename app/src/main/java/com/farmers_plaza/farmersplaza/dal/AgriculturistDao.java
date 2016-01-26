package com.farmers_plaza.farmersplaza.dal;

import android.util.Log;

import com.farmers_plaza.farmersplaza.models.Agriculturist;
import com.farmers_plaza.farmersplaza.models.Farmer;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class AgriculturistDao {

    public Agriculturist retrieveAgriculturist(ParseUser user){

        try{
            ParseQuery<Agriculturist> queryAgri = ParseQuery.getQuery(Agriculturist.class);
            queryAgri.whereEqualTo("user", user);
            return queryAgri.getFirst();

        }catch(Exception e){
            e.printStackTrace();
            Log.e("TAG", e.getMessage());
        }//try catch
        return null;

    }

    public String registerAgriculturist(Agriculturist agri){

        try{

            ParseQuery queryAgriculturist = ParseQuery.getQuery(Agriculturist.class);
            queryAgriculturist.whereEqualTo("firstName", agri.getFirstName());
            queryAgriculturist.whereEqualTo("middleName", agri.getMiddleName());
            queryAgriculturist.whereEqualTo("lastName", agri.getLastName());
            if (queryAgriculturist.count() > 0) {
                return "error-existing";
            }//end if(queryFarmer.count()>0)
            agri.save();
            return "success";

        }catch(ParseException pe){
            pe.printStackTrace();
            return "error-existing";
        }catch(Exception e){
            e.printStackTrace();
        }//try catch
        return "error-database";

    }//end public String registerAgriculturist(Agriculturist agri)

}//end public class AgriculturistDao
