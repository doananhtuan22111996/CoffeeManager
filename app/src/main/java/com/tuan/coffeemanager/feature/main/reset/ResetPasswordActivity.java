package com.tuan.coffeemanager.feature.main.reset;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.base.BaseActivity;
import com.tuan.coffeemanager.feature.main.reset.presenter.ResetPasswordPresenter;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.widget.DialogLoadingFragment;
import com.tuan.coffeemanager.widget.KeyBoardUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResetPasswordActivity extends BaseActivity implements ViewListener.ViewResetPasswordListener {

    private ResetPasswordPresenter resetPasswordPresenter;

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.btnSendEmail)
    Button btnSendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        resetPasswordPresenter = new ResetPasswordPresenter(this);

        tvTitle.setText(R.string.text_reset_password_title);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyBoardUtil.hideKeyBoard(ResetPasswordActivity.this);
                if (edtEmail.getText().toString().trim().isEmpty()) {
                    Toast.makeText(ResetPasswordActivity.this, getString(R.string.text_mesage_email_empty), Toast.LENGTH_SHORT).show();
                } else {
                    showLoading();
                    resetPasswordPresenter.resetPassword(ResetPasswordActivity.this, edtEmail.getText().toString().trim());
                }
            }
        });
    }

    @Override
    public void onSuccess(String message) {
        hideLoading();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onFailure(String error) {
        hideLoading();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
