package com.farmers_plaza.farmersplaza.dal;

import com.parse.ParseObject;

import java.util.Date;

public class Person extends ParseObject{

    private String strFirstName;
    private String strMiddleName;
    private String strLastName;
    private String strAddress;
    private long datBirthday;

    public Date getBirthday() {
        return new Date(this.datBirthday);
    }

    public void setBirthday(Date datBirthday) {
        this.datBirthday = datBirthday.getTime();
    }

    public String getAddress() {
        return strAddress;
    }

    public void setAddress(String strAddress) {
        this.strAddress = strAddress;
    }

    public String getFirstName() {
        return strFirstName;
    }

    public void setFirstName(String strFirstName) {
        this.strFirstName = strFirstName;
    }

    public String getLastName() {
        return strLastName;
    }

    public void setLastName(String strLastName) {
        this.strLastName = strLastName;
    }

    public String getMiddleName() {
        return strMiddleName;
    }

    public void setMiddleName(String strMiddleName) {
        this.strMiddleName = strMiddleName;
    }

}//end public class Person
