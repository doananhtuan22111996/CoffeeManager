package com.tuan.coffeemanager.model;

import java.util.List;

public class OrderDetail {
    private String order_detail_id;
    private String user_id;
    private String date;
    private Boolean isStatus;
    private List<DrinkOrder> drinkOrderList;

    public OrderDetail() {
    }

    public OrderDetail(String order_detail_id, String user_id, String date, Boolean isStatus, List<DrinkOrder> drinkOrderList) {
        this.order_detail_id = order_detail_id;
        this.user_id = user_id;
        this.date = date;
        this.isStatus = isStatus;
        this.drinkOrderList = drinkOrderList;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public Boolean getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(Boolean status) {
        isStatus = status;
    }
}
