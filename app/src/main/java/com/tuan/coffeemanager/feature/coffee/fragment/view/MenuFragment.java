package com.tuan.coffeemanager.feature.coffee.fragment.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.base.NodeBaseApp;
import com.tuan.coffeemanager.feature.coffee.fragment.adapter.DrinkCoffeeAdater;
import com.tuan.coffeemanager.feature.coffee.fragment.presenter.MenuCoffeePresenter;
import com.tuan.coffeemanager.feature.coffeedetail.CoffeeDetailActivity;
import com.tuan.coffeemanager.listener.OnItemClickListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MenuFragment extends Fragment implements ViewListener.ViewListDataListener<Drink> {

    @BindView(R.id.rvMenu)
    RecyclerView rvMenu;
    Unbinder unbinder;

    MenuCoffeePresenter menuCoffeePresenter;

    public static MenuFragment newInstance() {
        Bundle args = new Bundle();
        MenuFragment fragment = new MenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMenu.setLayoutManager(new GridLayoutManager(getContext(), 3));
        menuCoffeePresenter = new MenuCoffeePresenter(this);
        menuCoffeePresenter.getMenuListData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccess(List<Drink> drinks) {
        final DrinkCoffeeAdater drinkCoffeeAdater = new DrinkCoffeeAdater(getContext(), drinks);
        rvMenu.setAdapter(drinkCoffeeAdater);
        drinkCoffeeAdater.notifyDataSetChanged();
        drinkCoffeeAdater.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = new Intent(getActivity(), CoffeeDetailActivity.class);
                intent.putExtra(NodeBaseApp.DRINK_ID, drinkCoffeeAdater.getDrinkList().get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onFailure(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
