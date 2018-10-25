package com.tuan.coffeemanager.feature.coffeeDetail.presenter;

import android.app.Activity;

import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.interactor.FirebaseDeleteDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;

public class CoffeeDetailPresenter implements FirebaseListener.DataListener<Drink>, FirebaseListener.DeleteListener {

    private FirebaseDataApp firebaseDataApp;
    private FirebaseDeleteDataApp firebaseDeleteDataApp;
    private ViewListener.ViewDataListener viewDataListener;
    private ViewListener.ViewDeleteListener viewDeleteListener;

    public CoffeeDetailPresenter(ViewListener.ViewDataListener viewDataListener, ViewListener.ViewDeleteListener viewDeleteListener) {
        this.viewDataListener = viewDataListener;
        this.viewDeleteListener = viewDeleteListener;
        this.firebaseDataApp = new FirebaseDataApp(this);
        this.firebaseDeleteDataApp = new FirebaseDeleteDataApp(this);
    }

    public void getData(String id) {
        firebaseDataApp.getData(ConstApp.NODE_DRINK, id, Drink.class);
    }

    public void deleteData(Activity activity, String id) {
        firebaseDeleteDataApp.deleteDataStatus(activity, ConstApp.NODE_DRINK, id);
    }

    @Override
    public void getDataSuccess(Drink drink) {
        viewDataListener.onSuccess(drink);
    }

    @Override
    public void getDataFailure(String error) {
        viewDataListener.onFailure(error);
    }

    @Override
    public void deleteSuccess(String message) {
        viewDeleteListener.deleteSuccess(message);
    }

    @Override
    public void deleteFailure(String error) {
        viewDeleteListener.deleteFailure(error);
    }
}
