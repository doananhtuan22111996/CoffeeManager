package com.tuan.coffeemanager.model;

public class Drink {

    private String id;
    private String name;
    private String description;
    private int price;
    private int purchases;
    private String uuid;
    private String url;

    public Drink() {
    }

    public Drink(String id, String name, String description, int price, int purchases, String uuid, String url) {
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

    public int getPrice() {
        return price;
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

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPurchases() {
        return purchases;
    }

    public void setPurchases(int purchases) {
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
