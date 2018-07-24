package com.tuan.coffeemanager.feature.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.feature.order.adapter.OrderAdapter;
import com.tuan.coffeemanager.feature.order.adapter.OrderMenuAdapter;
import com.tuan.coffeemanager.feature.order.presenter.OrderCurrentPresenter;
import com.tuan.coffeemanager.feature.order.presenter.OrderPresenter;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.OnItemClickListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.model.DrinkOrder;
import com.tuan.coffeemanager.model.OrderDetail;
import com.tuan.coffeemanager.model.Table;
import com.tuan.coffeemanager.sharepref.DataUtil;
import com.tuan.coffeemanager.widget.CustomDialogLoadingFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener, ViewListener.ViewListDataListener<Drink>, ViewListener.ViewCurrentBill, ViewListener.ViewPostListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvNumberTable)
    TextView tvNumberTable;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvNumberBill)
    TextView tvNumberBill;
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
    private OrderCurrentPresenter orderCurrentPresenter;
    private Table table = null;
    private List<DrinkOrder> drinkOrderList = new ArrayList<>();
    private List<DrinkOrder> drinkOrderListPost = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        FirebaseDataApp.isActivity = true;

        CustomDialogLoadingFragment.showLoading(getSupportFragmentManager());

        orderPresenter = new OrderPresenter(this, this);
        rvMenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        tvTitle.setText(R.string.text_order_title);
        tvTime.setText(getString(R.string.text_time_bill, getCalendar()));
        ivBack.setOnClickListener(this);
        tvSaveCoffee.setOnClickListener(this);

        table = (Table) getIntent().getExtras().getSerializable(ContactBaseApp.TABLE_OBJ);
        if (table != null) {
            tvNumberTable.setText(getString(R.string.text_number_table, String.valueOf(table.getNumber())));
        }
        String nameUser = DataUtil.getNameUser(this);
        if (nameUser.trim().isEmpty()) {
            tvUser.setText(getString(R.string.text_employee_bill, getString(R.string.text_example)));
        } else {
            tvUser.setText(getString(R.string.text_employee_bill, nameUser));
        }

        String numberBill = DataUtil.getIndexBill(this);
        if (numberBill.trim().isEmpty()) {
            orderCurrentPresenter = new OrderCurrentPresenter(this);
            orderCurrentPresenter.getCurrentBill();
        } else {
            tvNumberBill.setText(getString(R.string.text_number_bill, Integer.parseInt(numberBill)));
            orderPresenter.getListDataDrink();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack: {
                onBackPressed();
                break;
            }
            case R.id.tvSaveCoffee: {
                CustomDialogLoadingFragment.showLoading(getSupportFragmentManager());
                for (DrinkOrder drinkOrder : drinkOrderList) {
                    drinkOrderListPost.add(new DrinkOrder(drinkOrder.getId(), drinkOrder.getAmount()));
                }
                OrderDetail orderDetail = new OrderDetail(null, getCalendar(), drinkOrderListPost);
                orderPresenter.postDataOrder(this, orderDetail);
                break;
            }
        }
    }

    @Override
    public void onSuccess(final List<Drink> drinks) {
        CustomDialogLoadingFragment.hideLoading();
        OrderMenuAdapter orderMenuAdapter = new OrderMenuAdapter(this, drinks);
        final OrderAdapter orderAdapter = new OrderAdapter(OrderActivity.this, drinkOrderList);
        rvMenu.setAdapter(orderMenuAdapter);
        orderMenuAdapter.notifyDataSetChanged();
        orderMenuAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                final Drink drink = drinks.get(position);
                if (!isExist(drink)) {
                    drinkOrderList.add(new DrinkOrder(drink.getId(), drink.getName(), drink.getPrice(), drink.getPurchases(), drink.getUuid(), drink.getUrl(), "1"));
                    if (drinkOrderList.size() > 0) {
                        orderAdapter.setDrinkOrderList(drinkOrderList);
                        rvOrder.setAdapter(orderAdapter);
                        orderAdapter.notifyDataSetChanged();
                        tvTotal.setText(String.valueOf(total(drinkOrderList)) + "$");
                        orderAdapter.setOnOrderItemClickListener(new OnOrderItemClickListener() {
                            @Override
                            public void onItemClickListener(int position) {
                                drinkOrderList.remove(position);
                                orderAdapter.setDrinkOrderList(drinkOrderList);
                                rvOrder.setAdapter(orderAdapter);
                                orderAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onItemClickBtnListener(int position, int amount) {
                                DrinkOrder drinkOrder = drinkOrderList.get(position);
                                drinkOrder.setAmount(String.valueOf(amount));
                                tvTotal.setText(String.valueOf(total(drinkOrderList)) + "$");
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onSuccess(String index) {
        DataUtil.setIndexBill(this, index);
        tvNumberBill.setText(getString(R.string.text_number_bill, Integer.parseInt(index) + 1));
        orderPresenter.getListDataDrink();
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

    private String getCalendar() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return String.valueOf(day + "/" + month + "/" + year);
    }

    private Boolean isExist(Drink drink) {
        for (DrinkOrder drinkOrder : drinkOrderList) {
            if (drinkOrder.getId().equals(drink.getId())) {
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
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postFailure(String error) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
