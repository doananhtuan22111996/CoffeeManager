package com.tuan.coffeemanager.feature.coffee.fragment.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.feature.coffee.CoffeeActivity;
import com.tuan.coffeemanager.feature.coffee.fragment.adapter.TableCoffeeAdapter;
import com.tuan.coffeemanager.feature.coffee.fragment.presenter.TableCoffeePresenter;
import com.tuan.coffeemanager.feature.order.OrderActivity;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.OnItemClickListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Order;
import com.tuan.coffeemanager.model.Table;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TableFragment extends Fragment implements ViewListener.ViewListDataListener<Table>, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.rvTable)
    RecyclerView rvTable;
    @BindView(R.id.navTable)
    NavigationView navTable;
    @BindView(R.id.dlTable)
    DrawerLayout dlTable;
    Unbinder unbinder;

    private TableCoffeePresenter tableCoffeePresenter;
    private Table table;

    public static TableFragment newInstance() {
        Bundle args = new Bundle();
        TableFragment fragment = new TableFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_table, container, false);
        unbinder = ButterKnife.bind(this, view);
        FirebaseDataApp.isActivity = true;

        dlTable.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        navTable.setNavigationItemSelectedListener(this);

        rvTable.setLayoutManager(new GridLayoutManager(getContext(), 3));

        TextView tvNameUser = navTable.getHeaderView(0).findViewById(R.id.tvNameUser);
        tvNameUser.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        tableCoffeePresenter = new TableCoffeePresenter(this);
        tableCoffeePresenter.getTableListData();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStop() {
        super.onStop();
        FirebaseDataApp.isActivity = false;
    }

    @Override
    public void onSuccess(final List<Table> tables) {
        final TextView tvNumberTable = navTable.getHeaderView(0).findViewById(R.id.tvNumberTable);
        TableCoffeeAdapter tableCoffeeAdapter = new TableCoffeeAdapter(getContext(), tables);
        rvTable.setAdapter(tableCoffeeAdapter);
        tableCoffeeAdapter.notifyDataSetChanged();
        tableCoffeeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                table = tables.get(position);
                tvNumberTable.setText(getResources().getString(R.string.text_number_table, String.valueOf(position + 1)));
                dlTable.openDrawer(Gravity.START);
            }
        });
    }

    @Override
    public void onFailure(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_Order: {
                Intent intent = new Intent(getActivity(), OrderActivity.class);
                intent.putExtra(ContactBaseApp.TABLE_OBJ, table);
                startActivity(intent);
                dlTable.closeDrawer(Gravity.START);
                break;
            }
        }
        return false;
    }
}
