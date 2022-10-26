package com.example.fuel_management.Models;

/**
 * This DTO class contain information vehicle details
 *
 * @version 1.0
 */
public class VehicleTypeDTO {

    String vehicleType;

    int total;

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
