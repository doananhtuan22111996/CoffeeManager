package com.tuan.coffeemanager.feature.featureManager.employeeManager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.feature.featureManager.employeeManager.adapter.EmployeeManagerAdapter;
import com.tuan.coffeemanager.feature.featureManager.employeeManager.presenter.EmployeeManagerPresenter;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.User;
import com.tuan.coffeemanager.widget.CustomDialogLoadingFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmployeeManagerActivity extends AppCompatActivity implements ViewListener.ViewListDataListener<User> {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.rvEmployee)
    RecyclerView rvEmployee;
    private EmployeeManagerPresenter employeeManagerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_manager);
        ButterKnife.bind(this);
        FirebaseDataApp.isActivity = true;
        CustomDialogLoadingFragment.showLoading(getSupportFragmentManager());

        employeeManagerPresenter = new EmployeeManagerPresenter(this);
        employeeManagerPresenter.getDataUser();

        tvTitle.setText(R.string.text_title_employee_manger);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        rvEmployee.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onSuccess(List<User> users) {
        CustomDialogLoadingFragment.hideLoading();
        EmployeeManagerAdapter employeeManagerAdapter = new EmployeeManagerAdapter(this, users);
        rvEmployee.setAdapter(employeeManagerAdapter);
        employeeManagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(String error) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
