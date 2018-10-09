package com.tuan.coffeemanager.feature.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.base.BaseFragment;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.feature.coffee.CoffeeActivity;
import com.tuan.coffeemanager.feature.featureBartender.OrderBartenderActivity;
import com.tuan.coffeemanager.feature.featureManager.main.MainManagerActivity;
import com.tuan.coffeemanager.feature.main.reset.ResetPasswordActivity;
import com.tuan.coffeemanager.feature.main.fragment.presenter.SignInPresenter;
import com.tuan.coffeemanager.interactor.FirebaseDataApp;
import com.tuan.coffeemanager.listener.ViewListener;
import com.tuan.coffeemanager.model.User;
import com.tuan.coffeemanager.sharepref.DataUtil;
import com.tuan.coffeemanager.widget.DialogLoadingFragment;
import com.tuan.coffeemanager.widget.KeyBoardUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SignInFragment extends BaseFragment implements ViewListener.ViewSignInListener, ViewListener.ViewDataListener<User>, ViewListener.ViewPostListener {

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
    private String id;

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

        signInPresenter = new SignInPresenter(this, this, this);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyBoardUtil.hideKeyBoard(getActivity());
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
                    KeyBoardUtil.hideKeyBoard(getActivity());
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
    public void onSuccess(final String id) {
        this.id = id;
        DataUtil.setIdUser(getContext(), id);
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
                    Log.d(ConstApp.TOKEN, task.getResult().getToken());
                    signInPresenter.postTokenUser(getActivity(), id, task.getResult().getToken());
                }
            }
        });

    }

    @Override
    public void onSuccess(User user) {
        hideLoading();
        DataUtil.setNameUser(getContext(), user.getName());
        DataUtil.setPosition(getContext(), user.getPosition());
        if (user.getPosition().equals(ConstApp.EMPLOYEE)) {
            startActivity(new Intent(getActivity(), CoffeeActivity.class));
            if (getActivity() != null) {
                getActivity().finish();
            }
        } else if (user.getPosition().equals(ConstApp.BARTENDER_POSITION)) {
            startActivity(new Intent(getActivity(), OrderBartenderActivity.class));
            if (getActivity() != null) {
                getActivity().finish();
            }
        } else {
            startActivity(new Intent(getActivity(), MainManagerActivity.class));
            if (getActivity() != null) {
                getActivity().finish();
            }
        }
    }

    @Override
    public void onFailure(String error) {
        hideLoading();
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
    public void postSuccess(String message) {
        signInPresenter.gerDataUser(id);
    }

    @Override
    public void postFailure(String error) {
        hideLoading();
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
