package com.tuan.coffeemanager.feature.coffee;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.tuan.coffeemanager.R;
import com.tuan.coffeemanager.contact.ContactBaseApp;
import com.tuan.coffeemanager.feature.addcoffee.AddCoffeeActivity;
import com.tuan.coffeemanager.feature.main.MainActivity;
import com.tuan.coffeemanager.widget.CustomKeyBoard;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoffeeActivity extends AppCompatActivity {

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

        if (getIntent().getExtras() != null){
            int page = getIntent().getExtras().getInt("FLAG", 0);
            changePage(page);
        }

        ivAddCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoffeeActivity.this, AddCoffeeActivity.class);
                intent.putExtra(ContactBaseApp.DRINK_ID, "");
                startActivity(intent);
            }
        });
    }

    private void changePage(int page){
        viewPager.setCurrentItem(page);
    }

    private void initViewPager() {
        ivAddCoffee.setVisibility(View.GONE);
        viewPager.setAdapter(new CoffeeViewPagerAdapter(getSupportFragmentManager()));
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

}
