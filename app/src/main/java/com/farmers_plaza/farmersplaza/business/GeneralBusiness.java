package com.farmers_plaza.farmersplaza.business;

import com.parse.ParseUser;

public class GeneralBusiness {

    public static boolean isUserAuthenticated() {
        return ParseUser.getCurrentUser().isAuthenticated();
    }
}
