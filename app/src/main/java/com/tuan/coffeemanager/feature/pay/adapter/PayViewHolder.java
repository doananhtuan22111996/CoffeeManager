package com.tuan.coffeemanager.feature.pay.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.model.DrinkOrder;
import com.tuan.coffeemanager.widget.GlideUtil;

public class PayViewHolder extends RecyclerView.ViewHolder {

    ImageView ivCoffee;
    TextView tvNameCoffee;
    ImageView ivCheck;
    TextView tvPrice;
    Button btnDown;
    Button btnUp;
    TextView tvAmount;

    PayViewHolder(@NonNull View itemView) {
        super(itemView);
        ivCoffee = itemView.findViewById(R.id.ivCoffee);
        tvNameCoffee = itemView.findViewById(R.id.tvNameCoffee);
        ivCheck = itemView.findViewById(R.id.ivCheck);
        tvPrice = itemView.findViewById(R.id.tvPrice);
        btnDown = itemView.findViewById(R.id.btnDown);
        btnUp = itemView.findViewById(R.id.btnUp);
        tvAmount = itemView.findViewById(R.id.tvAmount);

        ivCheck.setVisibility(View.GONE);
        btnUp.setVisibility(View.GONE);
        btnDown.setVisibility(View.GONE);
    }

    public void bind(Context context, DrinkOrder drinkOrder) {
        GlideUtil.showImage(context, ivCoffee, drinkOrder.getUrl());
        tvNameCoffee.setText(drinkOrder.getName());
        tvAmount.setText(drinkOrder.getAmount());
        int amount = Integer.parseInt(drinkOrder.getAmount());
        tvPrice.setText(String.valueOf(amount * Integer.parseInt(drinkOrder.getPrice()) + "$"));
        if (drinkOrder.getIsStatus_detail() == true) {
            ivCheck.setVisibility(View.VISIBLE);
        } else {
            ivCheck.setVisibility(View.GONE);
        }
    }
}
