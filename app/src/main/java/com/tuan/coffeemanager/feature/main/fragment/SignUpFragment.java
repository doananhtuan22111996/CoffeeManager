package com.tuan.coffeemanager.feature.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.feature.coffee.CoffeeActivity;
import com.tuan.coffeemanager.feature.main.fragment.presenter.SignUpPresenter;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.User;
import com.tuan.coffeemanager.widget.CustomDialogLoadingFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SignUpFragment extends Fragment implements ViewListener.ViewDataListener<User> {

    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.edtConfirmPassword)
    EditText edtConfirmPassword;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    Unbinder unbinder;

    private SignUpPresenter signUpPresenter;

    public static SignUpFragment newInstance() {
        Bundle args = new Bundle();
        SignUpFragment fragment = new SignUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signUpPresenter = new SignUpPresenter(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String cofpassword = edtConfirmPassword.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(getActivity(), R.string.text_mesage_email_empty, Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(getActivity(), R.string.text_message_email_valid, Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(getActivity(), R.string.text_message_password_empty, Toast.LENGTH_SHORT).show();
                } else if (cofpassword.isEmpty()) {
                    Toast.makeText(getActivity(), R.string.text_message_confirm_empty, Toast.LENGTH_SHORT).show();
                } else if (!password.equals(cofpassword)) {
                    Toast.makeText(getActivity(), getString(R.string.text_message_confirm_password), Toast.LENGTH_SHORT).show();
                } else {
                    CustomDialogLoadingFragment.showLoading(getFragmentManager());
                    signUpPresenter.signUp(email, password, getActivity());
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onSuccess(User user) {
        CustomDialogLoadingFragment.hideLoading();
        startActivity(new Intent(getActivity(), CoffeeActivity.class));
        Objects.requireNonNull(getActivity()).finish();
    }

    @Override
    public void onFailure(String error) {

    }

    private Boolean isValidEmail(String email) {
        if (email.isEmpty()) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
}
