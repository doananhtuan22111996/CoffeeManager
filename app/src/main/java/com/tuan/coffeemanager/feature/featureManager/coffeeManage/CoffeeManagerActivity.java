package com.tuan.coffeemanager.feature.featureManager.coffeeManage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.base.BaseActivity;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.feature.editCoffee.EditCoffeeActivity;
import com.tuan.coffeemanager.feature.coffee.fragment.adapter.DrinkCoffeeAdapter;
import com.tuan.coffeemanager.feature.coffee.fragment.presenter.MenuCoffeePresenter;
import com.tuan.coffeemanager.feature.coffeeDetail.CoffeeDetailActivity;
import com.tuan.coffeemanager.listener.OnItemClickListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoffeeManagerActivity extends BaseActivity implements View.OnClickListener, ViewListener.ViewListDataListener<Drink>, TextWatcher {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rvMenu)
    RecyclerView rvMenu;
    @BindView(R.id.ivAddCoffee)
    ImageView ivAddCoffee;
    @BindView(R.id.edtSearch)
    EditText edtSearch;

    private MenuCoffeePresenter menuCoffeePresenter;
    private DrinkCoffeeAdapter drinkCoffeeAdapter;
    private List<Drink> drinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_manager);
        ButterKnife.bind(this);
        showLoading();
        tvTitle.setText(getString(R.string.text_drink_coffee));
        ivBack.setOnClickListener(this);
        ivAddCoffee.setOnClickListener(this);
        rvMenu.setLayoutManager(new GridLayoutManager(this, 3));
        menuCoffeePresenter = new MenuCoffeePresenter(this);
        menuCoffeePresenter.getMenuListData();
        edtSearch.addTextChangedListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack: {
                onBackPressed();
                break;
            }
            case R.id.ivAddCoffee: {
                startActivity(new Intent(this, EditCoffeeActivity.class));
                break;
            }
        }
    }

    @Override
    public void onSuccess(List<Drink> drinkList) {
        hideLoading();
        drinks = drinkList;
        drinkCoffeeAdapter = new DrinkCoffeeAdapter(this, drinkList);
        rvMenu.setAdapter(drinkCoffeeAdapter);
        drinkCoffeeAdapter.notifyDataSetChanged();
        drinkCoffeeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = new Intent(CoffeeManagerActivity.this, CoffeeDetailActivity.class);
                intent.putExtra(ConstApp.DRINK_ID, drinkCoffeeAdapter.getDrinkList().get(position).getId());
                startActivity(intent);
            }
        });

    }

    @Override
    public void onFailure(String error) {
        hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
        List<Drink> drinkList = new ArrayList<>();
        if (search.isEmpty()) {
            drinkCoffeeAdapter.setDrinkList(this.drinks);
            drinkCoffeeAdapter.notifyDataSetChanged();
        } else {
            for (Drink drink : this.drinks) {
                if (drink.getName().toLowerCase().contains(search)) {
                    drinkList.add(drink);
                }
            }
            drinkCoffeeAdapter.setDrinkList(drinkList);
            drinkCoffeeAdapter.notifyDataSetChanged();
        }
    }
}
