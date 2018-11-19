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
import com.tuan.coffeemanager.feature.order.listener.IOnItemClickListener;
import com.tuan.coffeemanager.ext.GlideExt;
import com.tuan.coffeemanager.model.Drink;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private List<Drink> drinkOrderList;
    private IOnItemClickListener onItemClickListener;

    public OrderAdapter(Context context, List<Drink> drinkOrderList) {
        this.context = context;
        this.drinkOrderList = drinkOrderList;
    }

    public void setOnItemClickListener(IOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setDrinkOrderList(List<Drink> drinkOrderList) {
        this.drinkOrderList = drinkOrderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_bill, viewGroup, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder orderViewHolder, final int position) {
        final Drink drink = drinkOrderList.get(position);
        //14.2.2 Hiển thị danh sách Order - Hiển thị ảnh
        GlideExt.showImage(context, orderViewHolder.ivCoffee, drink.getUrl());
        //14.2.2 Hiển thị danh sách Order - Hiển thị tên
        orderViewHolder.tvNameCoffee.setText(drink.getName());
        //14.2.2 Hiển thị danh sách Order - Hiển thị số lượng
        orderViewHolder.tvAmount.setText(String.valueOf(drink.getAmount()));
        //14.2.2 Hiển thị danh sách Order - Hiển thị giá
        orderViewHolder.tvPrice.setText(context.getString(R.string.total_order, String.valueOf(drink.getAmount() * Integer.parseInt(drink.getPrice()))));
        if (drink.getStatus()) {
            orderViewHolder.btnDown.setVisibility(View.GONE);
            orderViewHolder.btnUp.setVisibility(View.GONE);
            orderViewHolder.ivDelete.setVisibility(View.GONE);
        } else {
            orderViewHolder.btnDown.setVisibility(View.VISIBLE);
            orderViewHolder.btnUp.setVisibility(View.VISIBLE);
            orderViewHolder.ivDelete.setVisibility(View.VISIBLE);
        }
        //14.3.b Xử lý từng item trong danh sách Order - Xử lý click down
        orderViewHolder.btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //14.3.b Xử lý từng item trong danh sách Order - Xử lý click down - giảm số lượng
                if (drink.getAmount() > 1) {
                    int amount = Integer.parseInt(orderViewHolder.tvAmount.getText().toString());
                    amount--;
                    orderViewHolder.tvAmount.setText(String.valueOf(amount));
                    orderViewHolder.tvPrice.setText(context.getString(R.string.total_order, String.valueOf(drink.getAmount() * Integer.parseInt(drink.getPrice()))));
                    onItemClickListener.onChangeAmountItemClickListener(position, amount);
                }
            }
        });
        //14.3.b Xử lý từng item trong danh sách Order - Xử lý click up
        orderViewHolder.btnUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //14.3.b Xử lý từng item trong danh sách Order - Xử lý click up - tăng số lượng
                int amount = Integer.parseInt(orderViewHolder.tvAmount.getText().toString());
                amount++;
                orderViewHolder.tvAmount.setText(String.valueOf(amount));
                orderViewHolder.tvPrice.setText(context.getString(R.string.total_order, String.valueOf(drink.getAmount() * Integer.parseInt(drink.getPrice()))));
                onItemClickListener.onChangeAmountItemClickListener(position, amount);
            }
        });
        //14.3.a Xử lý từng item trong danh sách Order - Xử lý click delete
        orderViewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onRemoveItemClickListener(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return drinkOrderList != null ? drinkOrderList.size() : 0;
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {

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
        @BindView(R.id.ivDelete)
        ImageView ivDelete;

        OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
