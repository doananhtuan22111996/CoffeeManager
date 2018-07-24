package com.tuan.coffeemanager.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DrinkOrder {

    private String id;
    private String amount;
    private String name;
    private String price;
    private String purchases;
    private String uuid;
    private String url;

    public DrinkOrder() {
    }

    public DrinkOrder(String id, String amount) {
        this.id = id;
        this.amount = amount;
    }

    public DrinkOrder(String id, String name, String price, String purchases, String uuid, String url, String amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.purchases = purchases;
        this.uuid = uuid;
        this.url = url;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
