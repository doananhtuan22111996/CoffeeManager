package com.tuan.coffeemanager.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Drink {

    private String id;
    private String name;
    private String description;
    private String price;
    private String purchases;
    private String uuid;
    private String url;

    public Drink() {
    }

    public Drink(String id, String name, String description, String price, String purchases, String uuid, String url) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.purchases = purchases;
        this.uuid = uuid;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPurchases() {
        return purchases;
    }

    public void setPurchases(String purchases) {
        this.purchases = purchases;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
