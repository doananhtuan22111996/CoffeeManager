package com.tuan.coffeemanager.feature.main.fragment.presenter;

import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.feature.main.fragment.interactor.SignInInteractor;
import com.tuan.coffeemanager.feature.main.fragment.listener.ISignInListener;
import com.tuan.coffeemanager.model.User;

public class SignInPresenter implements ISignInListener.ISignInPresenterListener {

    private SignInInteractor signInInteractor;
    private ISignInListener.ISignInViewListener signInViewListener;

    public SignInPresenter(ISignInListener.ISignInViewListener signInViewListener) {
        this.signInViewListener = signInViewListener;
        signInInteractor = new SignInInteractor(this);
    }

    //Xử lý đăng nhập
    public void signIn(String email, String password) {
        signInInteractor.signIn(email, password);
    }

    @Override
    public void getSuccess(User user) {
        if (user != null) {
            signInViewListener.signInSuccess(user);
        } else {
            signInViewListener.signInFailure(ConstApp.SIGN_IN_E004);
        }
    }

    @Override
    public void getFailure(String error) {
        signInViewListener.signInFailure(error);
    }
}
