package com.tuan.coffeemanager.feature.featureManager.signUpEmployee.presenter;

import android.app.Activity;

import com.tuan.coffeemanager.interactor.FirebaseAuthApp;
import com.tuan.coffeemanager.interactor.FirebasePostDataApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.User;

public class SignUpPresenter implements FirebaseListener.SignUpListener, FirebaseListener.PostListener {

    private ViewListener.ViewDataListener viewDataListener;
    private ViewListener.ViewPostListener viewPostListener;
    private FirebaseAuthApp firebaseAuth;
    private FirebasePostDataApp firebasePostDataApp;

    public SignUpPresenter(ViewListener.ViewDataListener viewDataListener, ViewListener.ViewPostListener viewPostListener) {
        this.viewDataListener = viewDataListener;
        this.viewPostListener = viewPostListener;
        firebaseAuth = new FirebaseAuthApp(this);
        firebasePostDataApp = new FirebasePostDataApp(this);
    }

    public void signUp(String email, String password, String name, Activity activity) {
        firebaseAuth.signUpEmail(email, password, name, activity);
    }

    public void postDataUser(Activity activity, User user){
        firebasePostDataApp.postDataUser(activity, user);
    }

    @Override
    public void signUpSuccess(User user) {
        viewDataListener.onSuccess(user);
    }

    @Override
    public void signUpFailure(String error) {
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
