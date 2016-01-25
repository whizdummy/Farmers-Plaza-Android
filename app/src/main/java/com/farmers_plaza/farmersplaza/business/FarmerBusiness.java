package com.farmers_plaza.farmersplaza.business;

import com.farmers_plaza.farmersplaza.dal.FarmerDao;
import com.farmers_plaza.farmersplaza.models.Farmer;

public class FarmerBusiness {

    private FarmerDao farmerDao;

    public FarmerBusiness(){
        this.farmerDao = new FarmerDao();
    }//end public FarmerBusiness()

    public String validateFarmer(Farmer farmer){
        String regExpression;
        regExpression = "([A-z '-]){2,}";
        farmer = trimFarmerValues(farmer);
        if (!farmer.getFirstName().matches(regExpression))
            return "First name contains invalid characters";
        if (!farmer.getMiddleName().matches(regExpression))
            return "Middle name contains invalid characters";
        regExpression = "([A-z '.-]){2,}";
        if (!farmer.getLastName().matches(regExpression))
            return "Last name contains invalid characters";
        regExpression = "([A-z ,-]){2,}";
        if (!farmer.getAddress().matches(regExpression))
            return "Address contains invalid characters";
        regExpression = "(^09)+([0-9]){9}";
        if (!farmer.getPhoneNo().matches(regExpression))
            return "Phone number contains invalid characters";
        farmerDao.registerFarmer(farmer);
        return "Success";
    }//end public String validateFarmer

    private Farmer trimFarmerValues(Farmer farmer){
        farmer.setFirstName(farmer.getFirstName().trim());
        farmer.setMiddleName(farmer.getMiddleName().trim());
        farmer.setLastName(farmer.getLastName().trim());
        return farmer;
    }//end private Farmer trimFarmerValues

}//end public class FarmerBusiness
