package com.tuan.coffeemanager.feature.pay.presenter;

import android.app.Activity;

import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.interactor.FirebaseDeleteDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.model.OrderDetail;

import java.util.List;

public class PayPresenter implements FirebaseListener.ListDataObjectDoubleListener<OrderDetail, Drink>, FirebaseListener.DeleteListener {

    private ViewListener.ViewlistDataObjectDoubleListener viewlistDataObjectDoubleListener;
    private ViewListener.ViewDeleteListener viewDeleteListener;
    private FirebaseDataApp firebaseDataApp;
    private FirebaseDeleteDataApp firebaseDeleteDataApp;

    public PayPresenter(ViewListener.ViewlistDataObjectDoubleListener viewlistDataObjectDoubleListener, ViewListener.ViewDeleteListener viewDeleteListener) {
        this.viewlistDataObjectDoubleListener = viewlistDataObjectDoubleListener;
        this.viewDeleteListener = viewDeleteListener;
        firebaseDataApp = new FirebaseDataApp(this);
        firebaseDeleteDataApp = new FirebaseDeleteDataApp(this);
    }

    public void getDataOrderDetail(String order_detail_id) {
        firebaseDataApp.getListDataObjectDouble(ConstApp.NODE_ORDER_DETAIL, order_detail_id, ConstApp.NODE_DRINK, OrderDetail.class, Drink.class);
    }

    public void payOrder(Activity activity, String table_id) {
        firebaseDeleteDataApp.deleteData(activity, ConstApp.NODE_ORDER, table_id);
    }

    @Override
    public void getDataSuccess(OrderDetail orderDetail, List<Drink> drinkList) {
        for (int i = 0; i < orderDetail.getDrinkList().size(); i++) {
            Drink drinkOrder = orderDetail.getDrinkList().get(i);
            for (Drink drink : drinkList) {
                if (drinkOrder.getId().equals(drink.getId())) {
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

    @Override
    public void deleteSuccess(String message) {
        viewDeleteListener.deleteSuccess("Pay is success");
    }

    @Override
    public void deleteFailure(String error) {
        viewDeleteListener.deleteFailure(error);
    }
}
