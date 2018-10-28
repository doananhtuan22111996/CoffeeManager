package com.tuan.coffeemanager.model;

import com.google.firebase.database.PropertyName;

import java.util.List;

public class OrderDetail {
    private String orderId;
    private String userId;
    private String date;
    private Boolean isStatus;
    private List<Drink> drinkList;

    public OrderDetail() {
    }


    public OrderDetail(String orderId, String userId, String date, Boolean isStatus, List<Drink> drinkList) {
        this.orderId = orderId;
        this.userId = userId;
        this.date = date;
        this.isStatus = isStatus;
        this.drinkList = drinkList;
    }

    public OrderDetail(String userId, String date, Boolean isStatus, List<Drink> drinkList) {
        this.userId = userId;
        this.date = date;
        this.isStatus = isStatus;
        this.drinkList = drinkList;
    }

    @PropertyName("order_id")
    public String getOrderId() {
        return orderId;
    }

    @PropertyName("order_id")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @PropertyName("user_id")
    public String getUserId() {
        return userId;
    }

    @PropertyName("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @PropertyName("date")
    public String getDate() {
        return date;
    }

    @PropertyName("date")
    public void setDate(String date) {
        this.date = date;
    }

    @PropertyName("isStatus")
    public Boolean getStatus() {
        return isStatus;
    }

    @PropertyName("isStatus")
    public void setStatus(Boolean status) {
        isStatus = status;
    }

    @PropertyName("drink_order_list")
    public List<Drink> getDrinkList() {
        return drinkList;
    }

    @PropertyName("drink_order_list")
    public void setDrinkList(List<Drink> drinkList) {
        this.drinkList = drinkList;
    }
}
