package com.tuan.coffeemanager.feature.pay.presenter;

import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.User;

import java.util.List;

public class PayUserPresenter implements FirebaseListener.ListDataListener<User> {

    private ViewListener.ViewDataListener viewDataListener;
    private FirebaseDataApp firebaseDataApp;

    public PayUserPresenter(ViewListener.ViewDataListener viewDataListener) {
        this.viewDataListener = viewDataListener;
        firebaseDataApp = new FirebaseDataApp(this);
    }

    public void getDataManager() {
        firebaseDataApp.getListData(ContactBaseApp.NODE_USER, User.class);
    }

    @Override
    public void getDataSuccess(List<User> users) {
        for (User user : users) {
            if (user.getPosition().equals("manager")) {
                viewDataListener.onSuccess(user);
                break;
            }
        }
    }

    @Override
    public void getDataFailure(String error) {
        viewDataListener.onFailure(error);
    }
}
