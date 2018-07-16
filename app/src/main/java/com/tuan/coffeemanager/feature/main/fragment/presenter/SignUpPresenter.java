package com.tuan.coffeemanager.feature.main.fragment.presenter;

import android.app.Activity;

import com.tuan.coffeemanager.base.FirebaseAuthApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.User;

public class SignUpPresenter implements FirebaseListener.SignUpListener {

    private ViewListener.ViewDataListener viewDataListener;
    private FirebaseAuthApp firebaseAuth;

    public SignUpPresenter(ViewListener.ViewDataListener viewDataListener) {
        FirebaseAuthApp.newInstance();
        this.viewDataListener = viewDataListener;
        firebaseAuth = new FirebaseAuthApp(this);
    }

    public void signUp(String email, String password, Activity activity) {
        firebaseAuth.signUpEmail(email, password, activity);
    }

    @Override
    public void signUpSuccess(User user) {
        viewDataListener.onSuccess(user);
    }

    @Override
    public void signUpFailure(String error) {
        viewDataListener.onFailure(error);
    }
}
