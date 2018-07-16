package com.tuan.coffeemanager.widget;

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

import com.tuan.coffeemanager.R;

import java.util.Objects;

public class CustomDialogFragment extends DialogFragment {

    private static CustomDialogFragment customDialogFragment;

    private static CustomDialogFragment newInstance() {
        customDialogFragment = new CustomDialogFragment();
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
        View view = inflater.inflate(R.layout.layout_loading, null);
        alertDialogBuilder.setView(view);
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }
}
