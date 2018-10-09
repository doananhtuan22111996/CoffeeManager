package com.tuan.coffeemanager.feature.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.base.BaseFragment;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.feature.coffee.CoffeeActivity;
import com.tuan.coffeemanager.feature.featureBartender.OrderBartenderActivity;
import com.tuan.coffeemanager.feature.featureManager.main.MainManagerActivity;
import com.tuan.coffeemanager.feature.main.fragment.listener.ISignInListener;
import com.tuan.coffeemanager.feature.main.reset.ResetPasswordActivity;
import com.tuan.coffeemanager.feature.main.fragment.presenter.SignInPresenter;
import com.tuan.coffeemanager.model.User;
import com.tuan.coffeemanager.sharepref.DataUtil;
import com.tuan.coffeemanager.ext.KeyBoardExt;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SignInFragment extends BaseFragment implements ISignInListener.ISignInViewListener {

    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtPassword)
    EditText edtPassword;
    @BindView(R.id.btnSignIn)
    Button btnSignIn;
    @BindView(R.id.tvResetPassword)
    TextView tvResetPassword;
    Unbinder unbinder;

    private SignInPresenter signInPresenter;

    public static SignInFragment newInstance() {
        Bundle args = new Bundle();
        SignInFragment fragment = new SignInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        signInPresenter = new SignInPresenter(this);

        init();
    }

    private void init() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyBoardExt.hideKeyBoard(getActivity());
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(getActivity(), ConstApp.SIGN_IN_E001, Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(getActivity(), ConstApp.SIGN_IN_E002, Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(getActivity(), ConstApp.SIGN_IN_E003, Toast.LENGTH_SHORT).show();
                } else {
                    showLoading();
                    KeyBoardExt.hideKeyBoard(getActivity());
                    signInPresenter.signIn(email, password);
                }
            }
        });

        tvResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ResetPasswordActivity.class));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private Boolean isValidEmail(String email) {
        if (email.isEmpty()) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    @Override
    public void signInSuccess(User user) {
        hideLoading();
        if (getActivity() != null) {
            DataUtil.newInstance(getActivity()).setDataUser(user);
            switch (user.getPosition()) {
                case ConstApp.EMPLOYEE:
                    startActivity(new Intent(getActivity(), CoffeeActivity.class));
                    getActivity().finish();
                    break;
                case ConstApp.BARTENDER_POSITION:
                    startActivity(new Intent(getActivity(), OrderBartenderActivity.class));
                    getActivity().finish();
                    break;
                default:
                    startActivity(new Intent(getActivity(), MainManagerActivity.class));
                    getActivity().finish();
                    break;
            }
            Toast.makeText(getActivity(), ConstApp.SIGN_IN_E005, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void signInFailure(String error) {
        hideLoading();
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }
}
