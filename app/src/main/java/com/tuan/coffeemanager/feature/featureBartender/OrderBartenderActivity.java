package com.tuan.coffeemanager.feature.featureBartender;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.base.BaseActivity;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.feature.featureBartender.OrderDetail.OrderDetailBartenderActivity;
import com.tuan.coffeemanager.feature.featureBartender.adapter.OrderBartenderAdapter;
import com.tuan.coffeemanager.feature.featureBartender.presenter.OrderBartenderPresenter;
import com.tuan.coffeemanager.feature.main.MainActivity;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.OrderBartender;
import com.tuan.coffeemanager.sharepref.DataUtil;
import com.tuan.coffeemanager.widget.DialogLoadingFragment;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderBartenderActivity extends BaseActivity implements ViewListener.ViewListDataListener<OrderBartender> {

    @BindView(R.id.rvBill)
    RecyclerView rvBill;
    @BindView(R.id.tvLogout)
    TextView tvLogout;

    private OrderBartenderPresenter orderBartenderPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_bartender);
        ButterKnife.bind(this);
        showLoading();
        orderBartenderPresenter = new OrderBartenderPresenter(this);
        orderBartenderPresenter.getListBillBartender();
        rvBill.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                DataUtil.setIdUser(OrderBartenderActivity.this, null);
                DataUtil.setNameUser(OrderBartenderActivity.this, null);
                DataUtil.setPosition(OrderBartenderActivity.this, null);
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    AuthUI authUI = AuthUI.getInstance();
                    authUI.signOut(Objects.requireNonNull(OrderBartenderActivity.this));
                    hideLoading();
                    startActivity(new Intent(OrderBartenderActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void onSuccess(final List<OrderBartender> orderBartenders) {
        hideLoading();
        OrderBartenderAdapter orderBartenderAdapter = new OrderBartenderAdapter(this, orderBartenders);
        rvBill.setAdapter(orderBartenderAdapter);
        orderBartenderAdapter.notifyDataSetChanged();
        orderBartenderAdapter.setOnClickListener(new OrderBartenderAdapter.OnClickListener() {
            @Override
            public void onItemCLickListener(int position) {
                Intent intent = new Intent(OrderBartenderActivity.this, OrderDetailBartenderActivity.class);
                intent.putExtra(ConstApp.BARTENDER, orderBartenders.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onFailure(String error) {
        hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
