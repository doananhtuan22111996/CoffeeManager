package com.tuan.coffeemanager.feature.coffee;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.tuan.coffeemanager.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoffeeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.ivAddCoffee)
    ImageView ivAddCoffee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);
        ButterKnife.bind(this);

        initViewPager();

    }

    private void initViewPager() {
        viewPager.setAdapter(new CoffeeViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setCustomView(R.layout.tab_coffee);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setCustomView(R.layout.tab_table);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setCustomView(R.layout.tab_more);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tabLayout.setSelectedTabIndicatorColor(getColor(R.color.brown));
        } else {
            tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.brown));
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        switch (i) {
            case 0: {
                ivAddCoffee.setVisibility(View.VISIBLE);
            }
            case 1: {
                ivAddCoffee.setVisibility(View.GONE);
            }
            case 2: {
                ivAddCoffee.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
