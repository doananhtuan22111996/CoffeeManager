package com.tuan.coffeemanager.feature.order.listener;

public interface IOnItemClickListener {

    void onRemoveItemClickListener(int position);

    void onChangeAmountItemClickListener(int position, int amount);
}
