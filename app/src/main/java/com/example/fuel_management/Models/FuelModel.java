package com.example.fuel_management.Models;

import java.util.Date;

public class FuelModel {
    //Name of the fuel type
    public String fuelName;

    //Fuel status
    public String status;

    //Fuel arrival time
    public Date FuelArrivalTime;

    //Fuel finish time
    public Date FuelFinishTime;

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

    public Date getFuelArrivalTime() {
        return FuelArrivalTime;
    }

    public void setFuelArrivalTime(Date fuelArrivalTime) {
        FuelArrivalTime = fuelArrivalTime;
    }

    public Date getFuelFinishTime() {
        return FuelFinishTime;
    }

    public void setFuelFinishTime(Date fuelFinishTime) {
        FuelFinishTime = fuelFinishTime;
    }
}
