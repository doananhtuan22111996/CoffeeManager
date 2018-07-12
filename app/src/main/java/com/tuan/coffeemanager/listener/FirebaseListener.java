package com.tuan.coffeemanager.listener;

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

}
