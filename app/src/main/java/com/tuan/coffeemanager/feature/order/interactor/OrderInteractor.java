package com.tuan.coffeemanager.feature.order.interactor;

import com.tuan.coffeemanager.feature.order.listener.IOrderListener;

public class OrderInteractor {

    private IOrderListener.IOrderPresenterListener iOrderPresenterListener;

    public OrderInteractor(IOrderListener.IOrderPresenterListener iOrderPresenterListener) {
        this.iOrderPresenterListener = iOrderPresenterListener;
    }

    public void getCoffee(){

    }

    public void orderCoffee(){

    }
}
