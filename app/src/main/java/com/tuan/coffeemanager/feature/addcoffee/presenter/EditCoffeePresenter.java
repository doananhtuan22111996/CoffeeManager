package com.tuan.coffeemanager.feature.addcoffee.presenter;

import android.app.Activity;
import android.net.Uri;

import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.interactor.FirebasePostDataApp;
import com.tuan.coffeemanager.interactor.FirebaseStorageApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;

public class EditCoffeePresenter implements FirebaseListener.DataListener<Drink>, FirebaseListener.PostListener, FirebaseListener.PostImageListener {

    private FirebaseDataApp firebaseDataApp;
    private FirebasePostDataApp firebasePostDataApp;
    private FirebaseStorageApp firebaseStorageApp;
    private ViewListener.ViewDataListener viewDataListener;
    private ViewListener.ViewPostListener viewPostListener;
    private ViewListener.ViewPostImageListener viewPostImageListener;

    public EditCoffeePresenter(ViewListener.ViewDataListener viewDataListener, ViewListener.ViewPostListener viewPostListener, ViewListener.ViewPostImageListener viewPostImageListener) {
        this.viewDataListener = viewDataListener;
        this.viewPostListener = viewPostListener;
        this.viewPostImageListener = viewPostImageListener;
        firebaseDataApp = new FirebaseDataApp(this);
        firebasePostDataApp = new FirebasePostDataApp(this);
        firebaseStorageApp = new FirebaseStorageApp(this);
    }

    public void getDataDrink(String id) {
        firebaseDataApp.getData(ConstApp.NODE_DRINK, id, Drink.class);
    }

    public void editDataDrink(Activity activity, Drink drink) {
        firebasePostDataApp.editDataDrink(activity, drink);
    }

    public void editDataImage(Activity activity, Uri uri, String uuid){
        firebaseStorageApp.editDataImage(activity, uri, uuid);
    }

    @Override
    public void getDataSuccess(Drink drink) {
        viewDataListener.onSuccess(drink);
    }

    @Override
    public void getDataFailure(String error) {
        viewDataListener.onFailure(error);
    }

    @Override
    public void postSuccess(String message) {
        viewPostListener.postSuccess(message);
    }

    @Override
    public void postFailure(String error) {
        viewPostListener.postFailure(error);
    }

    @Override
    public void postImageSuccess(String uuid, String url) {
        viewPostImageListener.postImageSucces(uuid, url);
    }

    @Override
    public void postImageFailure(String error) {
        viewPostImageListener.postImageFailure(error);
    }
}
