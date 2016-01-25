package com.farmers_plaza.farmersplaza.business;

import com.farmers_plaza.farmersplaza.dal.AgriculturistDao;
import com.farmers_plaza.farmersplaza.models.Agriculturist;

public class AgriculturistBusiness {
    
    private AgriculturistDao agriculturistDao;
    
    public AgriculturistBusiness(){
        this.agriculturistDao = new AgriculturistDao(); 
    }//end public AgriculturistBusiness

    public String validateAgriculturist(Agriculturist agri){
        String regExpression;
        regExpression = "([A-z '-]){2,}";
        agri = trimAgriculturistValues(agri);
        if (!agri.getFirstName().matches(regExpression))
            return "First name contains invalid characters";
        if (!agri.getMiddleName().matches(regExpression))
            return "Middle name contains invalid characters";
        regExpression = "([A-z '.-]){2,}";
        if (!agri.getLastName().matches(regExpression))
            return "Last name contains invalid characters";
        regExpression = "([A-z ,-]){2,}";
        if (!agri.getAddress().matches(regExpression))
            return "Address contains invalid characters";
        regExpression = "(^09)+([0-9]){9}";
        if (!agri.getPhoneNo().matches(regExpression))
            return "Phone number contains invalid characters";
        agriculturistDao.registerAgriculturist(agri);
        return "Success";
    }//end public String validateAgriculturist

    private Agriculturist trimAgriculturistValues(Agriculturist agri){
        agri.setFirstName(agri.getFirstName().trim());
        agri.setMiddleName(agri.getMiddleName().trim());
        agri.setLastName(agri.getLastName().trim());
        return agri;
    }//end private Farmer trimFarmerValues
}
