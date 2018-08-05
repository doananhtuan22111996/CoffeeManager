package com.tuan.coffeemanager.feature.featureBartender.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.model.DrinkOrder;
import com.tuan.coffeemanager.model.OrderBartender;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderBartenderAdapter extends RecyclerView.Adapter<OrderBartenderAdapter.OrderBartenderViewHolder> {

    private Context context;
    private List<OrderBartender> orderBartenderList;
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public OrderBartenderAdapter(Context context, List<OrderBartender> orderBartenderList) {
        this.context = context;
        this.orderBartenderList = orderBartenderList;
    }

    @NonNull
    @Override
    public OrderBartenderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill, viewGroup, false);
        return new OrderBartenderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderBartenderViewHolder orderBartenderViewHolder, final int i) {
        OrderBartender orderBartender = orderBartenderList.get(i);
        orderBartenderViewHolder.tvDate.setText(context.getString(R.string.text_date, orderBartender.getDate()));
        orderBartenderViewHolder.tvTotal.setText(context.getString(R.string.text_total_only_bill, String.valueOf(totalBill(orderBartender))));
        orderBartenderViewHolder.clBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onItemCLickListener(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderBartenderList != null ? orderBartenderList.size() : 0;
    }

    class OrderBartenderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivBill)
        ImageView ivBill;
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvTotal)
        TextView tvTotal;
        @BindView(R.id.clBill)
        ConstraintLayout clBill;

        OrderBartenderViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private int totalBill(OrderBartender orderBartender) {
        int sum = 0;
        for (DrinkOrder drinkOrder : orderBartender.getDrinkOrderList()) {
            sum += Integer.parseInt(drinkOrder.getAmount()) * Integer.parseInt(drinkOrder.getPrice());
        }
        return sum;
    }

    public interface OnClickListener {
        void onItemCLickListener(int position);
    }
}
