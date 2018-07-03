package com.tuan.coffeemanager.feature.coffee;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tuan.coffeemanager.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoffeeActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);
        ButterKnife.bind(this);

        initViewPager();

    }

    private void initViewPager() {
        viewPager.setAdapter(new CoffeeViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setCustomView(R.layout.tab_coffee);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setCustomView(R.layout.tab_table);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setCustomView(R.layout.tab_more);
        tabLayout.setSelectedTabIndicatorColor(getColor(R.color.brown));
    }
}
