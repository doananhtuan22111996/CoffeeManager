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
import com.tuan.coffeemanager.model.Table;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TableCoffeeAdapter extends RecyclerView.Adapter<TableCoffeeAdapter.TableOrderViewHolder> {

    private Context context;
    private List<Table> tableList;
    private OnItemClickListener onItemClickListener;

    public TableCoffeeAdapter(Context context, List<Table> tableList) {
        this.context = context;
        this.tableList = tableList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<Table> getTableList() {
        return tableList;
    }

    @NonNull
    @Override
    public TableOrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coffee_table, viewGroup, false);
        return new TableOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableOrderViewHolder tableOrderViewHolder, final int i) {
        Table table = tableList.get(i);
        tableOrderViewHolder.tvNumberTable.setText(context.getString(R.string.text_number_table, String.valueOf(table.getNumber())));
        tableOrderViewHolder.ivCoffeeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClickListener(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tableList != null ? tableList.size() : 0;
    }

    static class TableOrderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivCoffeeTable)
        ImageView ivCoffeeTable;
        @BindView(R.id.tvNumberTable)
        TextView tvNumberTable;

        TableOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
