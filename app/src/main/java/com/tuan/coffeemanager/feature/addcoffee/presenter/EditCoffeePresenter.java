package com.tuan.coffeemanager.feature.addcoffee.presenter;

import com.tuan.coffeemanager.base.FirebaseDataApp;
import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;

public class EditCoffeePresenter implements FirebaseListener.DataListener<Drink> {

    private FirebaseDataApp firebaseDataApp;
    private ViewListener.ViewDataListener viewDataListener;

    public EditCoffeePresenter(ViewListener.ViewDataListener viewDataListener) {
        FirebaseDataApp.newIntance();
        this.viewDataListener = viewDataListener;
        firebaseDataApp = new FirebaseDataApp(this);
    }

    public void getDataDrink(String id) {
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
