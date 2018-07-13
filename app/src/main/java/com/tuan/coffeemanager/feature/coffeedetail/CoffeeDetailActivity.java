package com.tuan.coffeemanager.feature.coffeedetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.base.NodeBaseApp;
import com.tuan.coffeemanager.feature.coffeedetail.presenter.CoffeeDetailPresenter;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;

import org.w3c.dom.Node;

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

    private String id;
    private CoffeeDetailPresenter coffeeDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_detail);
        ButterKnife.bind(this);

        id = Objects.requireNonNull(getIntent().getExtras()).getString(NodeBaseApp.DRINK_ID);

        coffeeDetailPresenter = new CoffeeDetailPresenter(this);
        if (id != null) {
            coffeeDetailPresenter.getData(id);
        }

        ivBack.setOnClickListener(this);
        tvTitle.setText(this.getResources().getString(R.string.text_title_detail));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack: {
                onBackPressed();
            }
        }

    }

    private void setView(Drink drink) {
        tvNameCoffee.setText(drink.getName());
        tvDescriptionCoffee.setText(drink.getDescription());
        tvPriceCoffee.setText(String.valueOf(drink.getPrice()));
    }

    @Override
    public void onSuccess(Drink drink) {
        setView(drink);
    }

    @Override
    public void onFailure(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
