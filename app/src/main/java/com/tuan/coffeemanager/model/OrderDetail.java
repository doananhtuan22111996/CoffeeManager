package com.tuan.coffeemanager.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class OrderDetail {
    private String order_detail_id;
    private String date;
    private List<DrinkOrder> drinkOrderList;

    public OrderDetail() {
    }

    public OrderDetail(String order_detail_id, String date, List<DrinkOrder> drinkOrderList) {
        this.order_detail_id = order_detail_id;
        this.date = date;
        this.drinkOrderList = drinkOrderList;
    }

    public String getDate() {
        return date;
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

    public String getOrder_detail_id() {
        return order_detail_id;
    }

    public void setOrder_detail_id(String order_detail_id) {
        this.order_detail_id = order_detail_id;
    }
}
