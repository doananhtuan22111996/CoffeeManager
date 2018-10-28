package com.tuan.coffeemanager.feature.pay.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.ext.GlideExt;
import com.tuan.coffeemanager.model.Drink;

public class PayViewHolder extends RecyclerView.ViewHolder {

    private ImageView ivCoffee;
    private TextView tvNameCoffee;
    private ImageView ivCheck;
    private TextView tvPrice;
    private TextView tvAmount;

    PayViewHolder(@NonNull View itemView) {
        super(itemView);
        ivCoffee = itemView.findViewById(R.id.ivCoffee);
        tvNameCoffee = itemView.findViewById(R.id.tvNameCoffee);
        ivCheck = itemView.findViewById(R.id.ivCheck);
        tvPrice = itemView.findViewById(R.id.tvPrice);
        tvAmount = itemView.findViewById(R.id.tvAmount);
        ivCheck.setVisibility(View.GONE);
        Button btnDown = itemView.findViewById(R.id.btnDown);
        Button btnUp = itemView.findViewById(R.id.btnUp);
        btnUp.setVisibility(View.GONE);
        btnDown.setVisibility(View.GONE);
    }

    public void bind(Context context, Drink drinkOrder) {
        GlideExt.showImage(context, ivCoffee, drinkOrder.getUrl());
        tvNameCoffee.setText(drinkOrder.getName());
        tvAmount.setText(String.valueOf(drinkOrder.getAmount()));
        int amount = drinkOrder.getAmount();
        tvPrice.setText(String.valueOf(amount * Integer.parseInt(drinkOrder.getPrice()) + "$"));
        if (drinkOrder.getStatus()) {
            ivCheck.setVisibility(View.VISIBLE);
        } else {
            ivCheck.setVisibility(View.GONE);
        }
    }
}
