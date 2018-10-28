package com.tuan.coffeemanager.model;

import com.google.firebase.database.PropertyName;

public class Order {

    private String tableId;
    private String orderDetailId;

    public Order() {
    }

    public Order(String tableId, String orderDetailId) {
        this.tableId = tableId;
        this.orderDetailId = orderDetailId;
    }

    @PropertyName("table_id")
    public String getTableId() {
        return tableId;
    }

    @PropertyName("table_id")
    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    @PropertyName("order_detail_id")
    public String getOrderDetailId() {
        return orderDetailId;
    }

    @PropertyName("order_detail_id")
    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }
}
