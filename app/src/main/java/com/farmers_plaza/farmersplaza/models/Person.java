package com.farmers_plaza.farmersplaza.models;

import com.parse.ParseUser;

import java.util.Date;

public class Person extends ParseUser{

    public void setUser(String user){
        setUsername(user);
    }

    public String getUser(){
        return getString("username");
    }

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

    public void setPhoneNo(String strPhoneNo){
        put("phoneNo", strPhoneNo);
    }

    public String getPhoneNo() {
        return getString("phoneNo");
    }

}//end public class Person
