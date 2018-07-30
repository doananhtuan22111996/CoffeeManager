package com.tuan.coffeemanager.feature.coffee.fragment.presenter;

import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;

import java.util.ArrayList;
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
       List<Drink> drinkList = new ArrayList<>();
       for (Drink drink : drinks){
           if (drink.getIsStatus()){
               drinkList.add(drink);
           }
       }
        viewListDataListener.onSuccess(drinkList);
    }

    @Override
    public void getDataFailure(String error) {
        viewListDataListener.onFailure(error);
    }
}
