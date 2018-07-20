package com.tuan.coffeemanager.feature.addcoffee.presenter;

import android.app.Activity;

import com.tuan.coffeemanager.base.FirebasePostDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;

public class PostCoffeePresenter implements FirebaseListener.PostListener {

    private ViewListener.ViewPostListener postListener;
    private FirebasePostDataApp firebasePostDataApp;

    public PostCoffeePresenter(ViewListener.ViewPostListener postListener) {
        this.postListener = postListener;
        firebasePostDataApp = new FirebasePostDataApp(this);
    }

    public void postDataDrink(Activity activity, Drink drink) {
        firebasePostDataApp.postDataDrink(activity, drink);
    }

    @Override
    public void postSuccess(String message) {
        postListener.postSuccess(message);
    }

    @Override
    public void postFailure(String error) {
        postListener.postFailure(error);
    }
}
