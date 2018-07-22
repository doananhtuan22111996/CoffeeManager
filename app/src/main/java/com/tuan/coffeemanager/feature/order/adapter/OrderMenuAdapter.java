package com.tuan.coffeemanager.feature.order.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.listener.OnItemClickListener;
import com.tuan.coffeemanager.model.Drink;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderMenuAdapter extends RecyclerView.Adapter<OrderMenuAdapter.OrderMenuViewHolder> {


    private Context context;
    private List<Drink> drinkList;
    private OnItemClickListener onItemClickListener;

    public OrderMenuAdapter(Context context, List<Drink> drinkList) {
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
    public OrderMenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_menu, viewGroup, false);
        return new OrderMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderMenuViewHolder orderMenuViewHolder, final int i) {
        Drink drink = drinkList.get(i);
        orderMenuViewHolder.tvNameCoffee.setText(drink.getName());
        orderMenuViewHolder.tvPriceCoffee.setText(String.valueOf(drink.getPrice()) + "$");
        orderMenuViewHolder.cvOrderMenu.setOnClickListener(new View.OnClickListener() {
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

    static class OrderMenuViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvNameCoffee)
        TextView tvNameCoffee;
        @BindView(R.id.tvPriceCoffee)
        TextView tvPriceCoffee;
        @BindView(R.id.cvOrderMenu)
        CardView cvOrderMenu;

        OrderMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
