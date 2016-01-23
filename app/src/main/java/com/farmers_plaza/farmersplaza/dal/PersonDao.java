package com.farmers_plaza.farmersplaza.dal;

import com.farmers_plaza.farmersplaza.models.Agriculturist;
import com.farmers_plaza.farmersplaza.models.Farmer;
import com.farmers_plaza.farmersplaza.models.Person;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class PersonDao {

    public Person retrievePerson(ParseUser user){

        ParseQuery queryPerson;
        try{

            if (user.getInt("personType") == 0){

                queryPerson = ParseQuery.getQuery(Farmer.class);
                queryPerson.whereEqualTo("user", user);
                return (Farmer)queryPerson.getFirst();

            }//end if (user.getInt("personType") == 0)
            else{

                queryPerson = ParseQuery.getQuery(Agriculturist.class);
                queryPerson.whereEqualTo("user", user);
                return (Agriculturist)queryPerson.getFirst();

            }//end else

        }catch(Exception e){
            e.printStackTrace();
        }//end try catch
        return null;

    }//end retrievePerson

}//end public class PersonDao
