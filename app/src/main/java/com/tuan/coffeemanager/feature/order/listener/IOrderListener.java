package com.tuan.coffeemanager.feature.order.listener;

public interface IOrderListener {

    interface IOrderPresenterListener {
        void responseSuccess(String error);

        void responseFailure(String error);
    }

    interface IOrderViewListener {
        void orderSuccess(String error);

        void orderFailure(String error);
    }
}
