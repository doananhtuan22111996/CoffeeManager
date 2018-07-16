package com.tuan.coffeemanager.listener;

import com.tuan.coffeemanager.model.User;

import java.util.List;

public interface FirebaseListener {

    interface ListDataListener<T> {
        void getDataSuccess(List<T> tList);

        void getDataFailure(String error);
    }

    interface DataListener<T> {
        void getDataSuccess(T t);

        void getDataFailure(String error);
    }

    interface SignUpListener {

        void signUpSuccess(User user);

        void signUpFailure(String error);
    }

    interface SignInListener {

        void signInSuccess(User user);

        void signInFailure(String error);
    }

}
