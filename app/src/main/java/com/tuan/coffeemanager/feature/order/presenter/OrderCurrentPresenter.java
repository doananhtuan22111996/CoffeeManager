package com.tuan.coffeemanager.feature.order.presenter;

import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Order;

import java.util.List;

public class OrderCurrentPresenter implements FirebaseListener.ListDataListener<Order> {

    private ViewListener.ViewCurrentBill viewCurrentBill;
    private FirebaseDataApp firebaseDataApp;

    public OrderCurrentPresenter(ViewListener.ViewCurrentBill viewCurrentBill) {
        this.viewCurrentBill = viewCurrentBill;
        firebaseDataApp = new FirebaseDataApp(this);
    }

    public void getCurrentBill() {
        firebaseDataApp.getListData(ContactBaseApp.NODE_ORDER, Order.class);
    }

    @Override
    public void getDataSuccess(List<Order> orders) {
        viewCurrentBill.onSuccess(String.valueOf(orders.size()));
    }

    @Override
    public void getDataFailure(String error) {
        viewCurrentBill.onFailure(error);
    }
}
