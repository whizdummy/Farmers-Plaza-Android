package com.farmers_plaza.farmersplaza.dal;

import android.util.Log;

import com.farmers_plaza.farmersplaza.models.Agriculturist;
import com.farmers_plaza.farmersplaza.models.Farmer;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SignUpCallback;

public class AgriculturistDao {

    public String registerAgriculturist(Agriculturist agri){

        try{

            ParseQuery queryAgriculturist = ParseQuery.getQuery(Agriculturist.class);
            queryAgriculturist.whereEqualTo("firstName", agri.getFirstName());
            queryAgriculturist.whereEqualTo("middleName", agri.getMiddleName());
            queryAgriculturist.whereEqualTo("lastName", agri.getLastName());
            if (queryAgriculturist.count() > 0) {
                return "error-existing";
            }//end if(queryFarmer.count()>0)
//            agri.signUpInBackground(new SignUpCallback() {
//                @Override
//                public void done(ParseException e) {
//                    if(e != null) {
//                        Log.e("SIGN UP", e.getMessage());
//                    }
//                }
//            });
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
