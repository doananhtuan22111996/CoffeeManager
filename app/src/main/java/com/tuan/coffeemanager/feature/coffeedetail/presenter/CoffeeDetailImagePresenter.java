package com.tuan.coffeemanager.feature.coffeedetail.presenter;

import android.app.Activity;

import com.tuan.coffeemanager.interactor.FirebaseStorageApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;

public class CoffeeDetailImagePresenter implements FirebaseListener.DeleteImageListener {

    private ViewListener.ViewDeleteImageListener viewDeleteImageListener;
    private FirebaseStorageApp firebaseStorageApp;

    public CoffeeDetailImagePresenter(ViewListener.ViewDeleteImageListener viewDeleteImageListener) {
        this.viewDeleteImageListener = viewDeleteImageListener;
        this.firebaseStorageApp = new FirebaseStorageApp(this);
    }

    public void deleteDataImage(Activity activity, String uuid) {
        firebaseStorageApp.deleteDataImage(activity, uuid);
    }

    @Override
    public void deleteImageSuccess(String message) {
        viewDeleteImageListener.deleteImageSuccess(message);
    }

    @Override
    public void deleteImageFailure(String error) {
        viewDeleteImageListener.deleteImageFailure(error);
    }
}
