package com.example.fuel_management.Models;

/**
 * This model contain information related to the user
 *
 * @version 1.0
 */
public class UserModel {

    //User ID
    private String Id;

    //User First Name
    private String firstName;

    //User Last Name
    private String lastName;

    //User User Name
    private String userName;

    //User Email Address
    private String email;

    //User Type
    private String type;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
