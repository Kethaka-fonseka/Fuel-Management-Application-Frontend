package com.example.fuel_management.Models;

import java.util.List;

public class FillingStationModel {

    private String Id;

    private String name;

    private String owner;

    private List<FuelModel> fuelTypes;

    private String location;

    //User Type
    private String type;

    public FillingStationModel( String name, String owner, String fuelTypes, String email, String type) {
        this.name = name;
        this.owner = owner;
        this.fuelTypes = fuelTypes;
        this.email = email;
        this.type = type;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<FuelModel> getFuelTypes() {
        return fuelTypes;
    }

    public void setFuelTypes(List<FuelModel> fuelTypes) {
        this.fuelTypes = fuelTypes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
