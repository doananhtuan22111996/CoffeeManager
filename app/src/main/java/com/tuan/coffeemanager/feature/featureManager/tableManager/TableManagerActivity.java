package com.tuan.coffeemanager.feature.featureManager.tableManager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.feature.featureManager.tableManager.adapter.TableManagerAdapter;
import com.tuan.coffeemanager.feature.featureManager.tableManager.presenter.TableManagerPresenter;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Table;
import com.tuan.coffeemanager.widget.CustomDialogLoadingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TableManagerActivity extends AppCompatActivity implements ViewListener.ViewListDataListener<Table>, ViewListener.ViewPostListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivAddCoffee)
    ImageView ivAddCoffee;
    @BindView(R.id.rvTable)
    RecyclerView rvTable;
    private TableManagerPresenter tableManagerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_manager);
        ButterKnife.bind(this);
        FirebaseDataApp.isActivity = true;
        CustomDialogLoadingFragment.showLoading(getSupportFragmentManager());
        tvTitle.setText(getString(R.string.table_manager));
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        rvTable.setLayoutManager(new GridLayoutManager(this, 3));

        tableManagerPresenter = new TableManagerPresenter(this, this);
        tableManagerPresenter.gerListTable();
    }

    @Override
    public void onSuccess(final List<Table> tableList) {
        CustomDialogLoadingFragment.hideLoading();
        TableManagerAdapter tableManagerAdapter = new TableManagerAdapter(this, tableList);
        rvTable.setAdapter(tableManagerAdapter);
        tableManagerAdapter.notifyDataSetChanged();
        ivAddCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogLoadingFragment.showLoading(getSupportFragmentManager());
                Table table = new Table(null, tableList.size() + 1);
                tableManagerPresenter.postTable(TableManagerActivity.this, table);
            }
        });

    }

    @Override
    public void onFailure(String error) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseDataApp.isActivity = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseDataApp.isActivity = true;
    }

    @Override
    public void postSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        FirebaseDataApp.isActivity = true;
        tableManagerPresenter.gerListTable();
    }

    @Override
    public void postFailure(String error) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
