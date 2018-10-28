package com.tuan.coffeemanager.feature.order.listener;

import com.tuan.coffeemanager.model.Drink;

import java.util.List;

public interface IOrderListener {

    interface IOrderPresenterListener {
        void responseDrinkCoffeeSuccess(List<Drink> drinkList);

        void responseDrinkCoffeeFailure(String error);

        void responseOrderSuccess(String error);

        void responseOrderFailure(String error);
    }

    interface IOrderViewListener {
        void drinkCoffeeSuccess(List<Drink> drinkList);

        void drinkCoffeeFailure(String error);

        void orderSuccess(String error);

        void orderFailure(String error);
    }
}
