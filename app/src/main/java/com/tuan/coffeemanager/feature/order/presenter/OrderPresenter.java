package com.tuan.coffeemanager.feature.order.presenter;

import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;

import java.util.List;

public class OrderPresenter implements FirebaseListener.ListDataListener<Drink> {

    private ViewListener.ViewListDataListener viewListDataListener;
    private FirebaseDataApp firebaseDataApp;

    public OrderPresenter(ViewListener.ViewListDataListener viewListDataListener) {
        this.viewListDataListener = viewListDataListener;
        firebaseDataApp = new FirebaseDataApp(this);
    }

    public void getListDataDrink() {
        firebaseDataApp.getListData(ContactBaseApp.NODE_DRINK, Drink.class);
    }

    @Override
    public void getDataSuccess(List<Drink> drinks) {
        viewListDataListener.onSuccess(drinks);
    }

    @Override
    public void getDataFailure(String error) {
        viewListDataListener.onFailure(error);
    }
}
