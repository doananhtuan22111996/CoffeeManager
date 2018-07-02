package com.tuan.coffeemanager.model;

public class Order {

    private String user_id;
    private String order_detail_id;

    public Order() {
    }

    public Order(String user_id, String order_detail_id) {
        this.user_id = user_id;
        this.order_detail_id = order_detail_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getOrder_detail_id() {
        return order_detail_id;
    }
}
