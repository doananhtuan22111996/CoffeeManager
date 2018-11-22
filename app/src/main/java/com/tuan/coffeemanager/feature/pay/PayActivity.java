package com.tuan.coffeemanager.feature.pay;

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
import com.tuan.coffeemanager.feature.pay.adapter.PayAdapter;
import com.tuan.coffeemanager.feature.pay.presenter.PayPresenter;
import com.tuan.coffeemanager.feature.pay.presenter.PayUserPresenter;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.model.NotificationResponse;
import com.tuan.coffeemanager.model.OrderDetail;
import com.tuan.coffeemanager.model.Table;
import com.tuan.coffeemanager.model.User;
import com.tuan.coffeemanager.retrofit.Api;
import com.tuan.coffeemanager.sharepref.DataUtil;

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

public class PayActivity extends BaseActivity implements View.OnClickListener, ViewListener.ViewlistDataObjectDoubleListener<OrderDetail, Drink>, ViewListener.ViewDeleteListener, ViewListener.ViewDataListener<User> {

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
    @BindView(R.id.btnPay)
    Button btnPay;

    private Table table = null;
    private String nameUser = null;
    private String order_drink_id = null;
    private User user = null;

    private PayPresenter payPresenter;
    private PayUserPresenter payUserPresenter;
    private PayAdapter payAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);

        showLoading();

        if (getIntent().getExtras() != null) {
            table = (Table) getIntent().getExtras().getSerializable(ConstApp.TABLE_OBJ);
            order_drink_id = getIntent().getExtras().getString(ConstApp.ORDER_DETAIL_ID);
            if (table != null) {
                tvTitle.setText(R.string.text_title_pay);
                tvNumberTable.setVisibility(View.VISIBLE);
                btnPay.setVisibility(View.VISIBLE);
                tvNumberTable.setText(getString(R.string.text_number_table, String.valueOf(table.getNumber())));
                String nameUser = DataUtil.newInstance(this).getDataUser().getName();
                if (nameUser.trim().isEmpty()) {
                    tvUser.setText(getString(R.string.text_employee_bill, getString(R.string.text_example)));
                } else {
                    tvUser.setText(getString(R.string.text_employee_bill, nameUser));
                }
            } else {
                this.nameUser = getIntent().getExtras().getString(ConstApp.NAME_USER, null);
                tvTitle.setText(R.string.text_bill_detail);
                tvNumberTable.setVisibility(View.GONE);
                btnPay.setVisibility(View.GONE);
                if (nameUser != null) {
                    tvUser.setText(getString(R.string.text_employee_bill, nameUser));
                } else {
                    tvUser.setText(getString(R.string.text_employee_bill, getString(R.string.text_example)));
                }
            }
        }

        rvOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        payPresenter = new PayPresenter(this, this);
        payUserPresenter = new PayUserPresenter(this);
        payPresenter.getDataOrderDetail(order_drink_id);

        ivBack.setOnClickListener(this);
        btnPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack: {
                onBackPressed();
                break;
            }
            case R.id.btnPay: {
                showLoading();
                payPresenter.payOrder(PayActivity.this, table.getId());

//                OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                        .connectTimeout(20, TimeUnit.SECONDS)
//                        .writeTimeout(20, TimeUnit.SECONDS)
//                        .readTimeout(30, TimeUnit.SECONDS)
//                        .build();
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl(Api.BASE_URL)
//                        .client(okHttpClient)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//                Api api = retrofit.create(Api.class);
//                Call<NotificationResponse> call = api.pushNotification(user.getToken(), "Coffee Store has a new bill!");
//                call.enqueue(new Callback<NotificationResponse>() {
//                    @Override
//                    public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
//                        if (response != null) {
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<NotificationResponse> call, Throwable t) {
//
//                    }
//                });
                break;
            }
        }
    }

    @Override
    public void onSuccess(OrderDetail orderDetail, List<Drink> drinkList) {
        tvTime.setText(getString(R.string.text_time_bill, orderDetail.getDate()));
        tvTotal.setText(String.valueOf(total(orderDetail.getDrinkList())));
        payAdapter = new PayAdapter(this, orderDetail.getDrinkList());
        rvOrder.setAdapter(payAdapter);
        payAdapter.notifyDataSetChanged();
        btnPay.setEnabled(checkPay(orderDetail.getDrinkList()));
        if (!btnPay.isEnabled()) {
            Toast.makeText(this, "Waiting Bartender complete!", Toast.LENGTH_SHORT).show();
        }
        payUserPresenter.getDataManager();
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

    private int total(List<Drink> drinkOrderList) {
        int sum = 0;
        for (Drink drinkOrder : drinkOrderList) {
            sum += drinkOrder.getAmount() * Integer.parseInt(drinkOrder.getPrice());
        }
        return sum;
    }

    @Override
    public void deleteSuccess(String message) {
        hideLoading();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent intent = NavUtils.getParentActivityIntent(PayActivity.this);
        assert intent != null;
        intent.putExtra("FLAG", 1);
        NavUtils.navigateUpTo(PayActivity.this, intent);
        finish();

    }

    @Override
    public void deleteFailure(String error) {
        hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private Boolean checkPay(List<Drink> drinkOrderList) {
        for (Drink drinkOrder : drinkOrderList) {
            if (drinkOrder.getStatus() == false) {
                return false;
            }
        }
        return true;
    }
}
