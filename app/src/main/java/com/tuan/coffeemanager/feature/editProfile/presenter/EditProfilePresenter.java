package com.tuan.coffeemanager.feature.editProfile.presenter;

import android.app.Activity;

import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.interactor.FirebasePostDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.User;

public class EditProfilePresenter implements FirebaseListener.DataListener<User>, FirebaseListener.PostListener {

    private ViewListener.ViewDataListener viewDataListener;
    private ViewListener.ViewPostListener viewPostListener;
    private FirebaseDataApp firebaseDataApp;
    private FirebasePostDataApp firebasePostDataApp;

    public EditProfilePresenter(ViewListener.ViewDataListener viewDataListener, ViewListener.ViewPostListener viewPostListener) {
        this.viewDataListener = viewDataListener;
        this.viewPostListener = viewPostListener;
        firebaseDataApp = new FirebaseDataApp(this);
        firebasePostDataApp = new FirebasePostDataApp(this);
    }

    public void getDataUser(String id) {
        firebaseDataApp.getData(ContactBaseApp.NODE_USER, id, User.class);
    }

    public void postDataUser(Activity activity, User user) {
        firebasePostDataApp.postDataUser(activity, user);
    }

    @Override
    public void getDataSuccess(User user) {
        viewDataListener.onSuccess(user);
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
}
