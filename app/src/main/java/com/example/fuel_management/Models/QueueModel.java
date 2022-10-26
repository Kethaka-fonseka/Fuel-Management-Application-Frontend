package com.example.fuel_management.Models;

/**
 * Model class for Queue details.
 *
 * @version 1.0
 */
public class QueueModel {
    public String id;
    public String customer;
    public String fillingStation ;
    public String vehicleType ;
    public String arrivalTime ;
    public String deparTime ;
    public String status ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getFillingStation() {
        return fillingStation;
    }

    public void setFillingStation(String fillingStation) {
        this.fillingStation = fillingStation;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDeparTime() {
        return deparTime;
    }

    public void setDeparTime(String deparTime) {
        this.deparTime = deparTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
