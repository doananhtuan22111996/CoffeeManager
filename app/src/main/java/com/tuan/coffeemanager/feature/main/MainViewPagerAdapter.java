package com.tuan.coffeemanager.feature.main;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tuan.coffeemanager.feature.main.fragment.SignInFragment;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    private final int NUM_TAB = 1;

    MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0: {
                return SignInFragment.newInstance();
            }
            default: {
                return SignInFragment.newInstance();
            }
        }
    }

    @Override
    public int getCount() {
        return NUM_TAB;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf("Sign In");
    }
}
