package com.tuan.coffeemanager.feature.coffee.fragment.presenter;

import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Order;
import com.tuan.coffeemanager.model.Table;

import java.util.List;

public class TableCoffeePresenter implements FirebaseListener.ListDataDoubleListener<Table, Order> {

    private FirebaseDataApp firebaseDataApp;
    private ViewListener.ViewlistDataDoubleListener viewlistDataDoubleListener;
    private ViewListener.ViewListDataListener viewListDataListener;

    public TableCoffeePresenter(ViewListener.ViewlistDataDoubleListener viewlistDataDoubleListener, ViewListener.ViewListDataListener viewListDataListener) {
        this.viewlistDataDoubleListener = viewlistDataDoubleListener;
        this.viewListDataListener = viewListDataListener;
        firebaseDataApp = new FirebaseDataApp(this);

    }

    public void getTableOrderListData() {
        firebaseDataApp.getListDataDouble(ContactBaseApp.NODE_TABLE, ContactBaseApp.NODE_ORDER, Table.class, Order.class);
    }

    @Override
    public void getDataSuccess(List<Table> tableList, List<Order> orderList) {
        if (orderList.isEmpty()) {
            viewListDataListener.onSuccess(tableList);
        } else {
            viewlistDataDoubleListener.onSuccess(tableList, orderList);
        }
    }

    @Override
    public void getDataFailure(String error) {
        viewlistDataDoubleListener.onFailure(error);
    }
}
