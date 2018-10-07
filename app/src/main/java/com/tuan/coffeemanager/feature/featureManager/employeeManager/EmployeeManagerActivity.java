package com.tuan.coffeemanager.feature.featureManager.employeeManager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.base.BaseActivity;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.feature.editProfile.EditProfileActivity;
import com.tuan.coffeemanager.feature.featureManager.employeeManager.adapter.EmployeeManagerAdapter;
import com.tuan.coffeemanager.feature.featureManager.employeeManager.presenter.EmployeeManagerPresenter;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.OnItemClickListener;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.User;
import com.tuan.coffeemanager.widget.DialogLoadingFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmployeeManagerActivity extends BaseActivity implements ViewListener.ViewListDataListener<User> {

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
        showLoading();

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
    public void onSuccess(final List<User> users) {
        hideLoading();
        EmployeeManagerAdapter employeeManagerAdapter = new EmployeeManagerAdapter(this, users);
        rvEmployee.setAdapter(employeeManagerAdapter);
        employeeManagerAdapter.notifyDataSetChanged();
        employeeManagerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                Intent intent = new Intent(EmployeeManagerActivity.this, EditProfileActivity.class);
                intent.putExtra(ConstApp.ID_USER, users.get(position).getId());
                intent.putExtra(ConstApp.STATUS, false);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onFailure(String error) {
        hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseDataApp.isActivity = false;
    }
}
