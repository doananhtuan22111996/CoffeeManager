package com.tuan.coffeemanager.feature.coffee.fragment.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.base.BaseFragment;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.feature.coffee.fragment.adapter.DrinkCoffeeAdapter;
import com.tuan.coffeemanager.feature.coffee.fragment.presenter.MenuCoffeePresenter;
import com.tuan.coffeemanager.feature.coffeeDetail.CoffeeDetailActivity;
import com.tuan.coffeemanager.listener.OnItemClickListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.ext.KeyBoardExt;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MenuFragment extends BaseFragment implements ViewListener.ViewListDataListener<Drink>, TextWatcher, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rvMenu)
    RecyclerView rvMenu;
    Unbinder unbinder;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.svMenu)
    SwipeRefreshLayout svMenu;

    private MenuCoffeePresenter menuCoffeePresenter;
    private DrinkCoffeeAdapter drinkCoffeeAdapter;
    private List<Drink> drinkList;

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
        showLoading();
        rvMenu.setLayoutManager(new GridLayoutManager(getContext(), 3));
        menuCoffeePresenter = new MenuCoffeePresenter(this);
        menuCoffeePresenter.getMenuListData();
        edtSearch.addTextChangedListener(this);

        svMenu.setOnRefreshListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccess(List<Drink> drinks) {
        hideLoading();
        svMenu.setRefreshing(false);
        drinkList = drinks;
        drinkCoffeeAdapter = new DrinkCoffeeAdapter(getContext(), drinks);
        rvMenu.setAdapter(drinkCoffeeAdapter);
        drinkCoffeeAdapter.notifyDataSetChanged();
        drinkCoffeeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                KeyBoardExt.hideKeyBoard(getActivity());
                Intent intent = new Intent(getActivity(), CoffeeDetailActivity.class);
                intent.putExtra(ConstApp.DRINK_ID, drinkCoffeeAdapter.getDrinkList().get(position).getId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onFailure(String error) {
        hideLoading();
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        searchDrink(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    private void searchDrink(String search) {
        if (this.drinkList != null) {
            List<Drink> drinkList = new ArrayList<>();
            if (search.isEmpty()) {
                drinkCoffeeAdapter.setDrinkList(this.drinkList);
                drinkCoffeeAdapter.notifyDataSetChanged();
            } else {
                for (Drink drink : this.drinkList) {
                    if (drink.getName().toLowerCase().contains(search)) {
                        drinkList.add(drink);
                    }
                }
                drinkCoffeeAdapter.setDrinkList(drinkList);
                drinkCoffeeAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        menuCoffeePresenter.getMenuListData();
    }

    @Override
    public void onRefresh() {
        menuCoffeePresenter.getMenuListData();
    }
}
