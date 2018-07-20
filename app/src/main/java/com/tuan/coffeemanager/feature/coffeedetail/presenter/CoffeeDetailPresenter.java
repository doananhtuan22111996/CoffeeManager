package com.tuan.coffeemanager.feature.coffeedetail.presenter;

import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.base.FirebaseDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;

public class CoffeeDetailPresenter implements FirebaseListener.DataListener<Drink> {

    private FirebaseDataApp firebaseDataApp;
    private ViewListener.ViewDataListener viewDataListener;

    public CoffeeDetailPresenter(ViewListener.ViewDataListener viewDataListener) {
        this.viewDataListener = viewDataListener;
        this.firebaseDataApp = new FirebaseDataApp(this);
    }

    public void getData(String id) {
        firebaseDataApp.getData(ContactBaseApp.NODE_DRINK, id, Drink.class);
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
