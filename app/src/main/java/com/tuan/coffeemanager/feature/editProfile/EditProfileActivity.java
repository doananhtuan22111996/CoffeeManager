package com.tuan.coffeemanager.feature.editProfile;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.feature.editProfile.presenter.EditProfilePresenter;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.User;
import com.tuan.coffeemanager.widget.CustomDialogLoadingFragment;
import com.tuan.coffeemanager.widget.CustomKeyBoard;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditProfileActivity extends AppCompatActivity implements ViewListener.ViewDataListener<User>, View.OnClickListener {

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

    private EditProfilePresenter editProfilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);
        FirebaseDataApp.isActivity = true;
        editProfilePresenter = new EditProfilePresenter(this);

        if (getIntent().getExtras() != null) {
            id = getIntent().getExtras().getString(ContactBaseApp.ID_USER, null);
            isEdit = getIntent().getExtras().getBoolean(ContactBaseApp.STATUS, false);
            if (id != null && !isEdit) {
                CustomDialogLoadingFragment.showLoading(getSupportFragmentManager());
                tvTitle.setText(R.string.text_profile);
                btnSave.setText(R.string.text_remove_employee);
                editProfilePresenter.getDataUser(id);
            }
        }

        ivBack.setOnClickListener(this);
        clContent.setOnClickListener(this);
    }

    @Override
    public void onSuccess(User user) {
        CustomDialogLoadingFragment.hideLoading();
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
        if (!isEdit){
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
        }
    }
}
