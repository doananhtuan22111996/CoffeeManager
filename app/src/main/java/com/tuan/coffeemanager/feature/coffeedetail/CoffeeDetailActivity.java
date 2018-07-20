package com.tuan.coffeemanager.feature.coffeedetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.feature.addcoffee.AddCoffeeActivity;
import com.tuan.coffeemanager.feature.coffeedetail.presenter.CoffeeDetailPresenter;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.widget.CustomDialogLoadingFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoffeeDetailActivity extends AppCompatActivity implements ViewListener.ViewDataListener<Drink>, View.OnClickListener {

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

    private String id;
    private CoffeeDetailPresenter coffeeDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_detail);
        ButterKnife.bind(this);
        CustomDialogLoadingFragment.showLoading(getSupportFragmentManager());

        id = Objects.requireNonNull(getIntent().getExtras()).getString(ContactBaseApp.DRINK_ID);

        tvTitle.setText(this.getResources().getString(R.string.text_title_detail));

        coffeeDetailPresenter = new CoffeeDetailPresenter(this);
        if (id != null) {
            coffeeDetailPresenter.getData(id);
        }

        ivBack.setOnClickListener(this);
        tvEditCoffee.setOnClickListener(this);
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
                intent.putExtra(ContactBaseApp.DRINK_ID, id);
                startActivity(intent);
                break;
            }
        }

    }

    private void setView(Drink drink) {
        if (drink != null) {
            tvNameCoffee.setText(drink.getName());
            tvDescriptionCoffee.setText(drink.getDescription());
            tvPriceCoffee.setText(String.valueOf(drink.getPrice()));
        }
    }

    @Override
    public void onSuccess(Drink drink) {
        CustomDialogLoadingFragment.hideLoading();
        setView(drink);
    }

    @Override
    public void onFailure(String error) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
