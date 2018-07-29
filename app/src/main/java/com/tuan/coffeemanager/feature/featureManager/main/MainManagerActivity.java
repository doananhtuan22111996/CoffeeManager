package com.tuan.coffeemanager.feature.featureManager.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.feature.featureManager.employeeManager.EmployeeManagerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainManagerActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.ivAddCoffee)
    ImageView ivAddCoffee;
    @BindView(R.id.btnEditProfile)
    Button btnEditProfile;
    @BindView(R.id.btnSignUpEmployee)
    Button btnSignUpEmployee;
    @BindView(R.id.btnManagerEmployee)
    Button btnManagerEmployee;
    @BindView(R.id.btnRevenueManager)
    Button btnRevenueManager;
    @BindView(R.id.btnLogout)
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manager);
        ButterKnife.bind(this);

        ivAddCoffee.setVisibility(View.GONE);

        btnManagerEmployee.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnManagerEmployee: {
                startActivity(new Intent(this, EmployeeManagerActivity.class));
            }
        }
    }
}
