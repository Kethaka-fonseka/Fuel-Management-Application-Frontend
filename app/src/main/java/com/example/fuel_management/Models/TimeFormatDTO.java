package com.example.fuel_management.Models;

/**
 * DTO class for store time format.
 *
 * @version 1.0
 */
public class TimeFormatDTO {
    public int hours;
    public int minutes;
    public int seconds;

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
