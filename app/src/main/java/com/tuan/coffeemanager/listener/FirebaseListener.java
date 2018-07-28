package com.tuan.coffeemanager.listener;

import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.model.Order;
import com.tuan.coffeemanager.model.OrderDetail;
import com.tuan.coffeemanager.model.Table;
import com.tuan.coffeemanager.model.User;

import java.util.List;

public interface FirebaseListener {

    interface ListDataListener<T> {
        void getDataSuccess(List<T> tList);

        void getDataFailure(String error);
    }

    interface ListDataDoubleListener<T, K> {
        void getDataSuccess(List<T> tList, List<K> kList);

        void getDataFailure(String error);
    }

    interface ListDataObjectDoubleListener<T, K> {
        void getDataSuccess(T t, List<K> kList);

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

        void signInSuccess(String id);

        void signInFailure(String error);
    }

    interface PostListener {
        void postSuccess(String message);

        void postFailure(String error);
    }

    interface PostImageListener {
        void postImageSuccess(String uuid, String url);

        void postImageFailure(String error);
    }

    interface DeleteListener {
        void deleteSuccess(String message);

        void deleteFailure(String error);
    }

    interface DeleteImageListener {
        void deleteImageSuccess(String message);

        void deleteImageFailure(String error);
    }

}
