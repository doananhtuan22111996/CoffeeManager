package com.tuan.coffeemanager.feature.editProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.feature.editProfile.presenter.EditProfileDeletePresenter;
import com.tuan.coffeemanager.feature.editProfile.presenter.EditProfilePresenter;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.User;
import com.tuan.coffeemanager.sharepref.DataUtil;
import com.tuan.coffeemanager.widget.CustomDialogLoadingFragment;
import com.tuan.coffeemanager.widget.CustomKeyBoard;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileActivity extends AppCompatActivity implements ViewListener.ViewDataListener<User>, View.OnClickListener, ViewListener.ViewPostListener, ViewListener.ViewDeleteListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.editBirthDay)
    EditText editBirthDay;
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
        CustomDialogLoadingFragment.showLoading(getSupportFragmentManager());
        FirebaseDataApp.isActivity = true;

        editProfilePresenter = new EditProfilePresenter(this, this);
        id = DataUtil.getIdUser(this);

        if (getIntent().getExtras() != null) {
            id = getIntent().getExtras().getString(ContactBaseApp.ID_USER, null);
            isEdit = getIntent().getExtras().getBoolean(ContactBaseApp.STATUS, false);
            if (!isEdit) {
                tvTitle.setText(R.string.text_profile);
                btnSave.setText(R.string.text_remove_employee);
            } else {
                tvTitle.setText(R.string.text_edit_profile);
                btnSave.setText(R.string.text_save);
            }
        }

        editProfilePresenter.getDataUser(id);

        ivBack.setOnClickListener(this);
        clContent.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onSuccess(User user) {
        CustomDialogLoadingFragment.hideLoading();
        this.user = user;
        setView(user, isEdit);
    }

    @Override
    public void onFailure(String error) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseDataApp.isActivity = false;
    }

    private void setView(User user, Boolean isEdit) {
        if (!isEdit) {
            edtName.setEnabled(false);
            edtAddress.setEnabled(false);
            edtPhone.setEnabled(false);
            editBirthDay.setEnabled(false);
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
        if (user.getBirth_day() != null) {
            editBirthDay.setText(user.getBirth_day());
        }
        if (user.getPhone_number() != null) {
            edtPhone.setText(user.getPhone_number());
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
                CustomKeyBoard.hideKeyBoard(this);
                break;
            }
            case R.id.btnSave: {
                CustomKeyBoard.hideKeyBoard(this);
                if (isEdit) {
                    String name = edtName.getText().toString().trim();
                    String birthDay = editBirthDay.getText().toString().trim();
                    String phone = edtPhone.getText().toString().trim();
                    String address = edtAddress.getText().toString().trim();
                    if (!name.isEmpty()) {
                        user.setName(name);
                    }
                    if (!birthDay.isEmpty()) {
                        user.setBirth_day(birthDay);
                    }
                    if (!phone.isEmpty()) {
                        user.setPhone_number(phone);
                    }
                    if (!address.isEmpty()) {
                        user.setAddress(address);
                    }
                    CustomDialogLoadingFragment.showLoading(getSupportFragmentManager());
                    editProfilePresenter.postDataUser(this, user);
                } else {
                    CustomDialogLoadingFragment.showLoading(getSupportFragmentManager());
                    EditProfileDeletePresenter editProfileDeletePresenter = new EditProfileDeletePresenter(this);
                    editProfileDeletePresenter.deleteDataUser(this, id);
                }
                break;
            }
        }
    }

    @Override
    public void postSuccess(String message) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void postFailure(String error) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteSuccess(String message) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent intent = NavUtils.getParentActivityIntent(this);
        assert intent != null;
        NavUtils.navigateUpTo(this, intent);
        finish();
    }

    @Override
    public void deleteFailure(String error) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
