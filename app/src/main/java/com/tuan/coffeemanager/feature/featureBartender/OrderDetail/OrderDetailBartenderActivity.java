package com.tuan.coffeemanager.feature.featureBartender.OrderDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.feature.featureBartender.OrderDetail.presenter.OrderDetailBartenderPresenter;
import com.tuan.coffeemanager.feature.featureBartender.presenter.OrderBartenderPresenter;
import com.tuan.coffeemanager.feature.pay.adapter.PayAdapter;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.DrinkOrder;
import com.tuan.coffeemanager.model.OrderBartender;
import com.tuan.coffeemanager.widget.CustomDialogLoadingFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailBartenderActivity extends AppCompatActivity implements ViewListener.ViewDeleteListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvNumberTable)
    TextView tvNumberTable;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.rvOrder)
    RecyclerView rvOrder;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.btnDone)
    Button btnDone;

    private OrderBartender orderBartender = null;
    private PayAdapter payAdapter;
    private OrderDetailBartenderPresenter orderDetailBartenderPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_bartender);
        ButterKnife.bind(this);

        orderDetailBartenderPresenter = new OrderDetailBartenderPresenter(this);
        tvTitle.setText(R.string.text_order_bartender);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (getIntent().getExtras() != null) {
            orderBartender = (OrderBartender) getIntent().getExtras().getSerializable(ContactBaseApp.BARTENDER);
        }
        if (orderBartender != null) {
            tvTime.setText(getString(R.string.text_time_bill, orderBartender.getDate()));
            tvNumberTable.setText(getString(R.string.text_number_table, String.valueOf(orderBartender.getNumber())));
            tvTotal.setText(String.valueOf(total(orderBartender.getDrinkOrderList()) + "$"));
            rvOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            payAdapter = new PayAdapter(this, orderBartender.getDrinkOrderList());
            rvOrder.setAdapter(payAdapter);
            payAdapter.notifyDataSetChanged();

        }

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialogLoadingFragment.showLoading(getSupportFragmentManager());
                orderDetailBartenderPresenter.doneBill(OrderDetailBartenderActivity.this, orderBartender);
            }
        });
    }

    private int total(List<DrinkOrder> drinkOrderList) {
        int sum = 0;
        for (DrinkOrder drinkOrder : drinkOrderList) {
            sum += Integer.parseInt(drinkOrder.getAmount()) * Integer.parseInt(drinkOrder.getPrice());
        }
        return sum;
    }

    @Override
    public void deleteSuccess(String message) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent intent = NavUtils.getParentActivityIntent(this);
        assert intent != null;
        NavUtils.navigateUpTo(this, intent);
    }

    @Override
    public void deleteFailure(String error) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
