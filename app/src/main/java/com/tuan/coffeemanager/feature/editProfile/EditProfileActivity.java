package com.tuan.coffeemanager.feature.editProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.base.BaseActivity;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.feature.editProfile.presenter.EditProfileDeletePresenter;
import com.tuan.coffeemanager.feature.editProfile.presenter.EditProfilePresenter;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.User;
import com.tuan.coffeemanager.sharepref.DataUtil;
import com.tuan.coffeemanager.ext.KeyBoardExt;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileActivity extends BaseActivity implements ViewListener.ViewDataListener<User>, View.OnClickListener, ViewListener.ViewPostListener, ViewListener.ViewDeleteListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvBirthDayDP)
    TextView tvBirthDayDP;
    @BindView(R.id.edtPhone)
    EditText edtPhone;
    @BindView(R.id.edtAddress)
    EditText edtAddress;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.clContent)
    ConstraintLayout clContent;
    @BindView(R.id.tvPosition)
    TextView tvPosition;

    private String id = null;
    private boolean isEdit = false;
    private User user = null;

    private EditProfilePresenter editProfilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        showLoading();

        editProfilePresenter = new EditProfilePresenter(this, this);

        if (getIntent().getExtras() != null) {
            id = getIntent().getExtras().getString(ConstApp.ID_USER, null);
            isEdit = getIntent().getExtras().getBoolean(ConstApp.STATUS, false);
            if (!isEdit) {
                tvTitle.setText(R.string.text_profile);
                btnSave.setText(R.string.text_remove_employee);
            } else {
                tvTitle.setText(R.string.text_edit_profile);
                btnSave.setText(R.string.text_save);
            }
            if (id == null){
                id = DataUtil.newInstance(this).getDataUser().getId();
            }
        }

        editProfilePresenter.getDataUser(id);

        ivBack.setOnClickListener(this);
        clContent.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onSuccess(User user) {
        hideLoading();
        this.user = user;
        setView(user, isEdit);
    }

    @Override
    public void onFailure(String error) {
        hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private void setView(User user, Boolean isEdit) {
        if (!isEdit) {
            edtName.setEnabled(false);
            edtAddress.setEnabled(false);
            edtPhone.setEnabled(false);
            tvBirthDayDP.setEnabled(false);
        }
        if (user.getName() != null) {
            edtName.setText(user.getName());
        }
        if (user.getPosition() != null) {
            tvPosition.setText(user.getPosition());
        }
        if (user.getEmail() != null) {
            tvEmail.setText(user.getEmail());
        }
        if (user.getBirthDay() != null) {
            tvBirthDayDP.setText(user.getBirthDay());
        }
        if (user.getPhoneNumber() != null) {
            edtPhone.setText(user.getPhoneNumber());
        }
        if (user.getAddress() != null) {
            edtAddress.setText(user.getAddress());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack: {
                onBackPressed();
                break;
            }
            case R.id.clContent: {
                KeyBoardExt.hideKeyBoard(this);
                break;
            }
            case R.id.btnSave: {
                KeyBoardExt.hideKeyBoard(this);
                if (isEdit) {
                    String name = edtName.getText().toString().trim();
                    String birthDay = tvBirthDayDP.getText().toString().trim();
                    String phone = edtPhone.getText().toString().trim();
                    String address = edtAddress.getText().toString().trim();
                    if (!name.isEmpty()) {
                        user.setName(name);
                    }
                    if (!birthDay.isEmpty()) {
                        user.setBirthDay(birthDay);
                    }
                    if (!phone.isEmpty()) {
                        user.setPhoneNumber(phone);
                    }
                    if (!address.isEmpty()) {
                        user.setAddress(address);
                    }
                    showLoading();
                    editProfilePresenter.postDataUser(this, user);
                } else {
                    showLoading();
                    EditProfileDeletePresenter editProfileDeletePresenter = new EditProfileDeletePresenter(this);
                    editProfileDeletePresenter.deleteDataUser(this, id);
                }
                break;
            }
        }
    }

    @Override
    public void postSuccess(String message) {
        hideLoading();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void postFailure(String error) {
        hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteSuccess(String message) {
        hideLoading();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent intent = NavUtils.getParentActivityIntent(this);
        assert intent != null;
        NavUtils.navigateUpTo(this, intent);
        finish();
    }

    @Override
    public void deleteFailure(String error) {
        hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
