package com.farmers_plaza.farmersplaza.models;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.File;
import java.util.Date;

public class Person extends ParseObject{

    public void setUser(ParseUser user){
        put("user", user);
    }

    public ParseUser getUser(){
        return getParseUser("user");
    }

    public String getName(){
        return getFirstName()+" "+getMiddleName().charAt(0)+". "+getLastName();
    }

    public void setDisplayPhoto(ParseFile displayPhoto){
        put("displayPhoto", displayPhoto);
    }

    public ParseFile getDisplayPhoto(){
        return getParseFile("displayPhoto");
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
