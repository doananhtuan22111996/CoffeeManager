package com.tuan.coffeemanager.feature.featureManager.tableManager.presenter;

import android.app.Activity;

import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.interactor.FirebasePostDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Table;

import java.util.List;

public class TableManagerPresenter implements FirebaseListener.ListDataListener<Table>, FirebaseListener.PostListener {

    private FirebaseDataApp firebaseDataApp;
    private FirebasePostDataApp firebasePostDataApp;
    private ViewListener.ViewListDataListener viewListDataListener;
    private ViewListener.ViewPostListener viewPostListener;

    public TableManagerPresenter(ViewListener.ViewListDataListener viewListDataListener) {
        this.viewListDataListener = viewListDataListener;
        firebaseDataApp = new FirebaseDataApp(this);
    }

    public TableManagerPresenter(ViewListener.ViewListDataListener viewListDataListener, ViewListener.ViewPostListener viewPostListener) {
        this.viewListDataListener = viewListDataListener;
        this.viewPostListener = viewPostListener;
        firebaseDataApp = new FirebaseDataApp(this);
        firebasePostDataApp = new FirebasePostDataApp(this);
    }

    public void gerListTable() {
        firebaseDataApp.getListData(ContactBaseApp.NODE_TABLE, Table.class);
    }

    public void postTable(Activity activity, Table table) {
        firebasePostDataApp.postDataTable(activity, table);
    }

    @Override
    public void getDataSuccess(List<Table> tableList) {
        viewListDataListener.onSuccess(tableList);
    }

    @Override
    public void getDataFailure(String error) {
        viewListDataListener.onFailure(error);
    }

    @Override
    public void postSuccess(String message) {
        viewPostListener.postSuccess(message);
    }

    @Override
    public void postFailure(String error) {
        viewPostListener.postFailure(error);
    }
}
