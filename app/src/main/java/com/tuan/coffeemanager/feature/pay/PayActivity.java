package com.tuan.coffeemanager.feature.pay;

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
import com.tuan.coffeemanager.feature.pay.adapter.PayAdapter;
import com.tuan.coffeemanager.feature.pay.presenter.PayPresenter;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.model.DrinkOrder;
import com.tuan.coffeemanager.model.OrderDetail;
import com.tuan.coffeemanager.model.Table;
import com.tuan.coffeemanager.sharepref.DataUtil;
import com.tuan.coffeemanager.widget.CustomDialogLoadingFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PayActivity extends AppCompatActivity implements View.OnClickListener, ViewListener.ViewlistDataObjectDoubleListener<OrderDetail, Drink>, ViewListener.ViewDeleteListener {

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
    private String user_id = null;
    private String order_drink_id = null;

    private PayPresenter payPresenter;
    private PayAdapter payAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);

        FirebaseDataApp.isActivity = true;
        CustomDialogLoadingFragment.showLoading(getSupportFragmentManager());

        tvTitle.setText(R.string.text_title_pay);

        if (getIntent().getExtras() != null) {
            table = (Table) getIntent().getExtras().getSerializable(ContactBaseApp.TABLE_OBJ);
            order_drink_id = getIntent().getExtras().getString(ContactBaseApp.ORDER_DETAIL_ID);
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

        rvOrder.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        payPresenter = new PayPresenter(this, this);
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
                CustomDialogLoadingFragment.showLoading(getSupportFragmentManager());
                payPresenter.payOrder(this, table.getId());
                break;
            }
        }
    }

    @Override
    public void onSuccess(OrderDetail orderDetail, List<Drink> drinkList) {
        CustomDialogLoadingFragment.hideLoading();
        tvTime.setText(getString(R.string.text_time_bill, orderDetail.getDate()));
        tvTotal.setText(String.valueOf(total(orderDetail.getDrinkOrderList())));
        payAdapter = new PayAdapter(this, orderDetail.getDrinkOrderList());
        rvOrder.setAdapter(payAdapter);
        payAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String error) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private int total(List<DrinkOrder> drinkOrderList) {
        int sum = 0;
        for (DrinkOrder drinkOrder : drinkOrderList) {
            sum += Integer.parseInt(drinkOrder.getAmount()) * Integer.parseInt(drinkOrder.getPrice());
        }
        return sum;
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseDataApp.isActivity = false;
    }

    @Override
    public void deleteSuccess(String message) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent intent = NavUtils.getParentActivityIntent(this);
        assert intent != null;
        intent.putExtra("FLAG", 1);
        NavUtils.navigateUpTo(this, intent);
        finish();
    }

    @Override
    public void deleteFailure(String error) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
