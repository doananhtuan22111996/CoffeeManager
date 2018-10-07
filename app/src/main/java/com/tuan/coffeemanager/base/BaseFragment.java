package com.tuan.coffeemanager.base;

import android.content.Context;
import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {

    private AppEventListener appEventListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            appEventListener = (AppEventListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        appEventListener = null;
    }

    public void showLoading() {
        appEventListener.eventShowLoading();
    }

    public void hideLoading() {
        appEventListener.eventHideLoading();
    }
}
