package com.tuan.coffeemanager.feature.featureManager.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.base.BaseActivity;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.feature.editProfile.EditProfileActivity;
import com.tuan.coffeemanager.feature.featureManager.coffeeManage.CoffeeManagerActivity;
import com.tuan.coffeemanager.feature.featureManager.employeeManager.EmployeeManagerActivity;
import com.tuan.coffeemanager.feature.featureManager.revenue.RevenueManagerActivity;
import com.tuan.coffeemanager.feature.featureManager.signup.SignUpActivity;
import com.tuan.coffeemanager.feature.featureManager.tableManager.TableManagerActivity;
import com.tuan.coffeemanager.feature.main.MainActivity;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.model.User;
import com.tuan.coffeemanager.sharepref.DataUtil;
import com.tuan.coffeemanager.widget.DialogLoadingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainManagerActivity extends BaseActivity implements View.OnClickListener {

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
    @BindView(R.id.btnCoffeeManager)
    Button btnCoffeeManager;
    @BindView(R.id.ivCoffee)
    ImageView ivCoffee;
    @BindView(R.id.btnTableManager)
    Button btnTableManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manager);
        ButterKnife.bind(this);

        ivAddCoffee.setVisibility(View.GONE);

        btnManagerEmployee.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnEditProfile.setOnClickListener(this);
        btnSignUpEmployee.setOnClickListener(this);
        btnRevenueManager.setOnClickListener(this);
        btnCoffeeManager.setOnClickListener(this);
        btnTableManager.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnManagerEmployee: {
                startActivity(new Intent(this, EmployeeManagerActivity.class));
                break;
            }
            case R.id.btnLogout: {
                showLoading();
                DataUtil.newInstance(this).setDataUser(null);
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    AuthUI authUI = AuthUI.getInstance();
                    authUI.signOut(this);
                    hideLoading();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
                break;
            }
            case R.id.btnEditProfile: {
                startActivity(new Intent(this, EditProfileActivity.class)
                        .putExtra(ConstApp.ID_USER, DataUtil.newInstance(this).getDataUser().getId())
                        .putExtra(ConstApp.STATUS, true));
                break;
            }
            case R.id.btnSignUpEmployee: {
                startActivity(new Intent(this, SignUpActivity.class));
                break;
            }
            case R.id.btnRevenueManager: {
                startActivity(new Intent(this, RevenueManagerActivity.class));
                break;
            }
            case R.id.btnCoffeeManager: {
                startActivity(new Intent(this, CoffeeManagerActivity.class));
                break;
            }
            case R.id.btnTableManager: {
                startActivity(new Intent(this, TableManagerActivity.class));
                break;
            }
        }
    }

}
