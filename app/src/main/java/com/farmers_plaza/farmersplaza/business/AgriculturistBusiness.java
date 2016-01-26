package com.farmers_plaza.farmersplaza.business;

import android.text.TextUtils;

import com.farmers_plaza.farmersplaza.dal.AgriculturistDao;
import com.farmers_plaza.farmersplaza.models.Agriculturist;

public class AgriculturistBusiness extends Thread{
    
    private AgriculturistDao agriculturistDao;
    
    public AgriculturistBusiness(){
        this.agriculturistDao = new AgriculturistDao(); 
    }//end public AgriculturistBusiness

    public String validateAgriculturist(Agriculturist agri){
        if(isAgriculturistNull(agri)){
            return "error-validate";
        }

        String regExpression;
        regExpression = "([A-z '-]){2,}";
        agri = trimAgriculturistValues(agri);
        if (!agri.getFirstName().matches(regExpression)) {
            return "error-validate";
        }
        if (!agri.getMiddleName().matches(regExpression)) {
            return "error-validate";
        }
        regExpression = "([A-z '.-]){2,}";
        if (!agri.getLastName().matches(regExpression)) {
            return "error-validate";
        }
        regExpression = "([A-z ,-]){2,}";
        if (!agri.getAddress().matches(regExpression)) {
            return "error-validate";
        }
        regExpression = "(^09)+([0-9]){9}";
        if (!agri.getPhoneNo().matches(regExpression)) {
            return "error-validate";
        }

        agri.setFirstName(properCasing(agri.getFirstName()));
        agri.setMiddleName(properCasing(agri.getMiddleName()));
        agri.setLastName(properCasing(agri.getLastName()));
        agri.setAddress(properCasing(agri.getAddress()));
        return agriculturistDao.registerAgriculturist(agri);
    }//end public String validateAgriculturist

    private boolean isAgriculturistNull(Agriculturist agri){
        if (TextUtils.isEmpty(agri.getFirstName())){
            return true;
        }//end firstName
        else if (TextUtils.isEmpty(agri.getMiddleName())){
            return true;
        }//end middleName
        else if (TextUtils.isEmpty(agri.getFirstName())){
            return true;
        }//end firstName
        else if (TextUtils.isEmpty(agri.getAddress())){
            return true;
        }//end address
        else if (TextUtils.isEmpty(agri.getPhoneNo())){
            return true;
        }//end phoneNo
        else return false;
    }//end isFarmerNull

    private Agriculturist trimAgriculturistValues(Agriculturist agri){
        agri.setFirstName(agri.getFirstName().trim());
        agri.setMiddleName(agri.getMiddleName().trim());
        agri.setLastName(agri.getLastName().trim());
        agri.setAddress(agri.getAddress().trim());
        return agri;
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
    }
}
