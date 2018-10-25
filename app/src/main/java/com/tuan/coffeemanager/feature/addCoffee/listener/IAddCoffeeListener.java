package com.tuan.coffeemanager.feature.addCoffee.listener;

public interface IAddCoffeeListener {

    interface IAddCoffeePresenterListener {
        void responseSuccess(String error);

        void responseFailure(String error);
    }

    interface IAddCoffeeViewListener {
        void addCoffeeSuccess(String error);

        void addCoffeeFailure(String error);
    }
}
