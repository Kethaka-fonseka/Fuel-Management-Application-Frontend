package com.example.fuel_management.Models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Model class for store filling station details.
 *
 * @version 1.0
 */
public class FillingStationModel implements Serializable {

    private String Id;

    private String name;

    private String owner;

    private List<FuelModel> fuelTypes;

    private String location;

    private LocalDateTime  fuelArrivalTime;

    private LocalDateTime  fuelFinishTime;


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

    public LocalDateTime getFuelArrivalTime() {
        return fuelArrivalTime;
    }

    public void setFuelArrivalTime(LocalDateTime fuelArrivalTime) {
        this.fuelArrivalTime = fuelArrivalTime;
    }

    public LocalDateTime getFuelFinishTime() {
        return fuelFinishTime;
    }

    public void setFuelFinishTime(LocalDateTime fuelFinishTime) {
        this.fuelFinishTime = fuelFinishTime;
    }
}
