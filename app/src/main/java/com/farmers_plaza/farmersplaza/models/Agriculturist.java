package com.farmers_plaza.farmersplaza.models;

import com.parse.ParseClassName;
import com.parse.ParseUser;

@ParseClassName("Agriculturist")
public class Agriculturist extends Person{

    public void setUser(ParseUser user){
        put("user", user);
    }

    public ParseUser getUser(){
        return getParseUser("user");
    }

}//end public class Agriculturist
