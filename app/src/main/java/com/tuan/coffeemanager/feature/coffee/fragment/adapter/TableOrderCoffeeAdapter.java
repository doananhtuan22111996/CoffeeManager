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
import com.tuan.coffeemanager.model.Order;
import com.tuan.coffeemanager.model.Table;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TableOrderCoffeeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Table> tableList;
    private List<Order> orderList;
    private OnItemClickListener.OnTableClickListener onTableClickListener;

    public TableOrderCoffeeAdapter(Context context, List<Table> tableList, List<Order> orderList) {
        this.context = context;
        this.tableList = tableList;
        this.orderList = orderList;
    }

    public void setOnTableClickListener(OnItemClickListener.OnTableClickListener onTableClickListener) {
        this.onTableClickListener = onTableClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == R.layout.item_coffee_table_close) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_coffee_table_close, viewGroup, false);
            return new TableCoffeeCloseViewHolder(view);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_coffee_table, viewGroup, false);
        return new TableCoffeeOpenViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        Table table = tableList.get(i);
        if (viewHolder.getItemViewType() == R.layout.item_coffee_table_close) {
            TableCoffeeCloseViewHolder tableCoffeeCloseViewHolder = new TableCoffeeCloseViewHolder(viewHolder.itemView);
            tableCoffeeCloseViewHolder.tvNumberTable.setText(context.getString(R.string.text_number_table, String.valueOf(table.getNumber())));
            tableCoffeeCloseViewHolder.ivCoffeeTable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTableClickListener.onItemCloseClickListener(i);
                }
            });
        } else {
            TableCoffeeOpenViewHolder tableCoffeeOpenViewHolder = new TableCoffeeOpenViewHolder(viewHolder.itemView);
            tableCoffeeOpenViewHolder.tvNumberTable.setText(context.getString(R.string.text_number_table, String.valueOf(table.getNumber())));
            tableCoffeeOpenViewHolder.ivCoffeeTable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onTableClickListener.onItemOpenClickListener(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return tableList != null ? tableList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (orderList.size() > position) {
            if (orderList.get(position).getTable_id().equals(tableList.get(position).getId())) {
                return R.layout.item_coffee_table_close;
            }
        }
        return R.layout.item_coffee_table;
    }

    static class TableCoffeeOpenViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivCoffeeTable)
        ImageView ivCoffeeTable;
        @BindView(R.id.tvNumberTable)
        TextView tvNumberTable;

        TableCoffeeOpenViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class TableCoffeeCloseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivCoffeeTable)
        ImageView ivCoffeeTable;
        @BindView(R.id.tvNumberTable)
        TextView tvNumberTable;

        TableCoffeeCloseViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
