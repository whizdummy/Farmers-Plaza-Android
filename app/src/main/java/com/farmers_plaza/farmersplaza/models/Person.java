package com.farmers_plaza.farmersplaza.models;

import com.parse.ParseObject;

import java.util.Date;

public class Person extends ParseObject{

    public Date getBirthday() {
        return getDate("birthday");
    }

    public void setBirthday(Date datBirthday) {
        put("birthday", datBirthday);
    }

    public String getAddress() {
        return getString("address");
    }

    public void setAddress(String strAddress) {
        put("address", strAddress);
    }

    public String getFirstName() {
        return getString("firstName");
    }

    public void setFirstName(String strFirstName) {
        put("firstName", strFirstName);
    }

    public String getLastName() {
        return getString("lastName");
    }

    public void setLastName(String strLastName) {
        put("lastName", strLastName);
    }

    public String getMiddleName() {
        return getString("middleName");
    }

    public void setMiddleName(String strMiddleName) {
        put("middleName", strMiddleName);
    }

}//end public class Person
