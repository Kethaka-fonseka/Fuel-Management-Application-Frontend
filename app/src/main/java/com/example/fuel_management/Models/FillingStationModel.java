package com.example.fuel_management.Models;

import java.util.ArrayList;

public class FillingStationModel {

    private String Id;

    //User First Name
    private String name;

    //User Last Name
    private String owner;

    //User User Name
    private String fuelTypes;

    //User Email Address
    private String email;

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

    public String getFuelTypes() {
        return fuelTypes;
    }

    public void setFuelTypes(String fuelTypes) {
        this.fuelTypes = fuelTypes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private static int lastContactId = 0;

    public static ArrayList<FillingStationModel> createContactsList(int numContacts) {
        ArrayList<FillingStationModel> contacts = new ArrayList<FillingStationModel>();

        for (int i = 0; i <= numContacts; i++) {
            contacts.add(new FillingStationModel("panadura","erer","sdsd","sd","sd"));
        }

        return contacts;
    }
}
