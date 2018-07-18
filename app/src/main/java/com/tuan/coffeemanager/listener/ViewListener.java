package com.tuan.coffeemanager.listener;

import java.util.List;

public interface ViewListener {

    interface ViewListDataListener<T> {
        void onSuccess(List<T> tList);

        void onFailure(String error);
    }

    interface ViewDataListener<T> {
        void onSuccess(T t);

        void onFailure(String error);
    }

    interface ViewSignInListener {
        void onSuccess(String id);

        void onFailure(String error);
    }
}
