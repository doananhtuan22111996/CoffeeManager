package com.tuan.coffeemanager.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tuan.coffeemanager.widget.DialogLoadingFragment;

public abstract class BaseActivity extends AppCompatActivity implements AppEventListener {

    private DialogLoadingFragment dialogLoadingFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogLoadingFragment = DialogLoadingFragment.newInstance();
    }

    public void showLoading() {
        dialogLoadingFragment.show(getSupportFragmentManager(), "fragment");
    }

    public void hideLoading() {
        dialogLoadingFragment.dismiss();
    }

    @Override
    public void eventShowLoading() {
        showLoading();
    }

    @Override
    public void eventHideLoading() {
        hideLoading();
    }
}
