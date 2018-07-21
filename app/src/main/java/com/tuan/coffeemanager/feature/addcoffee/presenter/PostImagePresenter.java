package com.tuan.coffeemanager.feature.addcoffee.presenter;

import android.app.Activity;
import android.net.Uri;

import com.tuan.coffeemanager.base.FirebaseStorageApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;

public class PostImagePresenter implements FirebaseListener.PostImageListener {

    private ViewListener.ViewPostImageListener viewPostImageListener;
    private FirebaseStorageApp firebaseStorageApp;

    public PostImagePresenter(ViewListener.ViewPostImageListener viewPostImageListener) {
        this.viewPostImageListener = viewPostImageListener;
        firebaseStorageApp = new FirebaseStorageApp(this);
    }

    public void postDataImage(Activity activity, Uri uri) {
        firebaseStorageApp.postDataImage(activity, uri);
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
