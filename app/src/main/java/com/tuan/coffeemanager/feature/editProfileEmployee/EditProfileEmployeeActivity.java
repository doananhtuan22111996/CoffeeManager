package com.tuan.coffeemanager.feature.editProfileEmployee;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.base.BaseActivity;
import com.tuan.coffeemanager.ext.KeyBoardExt;
import com.tuan.coffeemanager.feature.editProfileEmployee.listener.IDatePickerListener;
import com.tuan.coffeemanager.feature.editProfileEmployee.listener.IEditProfileEmployeeListener;
import com.tuan.coffeemanager.feature.editProfileEmployee.presenter.EditProfileEmployeePresenter;
import com.tuan.coffeemanager.model.User;
import com.tuan.coffeemanager.sharepref.DataUtil;
import com.tuan.coffeemanager.widget.DialogDatePickerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileEmployeeActivity extends BaseActivity implements View.OnClickListener,
        IEditProfileEmployeeListener.IEditProfileViewListener, IDatePickerListener {

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

    private EditProfileEmployeePresenter editProfileEmployeePresenter;
    private DialogDatePickerFragment dialogDatePickerFragment;
    private User user = new User();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        init();
        user = DataUtil.newInstance(this).getDataUser();
        setView(user);
    }

    private void init() {
        editProfileEmployeePresenter = new EditProfileEmployeePresenter(this);
        dialogDatePickerFragment = DialogDatePickerFragment.newInstance();

        tvTitle.setText(R.string.text_edit_profile);
        btnSave.setText(R.string.text_save);
        ivBack.setOnClickListener(this);
        clContent.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        tvBirthDayDP.setOnClickListener(this);
    }

    private void setView(User user) {
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
            case R.id.tvBirthDayDP: {
                dialogDatePickerFragment.show(getSupportFragmentManager(), "fragment");
                break;
            }
            case R.id.btnSave: {
                showLoading();
                KeyBoardExt.hideKeyBoard(this);
                setProfile();
                editProfileEmployeePresenter.requestUpdateProfile(user);
                break;
            }
        }
    }

    private void setProfile() {
        user.setName(edtName.getText().toString().trim());
        user.setBirthDay(tvBirthDayDP.getText().toString().trim());
        user.setPhoneNumber(edtPhone.getText().toString().trim());
        user.setAddress(edtAddress.getText().toString().trim());
    }

    @Override
    public void updateSuccess(String error) {
        hideLoading();
        DataUtil.newInstance(this).setDataUser(user);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void updateFailure(String error) {
        hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultDate(String strDate) {
        tvBirthDayDP.setText(strDate);
        dialogDatePickerFragment.dismiss();
    }
}
