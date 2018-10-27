package com.tuan.coffeemanager.feature.featureBartender.OrderDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.base.BaseActivity;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.feature.featureBartender.OrderDetail.presenter.OrderDetailBartenderPresenter;
import com.tuan.coffeemanager.feature.pay.adapter.PayAdapter;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.model.NotificationResponse;
import com.tuan.coffeemanager.model.OrderBartender;
import com.tuan.coffeemanager.model.User;
import com.tuan.coffeemanager.retrofit.Api;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderDetailBartenderActivity extends BaseActivity implements ViewListener.ViewDeleteListener, ViewListener.ViewDataListener<User> {

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
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_bartender);
        ButterKnife.bind(this);
        showLoading();

        orderDetailBartenderPresenter = new OrderDetailBartenderPresenter(this, this);
        tvTitle.setText(R.string.text_order_bartender);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        orderDetailBartenderPresenter.getDataWaiter();

        if (getIntent().getExtras() != null) {
            orderBartender = (OrderBartender) getIntent().getExtras().getSerializable(ConstApp.BARTENDER);
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
                showLoading();
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .connectTimeout(20, TimeUnit.SECONDS)
                        .writeTimeout(20, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Api api = retrofit.create(Api.class);
                Call<NotificationResponse> call = api.pushNotification(user.getToken(), "Complete Order!");
                call.enqueue(new Callback<NotificationResponse>() {
                    @Override
                    public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                        if (response != null) {
                            orderDetailBartenderPresenter.doneBill(OrderDetailBartenderActivity.this, orderBartender);
                        }
                    }

                    @Override
                    public void onFailure(Call<NotificationResponse> call, Throwable t) {

                    }
                });
            }
        });
    }

    private int total(List<Drink> drinkOrderList) {
        int sum = 0;
        for (Drink drinkOrder : drinkOrderList) {
            sum += Integer.parseInt(drinkOrder.getAmount()) * Integer.parseInt(drinkOrder.getPrice());
        }
        return sum;
    }

    @Override
    public void deleteSuccess(String message) {
        hideLoading();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent intent = NavUtils.getParentActivityIntent(this);
        assert intent != null;
        NavUtils.navigateUpTo(this, intent);
    }

    @Override
    public void deleteFailure(String error) {
        hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(User user) {
        hideLoading();
        this.user = user;
    }

    @Override
    public void onFailure(String error) {
        hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
