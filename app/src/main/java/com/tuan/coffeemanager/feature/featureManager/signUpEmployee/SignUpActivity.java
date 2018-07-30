package com.tuan.coffeemanager.feature.featureManager.signUpEmployee;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.feature.featureManager.signUpEmployee.presenter.SignUpPresenter;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.User;
import com.tuan.coffeemanager.widget.CustomDialogLoadingFragment;
import com.tuan.coffeemanager.widget.CustomKeyBoard;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, ViewListener.ViewDataListener<User>, ViewListener.ViewPostListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.edtConfirmPassword)
    EditText edtConfirmPassword;
    @BindView(R.id.edtName)
    EditText edtName;
    @BindView(R.id.spPosition)
    Spinner spPosition;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    @BindView(R.id.clContent)
    ConstraintLayout clContent;

    String position = null;
    private SignUpPresenter signUpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_employee);
        ButterKnife.bind(this);
        FirebaseDataApp.isActivity = true;

        signUpPresenter = new SignUpPresenter(this, this);

        init();
    }

    private void init() {
        ivBack.setOnClickListener(this);
        clContent.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        spPosition.setOnItemSelectedListener(this);
        tvTitle.setText(getString(R.string.text_sign_up));
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
            case R.id.btnSignUp: {
                CustomKeyBoard.hideKeyBoard(this);
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String cofpassword = edtConfirmPassword.getText().toString().trim();
                String name = edtName.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(this, R.string.text_mesage_email_empty, Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(this, R.string.text_message_email_valid, Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(this, R.string.text_message_password_empty, Toast.LENGTH_SHORT).show();
                } else if (cofpassword.isEmpty()) {
                    Toast.makeText(this, R.string.text_message_confirm_empty, Toast.LENGTH_SHORT).show();
                } else if (!password.equals(cofpassword)) {
                    Toast.makeText(this, getString(R.string.text_message_confirm_password), Toast.LENGTH_SHORT).show();
                } else if (name.isEmpty()) {
                    Toast.makeText(this, R.string.text_message_name_empty, Toast.LENGTH_SHORT).show();
                } else {
                    CustomDialogLoadingFragment.showLoading(getSupportFragmentManager());
                    signUpPresenter.signUp(email, password, name, this);
                }
                break;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseDataApp.isActivity = false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        position = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        position = adapterView.getSelectedItem().toString();
    }

    @Override
    public void onSuccess(User user) {
        user.setPosition(position);
        signUpPresenter.postDataUser(this, user);
    }

    @Override
    public void onFailure(String error) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void postSuccess(String message) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Intent intent = NavUtils.getParentActivityIntent(this);
        assert intent != null;
        NavUtils.navigateUpTo(this, intent);
    }

    @Override
    public void postFailure(String error) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private Boolean isValidEmail(String email) {
        if (email.isEmpty()) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
}
