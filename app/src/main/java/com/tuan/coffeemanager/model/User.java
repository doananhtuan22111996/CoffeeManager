package com.tuan.coffeemanager.model;


public class User {

    private String id;
    private String name;
    private String position;
    private String address;
    private String birth_day;
    private String phone_number;
    private String email;
    private String token;
    private Boolean isStatus;

    public User() {
    }

    public User(String id, String name, String position, String address, String birth_day, String phone_number, String email, String token, Boolean isStatus) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.address = address;
        this.birth_day = birth_day;
        this.phone_number = phone_number;
        this.email = email;
        this.token = token;
        this.isStatus = isStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getAddress() {
        return address;
    }

    public String getBirth_day() {
        return birth_day;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirth_day(String birth_day) {
        this.birth_day = birth_day;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Boolean getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(Boolean status) {
        isStatus = status;
    }
}
