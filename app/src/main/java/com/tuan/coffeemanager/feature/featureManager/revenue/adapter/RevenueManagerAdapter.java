package com.tuan.coffeemanager.feature.featureManager.revenue.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.listener.OnItemClickListener;
import com.tuan.coffeemanager.model.DrinkOrder;
import com.tuan.coffeemanager.model.OrderDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RevenueManagerAdapter extends RecyclerView.Adapter<RevenueManagerAdapter.RevenueManagerViewHolder> {

    private Context context;
    private List<OrderDetail> orderDetailList;
    private OnItemClickListener onItemClickListener;

    public RevenueManagerAdapter(Context context, List<OrderDetail> orderDetailList) {
        this.context = context;
        this.orderDetailList = orderDetailList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    @NonNull
    @Override
    public RevenueManagerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill, viewGroup, false);
        return new RevenueManagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RevenueManagerViewHolder revenueManagerViewHolder, final int i) {
        OrderDetail orderDetail = orderDetailList.get(i);
        revenueManagerViewHolder.tvDate.setText(context.getString(R.string.text_date, orderDetail.getDate()));
        revenueManagerViewHolder.tvTotal.setText(context.getString(R.string.text_total_only_bill, String.valueOf(totalBill(orderDetail))));
        revenueManagerViewHolder.clBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderDetailList != null ? orderDetailList.size() : 0;
    }

    private int totalBill(OrderDetail orderDetail) {
        int sum = 0;
        for (DrinkOrder drinkOrder : orderDetail.getDrinkOrderList()) {
            sum += Integer.parseInt(drinkOrder.getAmount()) * Integer.parseInt(drinkOrder.getPrice());
        }
        return sum;
    }

    static class RevenueManagerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvTotal)
        TextView tvTotal;
        @BindView(R.id.clBill)
        ConstraintLayout clBill;

        RevenueManagerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
