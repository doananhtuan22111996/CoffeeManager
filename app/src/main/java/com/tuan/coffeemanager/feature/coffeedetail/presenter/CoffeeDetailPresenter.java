package com.tuan.coffeemanager.feature.coffeedetail.presenter;

import com.tuan.coffeemanager.base.FirebaseApp;
import com.tuan.coffeemanager.base.NodeBaseApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;

import java.util.List;

public class CoffeeDetailPresenter implements FirebaseListener.DataListener<Drink> {

    private FirebaseApp firebaseApp;
    private ViewListener.ViewDataListener viewDataListener;

    public CoffeeDetailPresenter(ViewListener.ViewDataListener viewDataListener) {
        FirebaseApp.newIntance();
        this.viewDataListener = viewDataListener;
        this.firebaseApp = new FirebaseApp(this);
    }

    public void getData(String id) {
        firebaseApp.getData(NodeBaseApp.NODE_DRINK, id, Drink.class);
    }


    @Override
    public void getDataSuccess(Drink drink) {
        viewDataListener.onSuccess(drink);
    }

    @Override
    public void getDataFailure(String error) {
        viewDataListener.onFailure(error);
    }
}
