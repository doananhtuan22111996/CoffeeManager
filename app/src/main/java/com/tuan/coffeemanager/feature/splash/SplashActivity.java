package com.tuan.coffeemanager.feature.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.feature.coffee.CoffeeActivity;
import com.tuan.coffeemanager.feature.featureBartender.OrderBartenderActivity;
import com.tuan.coffeemanager.feature.featureManager.main.MainManagerActivity;
import com.tuan.coffeemanager.feature.main.MainActivity;
import com.tuan.coffeemanager.feature.splash.presenter.SplashPresenter;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.User;
import com.tuan.coffeemanager.sharepref.DataUtil;

public class SplashActivity extends AppCompatActivity implements ViewListener.ViewDataListener<User> {

    private SplashPresenter splashPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseDataApp.isActivity = true;
        splashPresenter = new SplashPresenter(this);

        String id = DataUtil.getIdUser(this);
        if (id.equals("")) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        } else {
            splashPresenter.getDataUser(id);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseDataApp.isActivity = false;
    }

    @Override
    public void onSuccess(User user) {
        if (user.getPosition().equals("employee")) {
            startActivity(new Intent(this, CoffeeActivity.class));
            finish();
        } else if (user.getPosition().equals("bartender")) {
            startActivity(new Intent(this, OrderBartenderActivity.class));
            finish();
        } else if (user.getPosition().equals("manager")) {
            startActivity(new Intent(this, MainManagerActivity.class));
            finish();
        }
    }

    @Override
    public void onFailure(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
