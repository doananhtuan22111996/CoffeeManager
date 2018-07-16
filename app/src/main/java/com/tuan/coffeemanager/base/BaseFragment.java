package com.tuan.coffeemanager.base;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.widget.CustomDialogFragment;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseFragment extends DialogFragment {

    private static BaseFragment customDialogFragment;

    private static BaseFragment newInstance() {
        customDialogFragment = new BaseFragment();
        return customDialogFragment;
    }

    public static void showLoading(FragmentManager fragmentManager) {
        if (customDialogFragment == null) {
            customDialogFragment = newInstance();
        }
        customDialogFragment.show(fragmentManager, "fragment");
    }

    public static void hideLoading() {
        customDialogFragment.dismiss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_main, null);
        alertDialogBuilder.setView(view);
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

}
