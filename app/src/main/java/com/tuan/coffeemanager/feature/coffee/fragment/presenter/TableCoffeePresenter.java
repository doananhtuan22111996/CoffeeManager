package com.tuan.coffeemanager.feature.coffee.fragment.presenter;

import com.tuan.coffeemanager.base.FirebaseApp;
import com.tuan.coffeemanager.base.NodeBaseApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Table;

import java.util.List;

public class TableCoffeePresenter implements FirebaseListener.ListDataListener<Table> {

    private FirebaseApp firebaseApp;
    private ViewListener.ViewListDataListener viewListDataListener;

    public TableCoffeePresenter(ViewListener.ViewListDataListener viewListDataListener) {
        FirebaseApp.newIntance();
        this.viewListDataListener = viewListDataListener;
        firebaseApp = new FirebaseApp(this);
    }

    public void getTableListData() {
        firebaseApp.getListData(NodeBaseApp.NODE_TABLE, Table.class);
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
