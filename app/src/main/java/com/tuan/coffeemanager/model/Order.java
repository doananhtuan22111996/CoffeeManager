package com.tuan.coffeemanager.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Order {

    private String table_id;
    private String order_detail_id;

    public Order() {
    }

    public Order(String table_id, String order_detail_id) {
        this.table_id = table_id;
        this.order_detail_id = order_detail_id;
    }

    public String getOrder_detail_id() {
        return order_detail_id;
    }

    public void setOrder_detail_id(String order_detail_id) {
        this.order_detail_id = order_detail_id;
    }

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }
}
