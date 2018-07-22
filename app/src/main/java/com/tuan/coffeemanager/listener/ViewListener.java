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

    interface ViewPostListener {
        void postSuccess(String message);

        void postFailure(String error);
    }

    interface ViewPostImageListener {
        void postImageSucces(String uuid, String url);

        void postImageFailure(String error);
    }

    interface ViewDeleteListener {
        void deleteSuccess(String message);

        void deleteFailure(String error);
    }

    interface ViewDeleteImageListener {
        void deleteImageSuccess(String message);

        void deleteImageFailure(String error);
    }

    interface ViewCurrentBill{
        void onSuccess(String index);

        void onFailure(String error);
    }
}
