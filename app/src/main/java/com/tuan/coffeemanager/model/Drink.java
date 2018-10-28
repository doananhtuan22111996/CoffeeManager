package com.tuan.coffeemanager.model;

import com.google.firebase.database.PropertyName;

public class Drink {

    private String id;
    private String name;
    private String description;
    private String price;
    private String uuid;
    private String url;
    private Boolean isStatus;
    private int amount;

    public Drink() {
    }

    public Drink(String name, String description, String price, Boolean isStatus) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isStatus = isStatus;
    }

    public Drink(String id, String name, String description, String price, String uuid, String url, Boolean isStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.uuid = uuid;
        this.url = url;
        this.isStatus = isStatus;
    }

    public Drink(String id, String name, String description, String price, String uuid, String url, Boolean isStatus, int amount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.uuid = uuid;
        this.url = url;
        this.isStatus = isStatus;
        this.amount = amount;
    }

    public Drink(String id, Boolean isStatus, int amount) {
        this.id = id;
        this.isStatus = isStatus;
        this.amount = amount;
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

    @PropertyName("description")
    public String getDescription() {
        return description;
    }

    @PropertyName("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @PropertyName("price")
    public String getPrice() {
        return price;
    }

    @PropertyName("price")
    public void setPrice(String price) {
        this.price = price;
    }

    @PropertyName("uuid")
    public String getUuid() {
        return uuid;
    }

    @PropertyName("uuid")
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @PropertyName("url")
    public String getUrl() {
        return url;
    }

    @PropertyName("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @PropertyName("isStatus")
    public Boolean getStatus() {
        return isStatus;
    }

    @PropertyName("isStatus")
    public void setStatus(Boolean status) {
        isStatus = status;
    }

    @PropertyName("amount")
    public int getAmount() {
        return amount;
    }

    @PropertyName("amount")
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
