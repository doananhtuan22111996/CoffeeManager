package com.tuan.coffeemanager.feature.coffeedetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.base.BaseActivity;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.feature.addcoffee.AddCoffeeActivity;
import com.tuan.coffeemanager.feature.coffeedetail.presenter.CoffeeDetailPresenter;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.sharepref.DataUtil;
import com.tuan.coffeemanager.widget.GlideUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoffeeDetailActivity extends BaseActivity implements ViewListener.ViewDataListener<Drink>, View.OnClickListener, ViewListener.ViewDeleteListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvNameCoffee)
    TextView tvNameCoffee;
    @BindView(R.id.tvDescriptionCoffee)
    TextView tvDescriptionCoffee;
    @BindView(R.id.tvPriceCoffee)
    TextView tvPriceCoffee;
    @BindView(R.id.tvEditCoffee)
    TextView tvEditCoffee;
    @BindView(R.id.tvRemoveCoffee)
    TextView tvRemoveCoffee;
    @BindView(R.id.ivCoffee)
    ImageView ivCoffee;
    @BindView(R.id.ivRightEdit)
    ImageView ivRightEdit;
    @BindView(R.id.ivRightRemove)
    ImageView ivRightRemove;

    private String id;
    private CoffeeDetailPresenter coffeeDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_detail);
        ButterKnife.bind(this);

        showLoading();

        setInitView();

        if (getIntent().getExtras() != null) {
            id = getIntent().getExtras().getString(ConstApp.DRINK_ID);
        }

        tvTitle.setText(this.getResources().getString(R.string.text_title_detail));

        coffeeDetailPresenter = new CoffeeDetailPresenter(this, this);
        if (id != null) {
            coffeeDetailPresenter.getData(id);
        }

        ivBack.setOnClickListener(this);
        tvEditCoffee.setOnClickListener(this);
        tvRemoveCoffee.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack: {
                onBackPressed();
                break;
            }
            case R.id.tvEditCoffee: {
                Intent intent = new Intent(CoffeeDetailActivity.this, AddCoffeeActivity.class);
                intent.putExtra(ConstApp.DRINK_ID, id);
                startActivity(intent);
                break;
            }
            case R.id.tvRemoveCoffee: {
                showLoading();
                coffeeDetailPresenter.deleteData(this, id);
                break;
            }
        }

    }

    private void setView(Drink drink) {
        if (drink != null) {
            tvNameCoffee.setText(drink.getName());
            tvDescriptionCoffee.setText(drink.getDescription());
            tvPriceCoffee.setText(String.valueOf(drink.getPrice()));
            if (drink.getUrl() != null && drink.getUuid() != null) {
                GlideUtil.showImage(this, ivCoffee, drink.getUrl());
            }
        }
    }

    @Override
    public void onSuccess(Drink drink) {
        hideLoading();
        setView(drink);
    }

    @Override
    public void onFailure(String error) {
        hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteSuccess(String message) {
        hideLoading();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent intent = NavUtils.getParentActivityIntent(this);
        NavUtils.navigateUpTo(this, intent);
    }

    @Override
    public void deleteFailure(String error) {
        hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private void setInitView() {
        if (DataUtil.getPisitionUser(this).equals("manager")) {
            tvEditCoffee.setEnabled(true);
            tvRemoveCoffee.setEnabled(true);
        } else if (DataUtil.getPisitionUser(this).equals("employee")) {
            tvEditCoffee.setTextColor(getResources().getColor(R.color.hint));
            tvRemoveCoffee.setTextColor(getResources().getColor(R.color.hint));
            tvEditCoffee.setEnabled(false);
            tvRemoveCoffee.setEnabled(false);
        }
    }
}
