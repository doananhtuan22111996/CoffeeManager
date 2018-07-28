package com.tuan.coffeemanager.feature.pay.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.model.DrinkOrder;

import java.util.List;

public class PayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<DrinkOrder> drinkOrderList;

    public PayAdapter(Context context, List<DrinkOrder> drinkOrderList) {
        this.context = context;
        this.drinkOrderList = drinkOrderList;
    }

    public List<DrinkOrder> getDrinkOrderList() {
        return drinkOrderList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_bill, viewGroup, false);
        return new PayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        DrinkOrder drinkOrder = drinkOrderList.get(i);
        PayViewHolder payViewHolder = new PayViewHolder(viewHolder.itemView);
        payViewHolder.bind(context, drinkOrder);
    }

    @Override
    public int getItemCount() {
        return drinkOrderList != null ? drinkOrderList.size() : 0;
    }
}
