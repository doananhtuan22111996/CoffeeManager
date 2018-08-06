package com.tuan.coffeemanager.feature.featureManager.tableManager.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.model.Table;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TableManagerAdapter extends RecyclerView.Adapter<TableManagerAdapter.TableManagerViewHolder> {


    private Context context;
    private List<Table> tableList;

    public TableManagerAdapter(Context context, List<Table> tableList) {
        this.context = context;
        this.tableList = tableList;
    }

    @NonNull
    @Override
    public TableManagerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coffee_table, viewGroup, false);
        return new TableManagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableManagerViewHolder tableManagerViewHolder, int i) {
        Table table = tableList.get(i);
        tableManagerViewHolder.tvNumberTable.setText(context.getString(R.string.text_number_table, String.valueOf(table.getNumber())));
    }

    @Override
    public int getItemCount() {
        return tableList != null ? tableList.size() : 0;
    }

    class TableManagerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivCoffeeTable)
        ImageView ivCoffeeTable;
        @BindView(R.id.tvNumberTable)
        TextView tvNumberTable;

        private TableManagerViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
