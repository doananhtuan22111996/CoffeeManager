package com.tuan.coffeemanager.feature.main.fragment.presenter;

import android.app.Activity;

import com.tuan.coffeemanager.interactor.FirebaseAuthApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;

public class SignInPresenter implements FirebaseListener.SignInListener {

    private FirebaseAuthApp firebaseAuthApp;
    private ViewListener.ViewSignInListener viewSignInListener;

    public SignInPresenter(ViewListener.ViewSignInListener viewSignInListener) {
        this.viewSignInListener = viewSignInListener;
        firebaseAuthApp = new FirebaseAuthApp(this);
    }

    public void signIn(String email, String password, Activity activity){
        firebaseAuthApp.signInEmail(email, password, activity);
    }

    @Override
    public void signInSuccess(String id) {
        viewSignInListener.onSuccess(id);
    }

    @Override
    public void signInFailure(String error) {
        viewSignInListener.onFailure(error);
    }
}
