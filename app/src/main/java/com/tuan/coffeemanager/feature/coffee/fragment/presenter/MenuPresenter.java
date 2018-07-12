package com.tuan.coffeemanager.feature.coffee.fragment.presenter;

import com.tuan.coffeemanager.base.FirebaseApp;
import com.tuan.coffeemanager.base.NodeBaseApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MenuPresenter implements FirebaseListener.ListDataListener<Drink> {

    private FirebaseApp firebaseApp;
    private ViewListener.ViewListDataListener viewListDataListener;

    public MenuPresenter(ViewListener.ViewListDataListener viewListDataListener) {
        FirebaseApp.newIntance();
        this.viewListDataListener = viewListDataListener;
        this.firebaseApp = new FirebaseApp(this);
    }

    public void getMenuData() {
        firebaseApp.getListData(NodeBaseApp.NODE_DRINK, Drink.class);
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
