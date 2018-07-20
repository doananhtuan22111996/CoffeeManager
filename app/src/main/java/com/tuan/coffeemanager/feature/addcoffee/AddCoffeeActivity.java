package com.tuan.coffeemanager.feature.addcoffee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.feature.addcoffee.presenter.EditCoffeePresenter;
import com.tuan.coffeemanager.feature.addcoffee.presenter.PostCoffeePresenter;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.Drink;
import com.tuan.coffeemanager.widget.CustomDialogLoadingFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCoffeeActivity extends AppCompatActivity implements ViewListener.ViewDataListener<Drink>, ViewListener.ViewPostListener, View.OnClickListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivCoffee)
    ImageView ivCoffee;
    @BindView(R.id.edtNameCoffee)
    EditText edtNameCoffee;
    @BindView(R.id.edtDescriptionCoffee)
    EditText edtDescriptionCoffee;
    @BindView(R.id.edtPriceCoffee)
    EditText edtPriceCoffee;
    @BindView(R.id.btnSaveCoffee)
    Button btnSaveCoffee;

    private String id = null;
    private Drink drink = null;
    private EditCoffeePresenter editCoffeePresenter;
    private PostCoffeePresenter postCoffeePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_coffee);
        ButterKnife.bind(this);
        id = Objects.requireNonNull(getIntent().getExtras()).getString(ContactBaseApp.DRINK_ID, "").trim();
        if (!Objects.requireNonNull(id).isEmpty()) {
            CustomDialogLoadingFragment.showLoading(getSupportFragmentManager());
            editCoffeePresenter = new EditCoffeePresenter(this, this);
            editCoffeePresenter.getDataDrink(id);
        } else {
            postCoffeePresenter = new PostCoffeePresenter(this);
        }

        tvTitle.setText(R.string.text_edit_drink_title);
        ivBack.setOnClickListener(this);
        btnSaveCoffee.setOnClickListener(this);
    }

    @Override
    public void onSuccess(Drink drink) {
        CustomDialogLoadingFragment.hideLoading();
        this.drink = drink;
        setView(drink);
    }

    private void setView(Drink drink) {
        edtNameCoffee.setText(drink.getName());
        edtDescriptionCoffee.setText(drink.getDescription());
        edtPriceCoffee.setText(String.valueOf(drink.getPrice()));
    }

    @Override
    public void onFailure(String error) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack: {
                onBackPressed();
                break;
            }
            case R.id.btnSaveCoffee: {
                String name = edtNameCoffee.getText().toString().trim();
                String description = edtDescriptionCoffee.getText().toString().trim();
                String price = edtPriceCoffee.getText().toString().trim();
                if (name.isEmpty()) {
                    Toast.makeText(this, R.string.text_message_name_empty, Toast.LENGTH_SHORT).show();
                } else if (description.isEmpty()) {
                    Toast.makeText(this, R.string.text_message_description_empty, Toast.LENGTH_SHORT).show();
                } else if (price.isEmpty()) {
                    Toast.makeText(this, R.string.text_message_price_empty, Toast.LENGTH_SHORT).show();
                } else {
                    if (id.isEmpty()) {
                        Drink drink = new Drink(null, name, description, Integer.parseInt(price), 0);
                        postCoffeePresenter.postDataDrink(this, drink);
                    } else {
                        Drink drink = new Drink(id, name, description, Integer.parseInt(price), this.drink.getPurchases());
                        editCoffeePresenter.editDataDrink(this, drink);
                    }
                }
                break;
            }
        }
    }

    @Override
    public void postSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postFailure(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
