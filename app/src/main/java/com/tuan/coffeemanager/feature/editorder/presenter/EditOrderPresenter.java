package com.tuan.coffeemanager.feature.editorder.presenter;

import android.app.Activity;

import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.interactor.FirebasePostDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;

import com.tuan.coffeemanager.model.DrinkOrder;
import com.tuan.coffeemanager.model.OrderDetail;

import java.util.List;

public class EditOrderPresenter implements FirebaseListener.ListDataDoubleListener<OrderDetail, Drink>, FirebaseListener.PostListener {

    private ViewListener.ViewlistDataDoubleListener viewlistDataDoubleListener;
    private ViewListener.ViewDataListener viewDataListener;
    private ViewListener.ViewPostListener viewPostListener;
    private FirebaseDataApp firebaseDataApp;
    private FirebasePostDataApp firebasePostDataApp;

    private String drink_order_id = null;

    public EditOrderPresenter(ViewListener.ViewlistDataDoubleListener viewlistDataDoubleListener, ViewListener.ViewDataListener viewDataListener, ViewListener.ViewPostListener viewPostListener) {
        this.viewlistDataDoubleListener = viewlistDataDoubleListener;
        this.viewDataListener = viewDataListener;
        this.viewPostListener = viewPostListener;
        firebaseDataApp = new FirebaseDataApp(this);
        firebasePostDataApp = new FirebasePostDataApp(this);
    }

    public void getDataOrderDetailDrink(String drink_order_id) {
        this.drink_order_id = drink_order_id;
        firebaseDataApp.getListDataDouble(ConstApp.NODE_ORDER_DETAIL, ConstApp.NODE_DRINK, OrderDetail.class, Drink.class);
    }

    public void editOrderDetail(Activity activity, OrderDetail orderDetail) {
        firebasePostDataApp.editOrderDetail(activity, orderDetail);
    }

    @Override
    public void getDataSuccess(List<OrderDetail> orderDetailList, List<Drink> drinkList) {
        for (OrderDetail orderDetail : orderDetailList) {
            if (orderDetail.getOrder_detail_id().equals(drink_order_id)) {
                for (int i = 0; i < orderDetail.getDrinkOrderList().size(); i++) {
                    DrinkOrder drinkOrder = orderDetail.getDrinkOrderList().get(i);
                    for (Drink drink : drinkList) {
                        if (drinkOrder.getDrink_id().equals(drink.getId())) {
                            drinkOrder.setPrice(drink.getPrice());
                            drinkOrder.setUrl(drink.getUrl());
                            drinkOrder.setUuid(drink.getUuid());
                            drinkOrder.setName(drink.getName());
                            break;
                        }
                    }
                }
                viewDataListener.onSuccess(orderDetail);
                viewlistDataDoubleListener.onSuccess(orderDetail.getDrinkOrderList(), drinkList);
                break;
            }
        }
    }

    @Override
    public void getDataFailure(String error) {
        viewlistDataDoubleListener.onFailure(error);
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
