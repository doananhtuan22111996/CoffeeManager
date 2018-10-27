package com.tuan.coffeemanager.feature.featureManager.revenue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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
import com.tuan.coffeemanager.feature.featureManager.revenue.adapter.RevenueManagerAdapter;
import com.tuan.coffeemanager.feature.featureManager.revenue.presenter.RevenueManagerPresenter;
import com.tuan.coffeemanager.feature.pay.PayActivity;
import com.tuan.coffeemanager.listener.OnItemClickListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.OrderDetail;
import com.tuan.coffeemanager.model.User;
import com.tuan.coffeemanager.ext.KeyBoardExt;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RevenueManagerActivity extends BaseActivity implements ViewListener.ViewListDataListener<OrderDetail>, ViewListener.ViewDataListener<User>, TextWatcher, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.rvBill)
    RecyclerView rvBill;
    @BindView(R.id.tvTotalBill)
    TextView tvTotalBill;
    @BindView(R.id.tvTotalMoney)
    TextView tvTotalMoney;
    @BindView(R.id.srBill)
    SwipeRefreshLayout srBill;

    private RevenueManagerPresenter revenueManagerPresenter;
    private RevenueManagerAdapter revenueManagerAdapter;
    private String orderDetail_id = null;
    private List<OrderDetail> orderDetailList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_manager);
        ButterKnife.bind(this);
        showLoading();

        tvTitle.setText(R.string.text_title_revenue_manager);

        revenueManagerPresenter = new RevenueManagerPresenter(this, this);
        revenueManagerPresenter.getDataBill();

        rvBill.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        edtSearch.addTextChangedListener(this);

        srBill.setOnRefreshListener(this);
    }

    @Override
    public void onSuccess(final List<OrderDetail> orderDetailList) {
        hideLoading();
        srBill.setRefreshing(false);
        tvTotalBill.setText(getString(R.string.text_total_bill_manger, String.valueOf(orderDetailList.size())));
        tvTotalMoney.setText(getString(R.string.text_total_money_manager, String.valueOf(totalBill(orderDetailList))));
        this.orderDetailList = orderDetailList;
        revenueManagerAdapter = new RevenueManagerAdapter(this, orderDetailList);
        rvBill.setAdapter(revenueManagerAdapter);
        revenueManagerAdapter.notifyDataSetChanged();
        revenueManagerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                showLoading();
                orderDetail_id = revenueManagerAdapter.getOrderDetailList().get(position).getOrder_detail_id();
                revenueManagerPresenter.getDataUser(revenueManagerAdapter.getOrderDetailList().get(position).getUser_id());
            }
        });
    }

    @Override
    public void onSuccess(User user) {
        hideLoading();
        KeyBoardExt.hideKeyBoard(this);
        startActivity(new Intent(RevenueManagerActivity.this, PayActivity.class)
                .putExtra(ConstApp.ORDER_DETAIL_ID, orderDetail_id)
                .putExtra(ConstApp.NAME_USER, user.getName()));
    }

    @Override
    public void onFailure(String error) {
        hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private int totalOnlyBill(OrderDetail orderDetail) {
        int sum = 0;
        for (DrinkOrder drinkOrder : orderDetail.getDrinkOrderList()) {
            sum += Integer.parseInt(drinkOrder.getAmount()) * Integer.parseInt(drinkOrder.getPrice());
        }
        return sum;
    }

    private int totalBill(List<OrderDetail> orderDetailList) {
        int sum = 0;
        for (OrderDetail orderDetail : orderDetailList) {
            sum += totalOnlyBill(orderDetail);
        }
        return sum;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        searchBill(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void searchBill(String search) {
        List<OrderDetail> orderDetailList = new ArrayList<>();
        if (search.isEmpty()) {
            revenueManagerAdapter.setOrderDetailList(this.orderDetailList);
            revenueManagerAdapter.notifyDataSetChanged();
            tvTotalBill.setText(getString(R.string.text_total_bill_manger, String.valueOf(this.orderDetailList.size())));
            tvTotalMoney.setText(getString(R.string.text_total_money_manager, String.valueOf(totalBill(this.orderDetailList))));
        } else {
            for (OrderDetail orderDetail : this.orderDetailList) {
                if (orderDetail.getDate().contains(search)) {
                    orderDetailList.add(orderDetail);
                }
            }
            revenueManagerAdapter.setOrderDetailList(orderDetailList);
            revenueManagerAdapter.notifyDataSetChanged();
            tvTotalBill.setText(getString(R.string.text_total_bill_manger, String.valueOf(orderDetailList.size())));
            tvTotalMoney.setText(getString(R.string.text_total_money_manager, String.valueOf(totalBill(orderDetailList))));
        }
    }

    @Override
    public void onRefresh() {
        revenueManagerPresenter.getDataBill();
    }
}
