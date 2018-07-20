package com.tuan.coffeemanager.feature.addcoffee.presenter;

import android.app.Activity;

import com.tuan.coffeemanager.base.FirebaseDataApp;
import com.tuan.coffeemanager.base.FirebasePostDataApp;
import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;

public class EditCoffeePresenter implements FirebaseListener.DataListener<Drink>, FirebaseListener.PostListener {

    private FirebaseDataApp firebaseDataApp;
    private FirebasePostDataApp firebasePostDataApp;
    private ViewListener.ViewDataListener viewDataListener;
    private ViewListener.ViewPostListener viewPostListener;

    public EditCoffeePresenter(ViewListener.ViewDataListener viewDataListener, ViewListener.ViewPostListener viewPostListener) {
        this.viewDataListener = viewDataListener;
        this.viewPostListener = viewPostListener;
        firebaseDataApp = new FirebaseDataApp(this);
        firebasePostDataApp = new FirebasePostDataApp(this);
    }

    public void getDataDrink(String id) {
        firebaseDataApp.getData(ContactBaseApp.NODE_DRINK, id, Drink.class);
    }

    public void editDataDrink(Activity activity, Drink drink){
        firebasePostDataApp.editDataDrink(activity, drink);
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
    public void postSuccess(String message) {
        viewPostListener.postSuccess(message);
    }

    @Override
    public void postFailure(String error) {
        viewPostListener.postFailure(error);
    }
}
