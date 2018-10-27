package com.tuan.coffeemanager.feature.editOrder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.base.BaseActivity;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.feature.editOrder.presenter.EditOrderPresenter;
import com.tuan.coffeemanager.feature.order.adapter.OrderAdapter;
import com.tuan.coffeemanager.feature.order.adapter.OrderMenuAdapter;
import com.tuan.coffeemanager.listener.OnItemClickListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.model.OrderDetail;
import com.tuan.coffeemanager.model.Table;
import com.tuan.coffeemanager.sharepref.DataUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditOrderActivity extends BaseActivity implements ViewListener.ViewlistDataDoubleListener<Drink, Drink>, ViewListener.ViewDataListener<OrderDetail>, View.OnClickListener, ViewListener.ViewPostListener {

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
    @BindView(R.id.tvMenuTitle)
    TextView tvMenuTitle;
    @BindView(R.id.rvMenu)
    RecyclerView rvMenu;
    @BindView(R.id.tvSaveCoffee)
    TextView tvSaveCoffee;

    private EditOrderPresenter editOrderPresenter;
    private OrderMenuAdapter orderMenuAdapter;
    private OrderAdapter orderAdapter;
    private Table table = null;
    private String user_id = null;
    private String order_drink_id = null;
    private String date = null;
    private List<Drink> drinkOrderList = new ArrayList<>();
    private List<Drink> drinkOrderListPost = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);
        ButterKnife.bind(this);

        showLoading();

        tvTitle.setText(R.string.text_title_edit_order);

        rvMenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        if (getIntent().getExtras() != null) {
            table = (Table) getIntent().getExtras().getSerializable(ConstApp.TABLE_OBJ);
            order_drink_id = getIntent().getExtras().getString(ConstApp.ORDER_DETAIL_ID);
            if (table != null) {
                tvNumberTable.setText(getString(R.string.text_number_table, String.valueOf(table.getNumber())));
            }
        }
        String nameUser = DataUtil.newInstance(this).getDataUser().getName();
        user_id = DataUtil.newInstance(this).getDataUser().getId();
        if (nameUser.trim().isEmpty()) {
            tvUser.setText(getString(R.string.text_employee_bill, getString(R.string.text_example)));
        } else {
            tvUser.setText(getString(R.string.text_employee_bill, nameUser));
        }

        editOrderPresenter = new EditOrderPresenter(this, this, this);
        editOrderPresenter.getDataOrderDetailDrink(order_drink_id);

        ivBack.setOnClickListener(this);
        tvSaveCoffee.setOnClickListener(this);
    }

    @Override
    public void onSuccess(final List<Drink> drinkOrders, final List<Drink> drinkList) {
        hideLoading();
        this.drinkOrderList = drinkOrders;
        orderMenuAdapter = new OrderMenuAdapter(this, drinkList);
        orderAdapter = new OrderAdapter(this, drinkOrderList);
        tvTotal.setText(String.valueOf(total(drinkOrderList)) + "$");
        rvMenu.setAdapter(orderMenuAdapter);
        rvOrder.setAdapter(orderAdapter);
        orderMenuAdapter.notifyDataSetChanged();
        orderAdapter.notifyDataSetChanged();
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
                Drink drinkOrder = drinkOrderList.get(position);
                drinkOrder.setAmount(String.valueOf(amount));
                tvTotal.setText(String.valueOf(total(drinkOrderList)) + "$");
            }
        });
        orderMenuAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                final Drink drink = drinkList.get(position);
                if (!isExist(drink)) {
                    drinkOrderList.add(new Drink(drink.getId(), drink.getName(), drink.getPrice(), drink.getUuid(), drink.getUrl(), "1", false));
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
    public void onSuccess(OrderDetail orderDetail) {
        tvTime.setText(getString(R.string.text_time_bill, orderDetail.getDate()));
        date = orderDetail.getDate();
    }

    @Override
    public void onFailure(String error) {
        hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private int total(List<Drink> drinkOrderList) {
        int sum = 0;
        for (Drink drinkOrder : drinkOrderList) {
            sum += Integer.parseInt(drinkOrder.getAmount()) * Integer.parseInt(drinkOrder.getPrice());
        }
        return sum;
    }

    private Boolean isExist(Drink drink) {
        for (Drink drinkOrder : drinkOrderList) {
            if (drinkOrder.getId().equals(drink.getId()) && drinkOrder.getStatus() == false) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSaveCoffee: {
                showLoading();
                for (Drink drinkOrder : drinkOrderList) {
                    drinkOrderListPost.add(new Drink(drinkOrder.getId(), drinkOrder.getStatus(), drinkOrder.getAmount()));
                }
                OrderDetail orderDetail = new OrderDetail(order_drink_id, user_id, date, true, drinkOrderListPost);
                editOrderPresenter.editOrderDetail(this, orderDetail);
                break;
            }
            case R.id.ivBack: {
                onBackPressed();
                break;
            }
        }
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
