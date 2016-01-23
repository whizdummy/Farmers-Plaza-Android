package com.farmers_plaza.farmersplaza.models;

import com.parse.ParseUser;

public class Agriculturist extends Person{

    private ParseUser user;

    public ParseUser getUser() {
        return user;
    }

    public void setUser(ParseUser user) {
        this.user = user;
    }

}//end public class Agriculturist
