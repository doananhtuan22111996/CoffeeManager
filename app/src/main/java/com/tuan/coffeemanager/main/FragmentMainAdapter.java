package com.tuan.coffeemanager.main;

import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.main.fragment.SignInFragment;
import com.tuan.coffeemanager.main.fragment.SignUpFragment;

public class FragmentMainAdapter extends FragmentStatePagerAdapter {

    private final int NUM_TAB = 2;

    FragmentMainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0: {
                return SignInFragment.newInstance();
            }
            case 1: {
                return SignUpFragment.newInstance();
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
        if (position == 0){
            return String.valueOf("Sign In");
        }else {
            return String.valueOf("Sign Up");
        }
    }
}
