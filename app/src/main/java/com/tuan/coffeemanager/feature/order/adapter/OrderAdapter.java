package com.tuan.coffeemanager.feature.order.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.listener.OnItemClickListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.model.DrinkOrder;
import com.tuan.coffeemanager.widget.CustomGlide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<DrinkOrder> drinkOrderList;
    private int amount;
    private OnItemClickListener.OnOrderItemClickListener onOrderItemClickListener;

    public OrderAdapter(Context context, List<DrinkOrder> drinkOrderList) {
        this.context = context;
        this.drinkOrderList = drinkOrderList;
    }

    public void setOnOrderItemClickListener(OnItemClickListener.OnOrderItemClickListener onOrderItemClickListener) {
        this.onOrderItemClickListener = onOrderItemClickListener;
    }

    public List<DrinkOrder> getDrinkOrderList() {
        return drinkOrderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_bill, viewGroup, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder orderViewHolder, final int i) {
        final DrinkOrder drink = drinkOrderList.get(i);
        CustomGlide.showImage(context, orderViewHolder.ivCoffee, drink.getUrl());
        orderViewHolder.tvNameCoffee.setText(drink.getName());
        amount = Integer.parseInt(drink.getAmount());
        orderViewHolder.tvAmount.setText(String.valueOf(amount));
        orderViewHolder.tvPrice.setText(String.valueOf(amount * drink.getPrice()) + "$");
        orderViewHolder.btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount > 1) {
                    amount = Integer.parseInt(orderViewHolder.tvAmount.getText().toString());
                    amount--;
                    orderViewHolder.tvAmount.setText(String.valueOf(amount));
                    orderViewHolder.tvPrice.setText(String.valueOf(amount * drink.getPrice()) + "$");
                    onOrderItemClickListener.onItemClickBtnListener(i, amount);
                }
            }
        });
        orderViewHolder.btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = Integer.parseInt(orderViewHolder.tvAmount.getText().toString());
                amount++;
                orderViewHolder.tvAmount.setText(String.valueOf(amount));
                orderViewHolder.tvPrice.setText(String.valueOf(amount * drink.getPrice()) + "$");
                onOrderItemClickListener.onItemClickBtnListener(i, amount);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drinkOrderList != null ? drinkOrderList.size() : 0;
    }

    static class OrderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivCoffee)
        ImageView ivCoffee;
        @BindView(R.id.tvNameCoffee)
        TextView tvNameCoffee;
        @BindView(R.id.tvPrice)
        TextView tvPrice;
        @BindView(R.id.btnDown)
        Button btnDown;
        @BindView(R.id.tvAmount)
        TextView tvAmount;
        @BindView(R.id.btnUp)
        Button btnUp;

        OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
