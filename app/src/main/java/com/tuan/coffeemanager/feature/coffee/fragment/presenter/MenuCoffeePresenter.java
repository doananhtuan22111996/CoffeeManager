package com.tuan.coffeemanager.feature.coffee.fragment.presenter;

import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.base.FirebaseDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MenuCoffeePresenter implements FirebaseListener.ListDataListener<Drink> {

    private FirebaseDataApp firebaseDataApp;
    private ViewListener.ViewListDataListener viewListDataListener;

    public MenuCoffeePresenter(ViewListener.ViewListDataListener viewListDataListener) {
        this.viewListDataListener = viewListDataListener;
        this.firebaseDataApp = new FirebaseDataApp(this);
    }

    public void getMenuListData() {
        firebaseDataApp.getListData(ContactBaseApp.NODE_DRINK, Drink.class);
    }

    @Override
    public void getDataSuccess(List<Drink> drinks) {
        Comparator<Drink> comparator = new Comparator<Drink>() {
            @Override
            public int compare(Drink drink, Drink t1) {
                return t1.getPurchases() - drink.getPurchases();
            }
        };
        Collections.sort(drinks, comparator);
        viewListDataListener.onSuccess(drinks);
    }

    @Override
    public void getDataFailure(String error) {
        viewListDataListener.onFailure(error);
    }
}
