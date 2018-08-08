package com.tuan.coffeemanager.model;

public class Drink {

    private String id;
    private String name;
    private String description;
    private String price;
    private String uuid;
    private String url;
    private Boolean isStatus;

    public Drink() {
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

    public Boolean getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(Boolean status) {
        isStatus = status;
    }
}
