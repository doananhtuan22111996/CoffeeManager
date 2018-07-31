package com.tuan.coffeemanager.feature.main.reset.presenter;

import android.app.Activity;

import com.tuan.coffeemanager.interactor.FirebaseAuthApp;
import com.tuan.coffeemanager.listener.FirebaseListener;
import com.tuan.coffeemanager.listener.ViewListener;

public class ResetPasswordPresenter implements FirebaseListener.ResetPasswordListener {

    private ViewListener.ViewResetPasswordListener viewResetPasswordListener;
    private FirebaseAuthApp firebaseAuthApp;

    public ResetPasswordPresenter(ViewListener.ViewResetPasswordListener viewResetPasswordListener) {
        this.viewResetPasswordListener = viewResetPasswordListener;
        firebaseAuthApp = new FirebaseAuthApp(this);
    }

    public void resetPassword(Activity activity, String email) {
        firebaseAuthApp.resetPassword(activity, email);
    }

    @Override
    public void resetSuccess(String message) {
        viewResetPasswordListener.onSuccess(message);
    }

    @Override
    public void resetFailure(String error) {
        viewResetPasswordListener.onFailure(error);
    }
}
