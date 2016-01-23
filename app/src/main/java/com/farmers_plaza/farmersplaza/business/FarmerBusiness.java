package com.farmers_plaza.farmersplaza.business;

import com.farmers_plaza.farmersplaza.dal.FarmerDao;

public class FarmerBusiness {

    private FarmerDao farmerDao;

    public FarmerBusiness(){
        this.farmerDao = new FarmerDao();
    }//end public FarmerBusiness()

}//end public class FarmerBusiness
