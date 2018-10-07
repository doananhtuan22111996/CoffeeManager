package com.tuan.coffeemanager.feature.coffee.fragment.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.base.BaseFragment;
import com.tuan.coffeemanager.constant.ConstApp;
import com.tuan.coffeemanager.feature.editProfile.EditProfileActivity;
import com.tuan.coffeemanager.feature.main.MainActivity;
import com.tuan.coffeemanager.sharepref.DataUtil;
import com.tuan.coffeemanager.widget.DialogLoadingFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MoreFragment extends BaseFragment {

    @BindView(R.id.tvEditProfile)
    TextView tvEditProfile;
    @BindView(R.id.tvAboutCoffee)
    TextView tvAboutCoffee;
    @BindView(R.id.tvLogOut)
    TextView tvLogOut;
    Unbinder unbinder;

    public static MoreFragment newInstance() {
        Bundle args = new Bundle();
        MoreFragment fragment = new MoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                DataUtil.setIdUser(getContext(), null);
                DataUtil.setNameUser(getContext(), null);
                DataUtil.setPosition(getContext(), null);
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    AuthUI authUI = AuthUI.getInstance();
                    authUI.signOut(Objects.requireNonNull(getContext()));
                    hideLoading();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    Objects.requireNonNull(getActivity()).finish();
                }
            }
        });

        tvEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditProfileActivity.class)
                        .putExtra(ConstApp.STATUS, true));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
