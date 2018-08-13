package com.tuan.coffeemanager.feature.featureManager.revenue.presenter;

import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.model.DrinkOrder;
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
        firebaseDataApp.getListDataDouble(ContactBaseApp.NODE_ORDER_DETAIL, ContactBaseApp.NODE_DRINK, OrderDetail.class, Drink.class);
    }

    public void getDataUser(String user_id) {
        firebaseDataApp.getData(ContactBaseApp.NODE_USER, user_id, User.class);
    }

    @Override
    public void getDataSuccess(List<OrderDetail> orderDetailList, List<Drink> drinkList) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList) {
            if (orderDetail.getIsStatus() == false) {
                for (DrinkOrder drinkOrder : orderDetail.getDrinkOrderList()) {
                    for (int i = 0; i < drinkList.size(); i++) {
                        if (drinkOrder.getDrink_id().equals(drinkList.get(i).getId())) {
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
