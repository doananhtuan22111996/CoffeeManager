package com.tuan.coffeemanager.feature.coffee.fragment.presenter;

import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.base.FirebaseDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Table;

import java.util.List;

public class TableCoffeePresenter implements FirebaseListener.ListDataListener<Table> {

    private FirebaseDataApp firebaseDataApp;
    private ViewListener.ViewListDataListener viewListDataListener;

    public TableCoffeePresenter(ViewListener.ViewListDataListener viewListDataListener) {
        this.viewListDataListener = viewListDataListener;
        firebaseDataApp = new FirebaseDataApp(this);
    }

    public void getTableListData() {
        firebaseDataApp.getListData(ContactBaseApp.NODE_TABLE, Table.class);
    }

    @Override
    public void getDataSuccess(List<Table> tables) {
        viewListDataListener.onSuccess(tables);
    }

    @Override
    public void getDataFailure(String error) {
        viewListDataListener.onFailure(error);
    }
}
