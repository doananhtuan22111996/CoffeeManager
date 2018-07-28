package com.tuan.coffeemanager.feature.pay.presenter;

import com.google.firebase.database.FirebaseDatabase;
import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.model.DrinkOrder;
import com.tuan.coffeemanager.model.OrderDetail;

import java.util.List;

public class PayPresenter implements FirebaseListener.ListDataObjectDoubleListener<OrderDetail, Drink> {

    private ViewListener.ViewlistDataObjectDoubleListener viewlistDataObjectDoubleListener;
    private FirebaseDataApp firebaseDataApp;

    public PayPresenter(ViewListener.ViewlistDataObjectDoubleListener viewlistDataObjectDoubleListener) {
        this.viewlistDataObjectDoubleListener = viewlistDataObjectDoubleListener;
        firebaseDataApp = new FirebaseDataApp(this);
    }

    public void getDataOrderDetail(String order_detail_id) {
        firebaseDataApp.getListDataObjectDouble(ContactBaseApp.NODE_ORDER_DETAIL, order_detail_id, ContactBaseApp.NODE_DRINK, OrderDetail.class, Drink.class);
    }

    @Override
    public void getDataSuccess(OrderDetail orderDetail, List<Drink> drinkList) {
        for (int i = 0; i < orderDetail.getDrinkOrderList().size(); i++) {
            DrinkOrder drinkOrder = orderDetail.getDrinkOrderList().get(i);
            for (Drink drink : drinkList) {
                if (drinkOrder.getDrink_id().equals(drink.getId())) {
                    drinkOrder.setPurchases(drink.getPurchases());
                    drinkOrder.setPrice(drink.getPrice());
                    drinkOrder.setUrl(drink.getUrl());
                    drinkOrder.setUuid(drink.getUuid());
                    drinkOrder.setName(drink.getName());
                    break;
                }
            }
        }
        viewlistDataObjectDoubleListener.onSuccess(orderDetail, drinkList);
    }

    @Override
    public void getDataFailure(String error) {
        viewlistDataObjectDoubleListener.onFailure(error);
    }
}
