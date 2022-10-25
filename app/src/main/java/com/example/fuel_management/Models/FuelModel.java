package com.example.fuel_management.Models;

import java.io.Serializable;
import java.util.Date;

public class FuelModel implements Serializable {
    //Name of the fuel type
    public String fuelName;

    //Fuel status
    public String status;


    public String getFuelName() {
        return fuelName;
    }

    public void setFuelName(String fuelName) {
        this.fuelName = fuelName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
