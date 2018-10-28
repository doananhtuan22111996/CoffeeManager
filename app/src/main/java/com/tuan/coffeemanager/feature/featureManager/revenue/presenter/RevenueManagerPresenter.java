package com.tuan.coffeemanager.feature.featureManager.revenue.presenter;

import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.model.OrderDetail;
import com.tuan.coffeemanager.model.User;

import java.util.ArrayList;
import java.util.List;

public class RevenueManagerPresenter implements FirebaseListener.ListDataDoubleListener<OrderDetail, Drink>, FirebaseListener.DataListener<User> {

    private ViewListener.ViewListDataListener viewListDataListener;
    private ViewListener.ViewDataListener viewDataListener;
    private FirebaseDataApp firebaseDataApp;

    public RevenueManagerPresenter(ViewListener.ViewListDataListener viewListDataListener, ViewListener.ViewDataListener viewDataListener) {
        this.viewListDataListener = viewListDataListener;
        this.viewDataListener = viewDataListener;
        this.firebaseDataApp = new FirebaseDataApp(this, this);
    }

    public void getDataBill() {
        firebaseDataApp.getListDataDouble(ConstApp.NODE_ORDER_DETAIL, ConstApp.NODE_DRINK, OrderDetail.class, Drink.class);
    }

    public void getDataUser(String user_id) {
        firebaseDataApp.getData(ConstApp.NODE_USER, user_id, User.class);
    }

    @Override
    public void getDataSuccess(List<OrderDetail> orderDetailList, List<Drink> drinkList) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList) {
            if (orderDetail.getStatus() == true) {
                for (Drink drinkOrder : orderDetail.getDrinkList()) {
                    for (int i = 0; i < drinkList.size(); i++) {
                        if (drinkOrder.getId().equals(drinkList.get(i).getId())) {
                            drinkOrder.setPrice(drinkList.get(i).getPrice());
                            drinkOrder.setUuid(drinkList.get(i).getUuid());
                            drinkOrder.setUrl(drinkList.get(i).getUrl());
                            break;
                        }
                    }
                }
                orderDetails.add(orderDetail);
            }
        }
        viewListDataListener.onSuccess(orderDetails);
    }

    @Override
    public void getDataSuccess(User user) {
        viewDataListener.onSuccess(user);
    }

    @Override
    public void getDataFailure(String error) {
        viewListDataListener.onFailure(error);
    }
}
