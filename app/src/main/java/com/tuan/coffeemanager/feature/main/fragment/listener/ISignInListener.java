package com.tuan.coffeemanager.feature.main.fragment.listener;

import com.tuan.coffeemanager.model.User;

public interface ISignInListener {

    interface ISignInPresenterListener {
        void getSuccess(User user);

        void getFailure(String error);
    }

    interface ISignInViewListener {
        void signInSuccess(User user);

        void signInFailure(String error);
    }
}
