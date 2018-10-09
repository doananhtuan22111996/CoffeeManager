package com.tuan.coffeemanager.model;


import com.google.firebase.database.PropertyName;

public class User {

    private String id;
    private String name;
    private String position;
    private String address;
    private String birthDay;
    private String phoneNumber;
    private String email;
    private String token;
    private Boolean isStatus;

    public User() {
    }

    public User(String id, String name, String position, String address, String birthDay, String phoneNumber, String email, String token, Boolean isStatus) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.address = address;
        this.birthDay = birthDay;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.token = token;
        this.isStatus = isStatus;
    }

    @PropertyName("id")
    public String getId() {
        return id;
    }

    @PropertyName("id")
    public void setId(String id) {
        this.id = id;
    }

    @PropertyName("name")
    public String getName() {
        return name;
    }

    @PropertyName("name")
    public void setName(String name) {
        this.name = name;
    }

    @PropertyName("position")
    public String getPosition() {
        return position;
    }

    @PropertyName("position")
    public void setPosition(String position) {
        this.position = position;
    }

    @PropertyName("address")
    public String getAddress() {
        return address;
    }

    @PropertyName("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @PropertyName("birth_day")
    public String getBirthDay() {
        return birthDay;
    }

    @PropertyName("birth_day")
    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    @PropertyName("phone_number")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @PropertyName("phone_number")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @PropertyName("email")
    public String getEmail() {
        return email;
    }

    @PropertyName("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @PropertyName("token")
    public String getToken() {
        return token;
    }

    @PropertyName("token")
    public void setToken(String token) {
        this.token = token;
    }

    @PropertyName("isStatus")
    public Boolean getStatus() {
        return isStatus;
    }

    @PropertyName("isStatus")
    public void setStatus(Boolean status) {
        isStatus = status;
    }
}
