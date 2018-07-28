package com.tuan.coffeemanager.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DrinkOrder {

    private String drink_id;
    private String amount;
    private String name;
    private String price;
    private String purchases;
    private String uuid;
    private String url;

    public DrinkOrder() {
    }

    public DrinkOrder(String drink_id, String amount) {
        this.drink_id = drink_id;
        this.amount = amount;
    }

    public DrinkOrder(String drink_id, String name, String price, String purchases, String uuid, String url, String amount) {
        this.drink_id = drink_id;
        this.name = name;
        this.price = price;
        this.purchases = purchases;
        this.uuid = uuid;
        this.url = url;
        this.amount = amount;
    }

    public String getDrink_id() {
        return drink_id;
    }

    public void setDrink_id(String drink_id) {
        this.drink_id = drink_id;
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
