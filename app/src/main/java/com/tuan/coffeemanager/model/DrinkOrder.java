package com.tuan.coffeemanager.model;

import java.io.Serializable;

public class DrinkOrder implements Serializable {

    private String drink_id;
    private String amount;
    private String name;
    private String price;
    private Boolean isStatus_detail;
    private String uuid;
    private String url;

    public DrinkOrder() {
    }

    public DrinkOrder(String drink_id, String amount, Boolean isStatus_detail) {
        this.drink_id = drink_id;
        this.amount = amount;
        this.isStatus_detail = isStatus_detail;
    }

    public DrinkOrder(String drink_id, String name, String price, String uuid, String url, String amount, Boolean isStatus_detail) {
        this.drink_id = drink_id;
        this.name = name;
        this.price = price;
        this.uuid = uuid;
        this.url = url;
        this.amount = amount;
        this.isStatus_detail = isStatus_detail;
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

    public Boolean getIsStatus_detail() {
        return isStatus_detail;
    }

    public void setIsStatus_detail(Boolean status_detail) {
        isStatus_detail = status_detail;
    }
}
