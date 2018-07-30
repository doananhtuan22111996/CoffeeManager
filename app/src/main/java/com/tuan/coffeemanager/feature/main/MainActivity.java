package com.tuan.coffeemanager.feature.main;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.tuan.coffeemanager.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViewPager();
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initViewPager() {
        viewPager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tabLayout.setTabTextColors(getColor(R.color.hint), getColor(R.color.black));
            tabLayout.setSelectedTabIndicatorColor(getColor(R.color.black));
        }else {
            tabLayout.setTabTextColors(getResources().getColor(R.color.hint), getColor(R.color.black));
            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.black));
        }
    }

}
