package com.tuan.coffeemanager.model;

import java.util.List;

public class OrderDetail {
    private String id;
    private String date;
    private List<DrinkOrder> drinkOrderList;

    public OrderDetail() {
    }

    public OrderDetail(String id, String date, List<DrinkOrder> drinkOrderList) {
        this.id = id;
        this.date = date;
        this.drinkOrderList = drinkOrderList;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<DrinkOrder> getDrinkOrderList() {
        return drinkOrderList;
    }

    public void setDrinkOrderList(List<DrinkOrder> drinkOrderList) {
        this.drinkOrderList = drinkOrderList;
    }
}
