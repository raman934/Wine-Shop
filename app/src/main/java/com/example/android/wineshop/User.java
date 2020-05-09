package com.example.android.wineshop;
public class User {
    private String firstName;
    private String lastName;
    private String location;
    private String contact;
    private String coordinates, address;
    private String userId;
    private String email;
    Boolean isowner;
    Boolean valid;

    public User() {

    }

    public User(String firstName, String lastName, String location, String contact, String coordinates, String address, String userId, String email, Boolean isowner) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.contact = contact;
        this.coordinates = coordinates;
        this.address = address;
        this.userId = userId;
        this.email = email;
        this.isowner = isowner;
        this.valid = false;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLocation() {
        return location;
    }

    public String getContact() {
        return contact;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getAddress() {
        return address;
    }

    public String getuserId() {
        return userId;
    }

    public String getmail() {
        return email;
    }

}