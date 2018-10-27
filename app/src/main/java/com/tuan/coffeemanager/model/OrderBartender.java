package com.tuan.coffeemanager.model;

import java.io.Serializable;
import java.util.List;

public class OrderBartender implements Serializable {
    private String table_id;
    private int number;
    private String order_detail_id;
    private String user_id;
    private String date;
    private Boolean isStatus;
    private List<Drink> drinkOrderList;

    public OrderBartender() {
    }

    public OrderBartender(String table_id, int number, String order_detail_id, String user_id, String date, Boolean isStatus, List<Drink> drinkOrderList) {
        this.table_id = table_id;
        this.number = number;
        this.order_detail_id = order_detail_id;
        this.user_id = user_id;
        this.date = date;
        this.isStatus = isStatus;
        this.drinkOrderList = drinkOrderList;
    }

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getOrder_detail_id() {
        return order_detail_id;
    }

    public void setOrder_detail_id(String order_detail_id) {
        this.order_detail_id = order_detail_id;
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

    public Boolean getStatus() {
        return isStatus;
    }

    public void setStatus(Boolean status) {
        isStatus = status;
    }

    public List<Drink> getDrinkOrderList() {
        return drinkOrderList;
    }

    public void setDrinkOrderList(List<Drink> drinkOrderList) {
        this.drinkOrderList = drinkOrderList;
    }
}
