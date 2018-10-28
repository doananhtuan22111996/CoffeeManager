package com.tuan.coffeemanager.feature.order.presenter;

import com.tuan.coffeemanager.feature.order.interactor.OrderInteractor;
import com.tuan.coffeemanager.feature.order.listener.IOrderListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.model.OrderDetail;

import java.util.List;

public class OrderPresenter implements IOrderListener.IOrderPresenterListener {

    private IOrderListener.IOrderViewListener iOrderViewListener;
    private OrderInteractor orderInteractor;

    public OrderPresenter(IOrderListener.IOrderViewListener iOrderViewListener) {
        this.iOrderViewListener = iOrderViewListener;
        orderInteractor = new OrderInteractor(this);
    }

    public void requestDrinkCoffee() {
        orderInteractor.getDrinkCoffee();
    }

    public void requestOrder(OrderDetail orderDetail, String tableId) {
        orderInteractor.orderCoffee(orderDetail, tableId);
    }


    @Override
    public void responseDrinkCoffeeSuccess(List<Drink> drinkList) {
        iOrderViewListener.drinkCoffeeSuccess(drinkList);
    }

    @Override
    public void responseDrinkCoffeeFailure(String error) {
        iOrderViewListener.drinkCoffeeFailure(error);
    }

    @Override
    public void responseOrderSuccess(String error) {
        iOrderViewListener.orderSuccess(error);
    }

    @Override
    public void responseOrderFailure(String error) {
        iOrderViewListener.orderFailure(error);
    }
}
