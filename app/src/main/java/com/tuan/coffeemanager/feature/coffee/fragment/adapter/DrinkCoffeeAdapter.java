package com.tuan.coffeemanager.feature.coffee.fragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.listener.OnItemClickListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.widget.CustomGlide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrinkCoffeeAdapter extends RecyclerView.Adapter<DrinkCoffeeAdapter.DrinkCoffeeViewHolder> {

    private Context context;
    private List<Drink> drinkList;
    private OnItemClickListener onItemClickListener;

    public DrinkCoffeeAdapter(Context context, List<Drink> drinkList) {
        this.context = context;
        this.drinkList = drinkList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<Drink> getDrinkList() {
        return drinkList;
    }

    @NonNull
    @Override
    public DrinkCoffeeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu_coffee, viewGroup, false);
        return new DrinkCoffeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkCoffeeViewHolder drinkCoffeeViewHolder, final int i) {
        Drink drink = drinkList.get(i);
        drinkCoffeeViewHolder.tvNameCoffee.setText(drink.getName());
        if (drink.getUrl() != null){
            CustomGlide.showImage(context, drinkCoffeeViewHolder.ivCoffee, drink.getUrl());
        }
        drinkCoffeeViewHolder.ivCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drinkList != null ? drinkList.size() : 0;
    }

    static class DrinkCoffeeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivCoffee)
        ImageView ivCoffee;
        @BindView(R.id.tvNameCoffee)
        TextView tvNameCoffee;

        DrinkCoffeeViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
