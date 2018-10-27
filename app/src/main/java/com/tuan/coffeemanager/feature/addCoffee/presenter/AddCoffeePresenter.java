package com.tuan.coffeemanager.feature.addCoffee.presenter;

import android.net.Uri;

import com.tuan.coffeemanager.feature.addCoffee.interactor.AddCoffeeInteractor;
import com.tuan.coffeemanager.feature.addCoffee.listener.IAddCoffeeListener;
import com.tuan.coffeemanager.model.Drink;

public class AddCoffeePresenter implements IAddCoffeeListener.IAddCoffeePresenterListener {

    private IAddCoffeeListener.IAddCoffeeViewListener iAddCoffeeViewListener;
    private AddCoffeeInteractor addCoffeeInteractor;

    public AddCoffeePresenter(IAddCoffeeListener.IAddCoffeeViewListener iAddCoffeeViewListener) {
        this.iAddCoffeeViewListener = iAddCoffeeViewListener;
        addCoffeeInteractor = new AddCoffeeInteractor(this);
    }

    public void requestAddCoffee(Drink drink, Uri uri) {
        addCoffeeInteractor.addCoffee(drink, uri);
    }

    @Override
    public void responseSuccess(String error) {
        iAddCoffeeViewListener.addCoffeeSuccess(error);
    }

    @Override
    public void responseFailure(String error) {
        iAddCoffeeViewListener.addCoffeeFailure(error);
    }
}
