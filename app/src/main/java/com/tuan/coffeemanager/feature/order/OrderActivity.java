package com.tuan.coffeemanager.feature.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.base.BaseActivity;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.feature.order.adapter.OrderAdapter;
import com.tuan.coffeemanager.feature.order.adapter.OrderMenuAdapter;
import com.tuan.coffeemanager.feature.order.presenter.OrderPresenter;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.OnItemClickListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.model.DrinkOrder;
import com.tuan.coffeemanager.model.OrderDetail;
import com.tuan.coffeemanager.model.Table;
import com.tuan.coffeemanager.sharepref.DataUtil;
import com.tuan.coffeemanager.widget.DialogLoadingFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends BaseActivity implements View.OnClickListener, ViewListener.ViewListDataListener<Drink>, ViewListener.ViewPostListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvNumberTable)
    TextView tvNumberTable;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvUser)
    TextView tvUser;
    @BindView(R.id.rvOrder)
    RecyclerView rvOrder;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.rvMenu)
    RecyclerView rvMenu;
    @BindView(R.id.tvSaveCoffee)
    TextView tvSaveCoffee;

    private OrderPresenter orderPresenter;
    private Table table = null;
    private String user_id = null;
    private List<DrinkOrder> drinkOrderList = new ArrayList<>();
    private List<DrinkOrder> drinkOrderListPost = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        FirebaseDataApp.isActivity = true;

        showLoading();

        orderPresenter = new OrderPresenter(this, this);
        rvMenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        tvTitle.setText(R.string.text_order_title);
        tvTime.setText(getString(R.string.text_time_bill, getCalendar()));
        ivBack.setOnClickListener(this);
        tvSaveCoffee.setOnClickListener(this);

        if (getIntent().getExtras() != null) {
            table = (Table) getIntent().getExtras().getSerializable(ConstApp.TABLE_OBJ);
            if (table != null) {
                tvNumberTable.setText(getString(R.string.text_number_table, String.valueOf(table.getNumber())));
            }
        }
        String nameUser = DataUtil.getNameUser(this);
        user_id = DataUtil.getIdUser(this);
        if (nameUser.trim().isEmpty()) {
            tvUser.setText(getString(R.string.text_employee_bill, getString(R.string.text_example)));
        } else {
            tvUser.setText(getString(R.string.text_employee_bill, nameUser));
        }
        orderPresenter.getListDataDrink();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack: {
                onBackPressed();
                break;
            }
            case R.id.tvSaveCoffee: {
                showLoading();
                for (DrinkOrder drinkOrder : drinkOrderList) {
                    drinkOrderListPost.add(new DrinkOrder(drinkOrder.getDrink_id(), drinkOrder.getAmount(), false));
                }
                OrderDetail orderDetail = new OrderDetail(null, user_id, getCalendar(), true, drinkOrderListPost);
                orderPresenter.postDataOrder(this, orderDetail, table.getId());
                break;
            }
        }
    }

    @Override
    public void onSuccess(final List<Drink> drinks) {
        hideLoading();
        OrderMenuAdapter orderMenuAdapter = new OrderMenuAdapter(this, drinks);
        final OrderAdapter orderAdapter = new OrderAdapter(OrderActivity.this, drinkOrderList);
        rvMenu.setAdapter(orderMenuAdapter);
        orderMenuAdapter.notifyDataSetChanged();
        orderAdapter.setOnOrderItemClickListener(new OnItemClickListener.OnOrderItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                drinkOrderList.remove(position);
                orderAdapter.setDrinkOrderList(drinkOrderList);
                rvOrder.setAdapter(orderAdapter);
                orderAdapter.notifyDataSetChanged();
                tvTotal.setText(String.valueOf(total(drinkOrderList)) + "$");
            }

            @Override
            public void onItemClickBtnListener(int position, int amount) {
                DrinkOrder drinkOrder = drinkOrderList.get(position);
                drinkOrder.setAmount(String.valueOf(amount));
                tvTotal.setText(String.valueOf(total(drinkOrderList)) + "$");
            }
        });
        orderMenuAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                final Drink drink = drinks.get(position);
                if (!isExist(drink)) {
                    drinkOrderList.add(new DrinkOrder(drink.getId(), drink.getName(), drink.getPrice(), drink.getUuid(), drink.getUrl(), "1", false));
                    if (drinkOrderList.size() > 0) {
                        orderAdapter.setDrinkOrderList(drinkOrderList);
                        rvOrder.setAdapter(orderAdapter);
                        orderAdapter.notifyDataSetChanged();
                        tvTotal.setText(String.valueOf(total(drinkOrderList)) + "$");
                    }
                }
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
        FirebaseDataApp.isActivity = false;
    }

    private String getCalendar() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return String.valueOf(day + "/" + month + "/" + year);
    }

    private Boolean isExist(Drink drink) {
        for (DrinkOrder drinkOrder : drinkOrderList) {
            if (drinkOrder.getDrink_id().equals(drink.getId())) {
                return true;
            }
        }
        return false;
    }

    private int total(List<DrinkOrder> drinkOrderList) {
        int sum = 0;
        for (DrinkOrder drinkOrder : drinkOrderList) {
            sum += Integer.parseInt(drinkOrder.getAmount()) * Integer.parseInt(drinkOrder.getPrice());
        }
        return sum;
    }

    @Override
    public void postSuccess(String message) {
        hideLoading();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent intent = NavUtils.getParentActivityIntent(this);
        assert intent != null;
        intent.putExtra("FLAG", 1);
        NavUtils.navigateUpTo(this, intent);
        finish();
    }

    @Override
    public void postFailure(String error) {
        hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
