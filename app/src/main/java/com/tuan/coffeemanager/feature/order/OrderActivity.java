package com.tuan.coffeemanager.feature.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.tuan.coffeemanager.feature.order.adapter.OrderAdapter;
import com.tuan.coffeemanager.feature.order.adapter.OrderMenuAdapter;
import com.tuan.coffeemanager.feature.order.listener.IOnItemClickListener;
import com.tuan.coffeemanager.feature.order.listener.IOrderListener;
import com.tuan.coffeemanager.feature.order.presenter.OrderPresenter;
import com.tuan.coffeemanager.listener.OnItemClickListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.model.OrderDetail;
import com.tuan.coffeemanager.model.Table;
import com.tuan.coffeemanager.model.User;
import com.tuan.coffeemanager.sharepref.DataUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderActivity extends BaseActivity implements View.OnClickListener, IOrderListener.IOrderViewListener {

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
    private User user = null;

    private List<Drink> drinkList = new ArrayList<>();
    private List<Drink> drinkOrderList = new ArrayList<>();
    private OrderMenuAdapter orderMenuAdapter;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);

        showLoading();

        init();
        event();

        //2.1.a Request Menu
        orderPresenter.requestDrinkCoffee();
    }

    //Khởi tạo giá trị ban đầu
    private void init() {
        tvTitle.setText(R.string.text_order_title);
        tvTime.setText(getString(R.string.text_time_bill, getCurrentCalendar()));

        user = DataUtil.newInstance(this).getDataUser();
        tvUser.setText(getString(R.string.text_employee_bill,
                (user != null && !user.getName().isEmpty()) ? user.getName() : getString(R.string.text_example)));

        if (getIntent().getExtras() != null) {
            table = (Table) getIntent().getExtras().getSerializable(ConstApp.TABLE_OBJ);
            tvNumberTable.setText(getString(R.string.text_number_table,
                    (table != null && table.getNumber() != 0) ? String.valueOf(table.getNumber()) : getString(R.string.table_default)));
        }

        orderPresenter = new OrderPresenter(this);
        rvMenu.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        orderAdapter = new OrderAdapter(this, null);
        orderMenuAdapter = new OrderMenuAdapter(this, null);
        rvMenu.setAdapter(orderMenuAdapter);
        rvOrder.setAdapter(orderAdapter);
        orderMenuAdapter.notifyDataSetChanged();
        orderAdapter.notifyDataSetChanged();
    }

    private void event() {
        ivBack.setOnClickListener(this);
        tvSaveCoffee.setOnClickListener(this);
        setOnClickMenuListener();
        setOnClickOrderListener();
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
                OrderDetail orderDetail = new OrderDetail(user.getId(), getCurrentCalendar(), false, drinkOrderList);
                orderPresenter.requestOrder(orderDetail, table.getId());
                break;
            }
        }
    }

    private void setOnClickOrderListener() {
        orderAdapter.setOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void onRemoveItemClickListener(int position) {
                drinkOrderList.remove(position);
                orderAdapter.setDrinkOrderList(drinkOrderList);
                rvOrder.setAdapter(orderAdapter);
                orderAdapter.notifyDataSetChanged();
                tvTotal.setText(getString(R.string.total_order, String.valueOf(total(drinkOrderList))));
            }

            @Override
            public void onChangeAmountItemClickListener(int position, int amount) {
                drinkOrderList.get(position).setAmount(amount);
                tvTotal.setText(getString(R.string.total_order, String.valueOf(total(drinkOrderList))));
            }
        });
    }

    //2.1.c Xử lý xự kiện nhấn Order
    private void setOnClickMenuListener() {
        orderMenuAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                final Drink drink = drinkList.get(position);
                //2.1.c Xử lý xự kiện nhấn Order -  Kiểm tra item trong danh sách Order
                if (!isExist(drink)) {
                    //2.1.c Xử lý xự kiện nhấn Order - Tạo món
                    //2.1.c Xử lý xự kiện nhấn Order - Tạo món - Amount
                    drink.setAmount(ConstApp.DEFAULT_AMOUNT);
                    ////2.1.c Xử lý xự kiện nhấn Order - Tạo món - Status
                    drink.setStatus(false);
                    //2.1.c Xử lý xự kiện nhấn Order - Tạo món - Lưu vào danh sách order
                    drinkOrderList.add(drink);
                    orderAdapter.setDrinkOrderList(drinkOrderList);
                    rvOrder.setAdapter(orderAdapter);
                    orderAdapter.notifyDataSetChanged();
                    //2.1.c Xử lý xự kiện nhấn Order - Tạo món - Tính tổng giá của Order
                    tvTotal.setText(getString(R.string.total_order, String.valueOf(total(drinkOrderList))));
                }
            }
        });
    }

    private String getCurrentCalendar() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return String.valueOf(day + ConstApp.KEY_DATE + month + ConstApp.KEY_DATE + year);
    }

    //2.1.c Xử lí sự kiện nhấn Order - Kiểm tra item trong danh sách Order
    private Boolean isExist(Drink drink) {
        for (Drink drinkOrder : drinkOrderList) {
            if (drinkOrder.getId().equals(drink.getId())) {
                //2.1.c Xử lí sự kiện nhấn Order - Kiểm tra item trong danh sách Order - true
                return true;
            }
        }
        //2.1.c Xử lí sự kiện nhấn Order - Kiểm tra item trong danh sách Order - false
        return false;
    }

    //2.1.c Xử lý sự kiện nhấn Order - Tạo món - Tính tổng giá trị Order
    private int total(List<Drink> drinkList) {
        int sum = 0;
        for (Drink drinkOrder : drinkList) {
            //priceDrink = amount * price (Giá tiền từng item)
            //priceOrder = Sum(priceDrink) (Giá tiền của Order)
            sum += drinkOrder.getAmount() * Integer.parseInt(drinkOrder.getPrice());
        }
        return sum;
    }


    //2.1.b Hiển thị menu
    @Override
    public void drinkCoffeeSuccess(List<Drink> drinkList) {
        hideLoading();
        this.drinkList = drinkList;
        orderMenuAdapter.setDrinkList(drinkList);
        orderMenuAdapter.notifyDataSetChanged();

    }

    @Override
    public void drinkCoffeeFailure(String error) {
        hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void orderSuccess(String error) {
        hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        Intent intent = NavUtils.getParentActivityIntent(this);
        assert intent != null;
        intent.putExtra(ConstApp.PAGE, ConstApp.PAGE_TABLE);
        NavUtils.navigateUpTo(this, intent);
        finish();
    }

    @Override
    public void orderFailure(String error) {
        hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
