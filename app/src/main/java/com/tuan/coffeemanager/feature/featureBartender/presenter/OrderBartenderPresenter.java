package com.tuan.coffeemanager.feature.featureBartender.presenter;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.model.Order;
import com.tuan.coffeemanager.model.OrderBartender;
import com.tuan.coffeemanager.model.OrderDetail;
import com.tuan.coffeemanager.model.Table;

import java.util.ArrayList;
import java.util.List;

public class OrderBartenderPresenter {

    private List<Table> tableList;
    private List<OrderBartender> orderBartenderList;
    private List<Drink> drinkList;
    private List<Order> orderList;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private ViewListener.ViewListDataListener viewListDataListener;

    public OrderBartenderPresenter(ViewListener.ViewListDataListener viewListDataListener) {
        this.viewListDataListener = viewListDataListener;
    }

    public void getListBillBartender() {
        getListTable();

    }

    private void getListTable() {
        databaseReference.child(ConstApp.NODE_TABLE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tableList = new ArrayList<>();
                for (DataSnapshot value : dataSnapshot.getChildren()) {
                    Table table = value.getValue(Table.class);
                    tableList.add(table);
                }
                getListOrder();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                viewListDataListener.onFailure(databaseError.getMessage());
            }
        });
    }

    private void getListOrder() {
        databaseReference.child(ConstApp.NODE_ORDER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderList = new ArrayList<>();
                for (DataSnapshot value : dataSnapshot.getChildren()) {
                    Order order = value.getValue(Order.class);
                    orderList.add(order);
                }
                getListOrderDetail();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                viewListDataListener.onFailure(databaseError.getMessage());
            }
        });
    }

    private void getListOrderDetail() {
        databaseReference.child(ConstApp.NODE_ORDER_DETAIL).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderBartenderList = new ArrayList<>();
                for (DataSnapshot value : dataSnapshot.getChildren()) {
                    OrderDetail orderDetail = value.getValue(OrderDetail.class);
                    if (orderDetail != null && orderDetail.getIsStatus() == true) {
                        for (Order order : orderList) {
                            if (order.getOrder_detail_id().equals(orderDetail.getOrder_detail_id())) {
                                OrderBartender orderBartender = new OrderBartender();
                                orderBartender.setOrder_detail_id(orderDetail.getOrder_detail_id());
                                orderBartender.setDate(orderDetail.getDate());
                                orderBartender.setDrinkOrderList(orderDetail.getDrinkOrderList());
                                orderBartender.setUser_id(orderDetail.getUser_id());
                                orderBartender.setStatus(true);
                                orderBartender.setTable_id(order.getTable_id());
                                orderBartenderList.add(orderBartender);
                            }
                        }
                    }
                }
                getOrderBartender();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                viewListDataListener.onFailure(databaseError.getMessage());
            }
        });
    }

    private void getOrderBartender() {
        for (OrderBartender orderBartender : orderBartenderList) {
            for (Table table : tableList) {
                if (orderBartender.getTable_id().equals(table.getId())) {
                    orderBartender.setNumber(table.getNumber());
                }
            }
        }
        getListDrink();
    }

    private void getListDrink() {
        databaseReference.child(ConstApp.NODE_DRINK).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                drinkList = new ArrayList<>();
                for (DataSnapshot value : dataSnapshot.getChildren()) {
                    Drink drink = value.getValue(Drink.class);
                    drinkList.add(drink);
                }
                for (OrderBartender orderBartender : orderBartenderList) {
                    for (int i = 0; i < orderBartender.getDrinkOrderList().size(); i++) {
                        DrinkOrder drinkOrder = orderBartender.getDrinkOrderList().get(i);
                        for (Drink drink : drinkList) {
                            if (drinkOrder.getDrink_id().equals(drink.getId())) {
                                drinkOrder.setPrice(drink.getPrice());
                                drinkOrder.setUrl(drink.getUrl());
                                drinkOrder.setUuid(drink.getUuid());
                                drinkOrder.setName(drink.getName());
                                break;
                            }
                        }
                    }
                }
                viewListDataListener.onSuccess(orderBartenderList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                viewListDataListener.onFailure(databaseError.getMessage());
            }
        });
    }
}