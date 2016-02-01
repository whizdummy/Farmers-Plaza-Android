package com.farmers_plaza.farmersplaza.models;

/**
 * Created by djfable02 on 2/1/16.
 */
public class FarmAdapter {
    private String farmName;
    private String owner;
    private String objectId;

    public FarmAdapter(String farmName) {
        this.farmName = farmName;
    }

    public FarmAdapter(String objectId, String farmName, String owner) {
        this.farmName = farmName;
        this.owner = owner;
    }

    public String getFarmName() {
        return this.farmName;
    }

    public String getOwner() {
        return this.owner;
    }

    public String getObjectId() {
        return this.objectId;
    }
}
