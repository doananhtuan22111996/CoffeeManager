package com.tuan.coffeemanager.feature.coffee.fragment.presenter;

import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Order;
import com.tuan.coffeemanager.model.Table;

import java.util.List;

public class TableCoffeePresenter implements FirebaseListener.ListDataTableOrderListener {

    private FirebaseDataApp firebaseDataApp;
    private ViewListener.ViewlistDataTableOrderListener viewlistDataTableOrderListener;
    private ViewListener.ViewListDataListener viewListDataListener;

    public TableCoffeePresenter(ViewListener.ViewlistDataTableOrderListener viewlistDataTableOrderListener, ViewListener.ViewListDataListener viewListDataListener) {
        this.viewlistDataTableOrderListener = viewlistDataTableOrderListener;
        this.viewListDataListener = viewListDataListener;
        firebaseDataApp = new FirebaseDataApp(this);

    }

    public void getTableOrderListData() {
        firebaseDataApp.getDataTableOrder();
    }

    @Override
    public void getDataSuccess(List<Table> tableList, List<Order> orderList) {
        if (orderList.isEmpty()) {
            viewListDataListener.onSuccess(tableList);
        } else {
            viewlistDataTableOrderListener.onSuccess(tableList, orderList);
        }
    }

    @Override
    public void getDataFailure(String error) {
        viewlistDataTableOrderListener.onFailure(error);
    }
}
