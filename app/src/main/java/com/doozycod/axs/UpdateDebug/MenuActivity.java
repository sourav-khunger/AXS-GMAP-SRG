package com.doozycod.axs.UpdateDebug;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.doozycod.axs.Adapter.SwipePagerAdapter;
import com.doozycod.axs.R;
import com.doozycod.axs.UpdateDebug.RouteMarker.RouteFragment;

public class MenuActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    private SwipePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        pagerAdapter = new SwipePagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tab_layout_main);
//        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(pagerAdapter);
        if (getIntent().hasExtra("map")) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameOut, new RouteFragment()).commit();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}