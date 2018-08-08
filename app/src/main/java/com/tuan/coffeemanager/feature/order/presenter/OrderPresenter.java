package com.tuan.coffeemanager.feature.order.presenter;

import android.app.Activity;

import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.interactor.FirebasePostDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.model.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class OrderPresenter implements FirebaseListener.ListDataListener<Drink>, FirebaseListener.PostListener {

    private ViewListener.ViewListDataListener viewListDataListener;
    private ViewListener.ViewPostListener viewPostListener;
    private FirebaseDataApp firebaseDataApp;
    private FirebasePostDataApp firebasePostDataApp;

    public OrderPresenter(ViewListener.ViewListDataListener viewListDataListener, ViewListener.ViewPostListener viewPostListener) {
        this.viewListDataListener = viewListDataListener;
        this.viewPostListener = viewPostListener;
        firebaseDataApp = new FirebaseDataApp(this);
        firebasePostDataApp = new FirebasePostDataApp(this);
    }

    public void getListDataDrink() {
        firebaseDataApp.getListData(ContactBaseApp.NODE_DRINK, Drink.class);
    }

    public void postDataOrder(Activity activity, OrderDetail orderDetail, String table_id) {
        firebasePostDataApp.postDataOrderDetail(activity, orderDetail, table_id);
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

    @Override
    public void postSuccess(String message) {
        viewPostListener.postSuccess(message);
    }

    @Override
    public void postFailure(String error) {
        viewPostListener.postFailure(error);
    }
}
