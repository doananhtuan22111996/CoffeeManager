package com.tuan.coffeemanager.listener;

public interface OnItemClickListener {
    void onItemClickListener(int position);

    interface OnOrderItemClickListener {
        void onItemClickListener(int position);

        void onItemClickBtnListener(int position, int amount);
    }

    interface OnTableClickListener {
        void onItemOpenClickListener(int position);

        void onItemCloseClickListener(int position);
    }
}
