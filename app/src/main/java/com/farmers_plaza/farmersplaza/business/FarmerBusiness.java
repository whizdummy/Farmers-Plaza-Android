package com.farmers_plaza.farmersplaza.business;

import android.text.TextUtils;

import com.farmers_plaza.farmersplaza.dal.FarmerDao;
import com.farmers_plaza.farmersplaza.models.Farmer;

public class FarmerBusiness extends Thread{

    private FarmerDao farmerDao;

    public FarmerBusiness(){
        this.farmerDao = new FarmerDao();
    }//end public FarmerBusiness()

    public String validateFarmer(Farmer farmer){
        if(isFarmerNull(farmer)){
            return "error-validate";
        }//end isFarmerNull

        String regExpression;
        regExpression = "([A-z '-]){2,}";
        farmer = trimFarmerValues(farmer);
        if (!farmer.getFirstName().matches(regExpression)) {
            return "error-validate";
        }//end firstName
        if (!farmer.getMiddleName().matches(regExpression)) {
            return "error-validate";
        }//end middleName
        regExpression = "([A-z '.-]){2,}";
        if (!farmer.getLastName().matches(regExpression)) {
            return "error-validate";
        }//end lastName
        regExpression = "([A-z ,-]){2,}";
        if (!farmer.getAddress().matches(regExpression)) {
            return "error-validate";
        }//end address
        regExpression = "(^09)+([0-9]){9}";
        if (!farmer.getPhoneNo().matches(regExpression)) {
            return "error-validate";
        }//end phoneNo

        farmer.setFirstName(properCasing(farmer.getFirstName()));
        farmer.setMiddleName(properCasing(farmer.getMiddleName()));
        farmer.setLastName(properCasing(farmer.getLastName()));
        farmer.setAddress(properCasing(farmer.getAddress()));
        return farmerDao.registerFarmer(farmer);
    }//end public String validateFarmer

    private boolean isFarmerNull(Farmer farmer){
        if (TextUtils.isEmpty(farmer.getFirstName())){
            return true;
        }//end firstName
        else if (TextUtils.isEmpty(farmer.getMiddleName())){
            return true;
        }//end middleName
        else if (TextUtils.isEmpty(farmer.getFirstName())){
            return true;
        }//end firstName
        else if (TextUtils.isEmpty(farmer.getAddress())){
            return true;
        }//end address
        else if (TextUtils.isEmpty(farmer.getPhoneNo())){
            return true;
        }//end phoneNo
        else return false;
    }//end isFarmerNull

    private Farmer trimFarmerValues(Farmer farmer){
        farmer.setFirstName(farmer.getFirstName().trim());
        farmer.setMiddleName(farmer.getMiddleName().trim());
        farmer.setLastName(farmer.getLastName().trim());
        farmer.setAddress(farmer.getAddress().trim());
        return farmer;
    }//end private Farmer trimFarmerValues

    private String properCasing(String str){
        String[] words = str.split("\\s+");
        StringBuilder sb = new StringBuilder();
        if (words[0].length() > 0) {
            sb.append(Character.toUpperCase(words[0].charAt(0)) + words[0].subSequence(1, words[0].length()).toString().toLowerCase());
            for (int i = 1; i < words.length; i++) {
                sb.append(" ");
                sb.append(Character.toUpperCase(words[i].charAt(0)) + words[i].subSequence(1, words[i].length()).toString().toLowerCase());
            }
        }
        return sb.toString();
    }//end properCasing

}//end public class FarmerBusiness
