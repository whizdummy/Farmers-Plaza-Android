package com.farmers_plaza.farmersplaza.dal;

import com.parse.ParseUser;

public class Farmer extends Person{

    private ParseUser user;

    public void setUser(ParseUser user){
        this.user = user;
    }

    public ParseUser getUser(){
        return this.user;
    }

}//end public class Farmer
