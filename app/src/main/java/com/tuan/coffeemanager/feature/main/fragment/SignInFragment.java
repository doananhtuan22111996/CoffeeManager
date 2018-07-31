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
import android.widget.TextView;
import android.widget.Toast;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.feature.coffee.CoffeeActivity;
import com.tuan.coffeemanager.feature.featureManager.main.MainManagerActivity;
import com.tuan.coffeemanager.feature.main.reset.ResetPasswordActivity;
import com.tuan.coffeemanager.feature.main.fragment.presenter.SignInPresenter;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.User;
import com.tuan.coffeemanager.sharepref.DataUtil;
import com.tuan.coffeemanager.widget.CustomDialogLoadingFragment;
import com.tuan.coffeemanager.widget.CustomKeyBoard;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SignInFragment extends Fragment implements ViewListener.ViewSignInListener, ViewListener.ViewDataListener<User> {

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

        FirebaseDataApp.isActivity = true;
        signInPresenter = new SignInPresenter(this, this);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomKeyBoard.hideKeyBoard(getActivity());
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.text_mesage_email_empty), Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(getActivity(), getString(R.string.text_message_email_valid), Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.text_message_password_empty), Toast.LENGTH_SHORT).show();
                } else {
                    CustomDialogLoadingFragment.showLoading(getFragmentManager());
                    CustomKeyBoard.hideKeyBoard(getActivity());
                    signInPresenter.signIn(email, password, getActivity());
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

    @Override
    public void onSuccess(String id) {
        DataUtil.setIdUser(getContext(), id);
        FirebaseDataApp.isActivity = true;
        signInPresenter.gerDataUser(id);
    }

    @Override
    public void onSuccess(User user) {
        CustomDialogLoadingFragment.hideLoading();
        DataUtil.setNameUser(getContext(), user.getName());
        if (user.getPosition().equals("employee")) {
            startActivity(new Intent(getActivity(), CoffeeActivity.class));
            Objects.requireNonNull(getActivity()).finish();
        } else {
            startActivity(new Intent(getActivity(), MainManagerActivity.class));
            Objects.requireNonNull(getActivity()).finish();
        }
    }

    @Override
    public void onFailure(String error) {
        CustomDialogLoadingFragment.hideLoading();
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    private Boolean isValidEmail(String email) {
        if (email.isEmpty()) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        FirebaseDataApp.isActivity = false;
    }
}
