package com.doozycod.axs.UpdateDebug.SlidingActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.doozycod.axs.Activity.ScreenSlidePagerActivity;
import com.doozycod.axs.BackgroudService.Workers.SyncRemoteServerService;
import com.doozycod.axs.Database.Repository.TaskInfoRepository;
import com.doozycod.axs.POJO.LoginResponse;
import com.doozycod.axs.R;
import com.doozycod.axs.UpdateDebug.SlidingActivity.Adapter.TabLayoutPagerAdapter;
import com.doozycod.axs.Utils.Constants;
import com.doozycod.axs.Utils.CustomViewPager;
import com.doozycod.axs.ui.NavigationViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewSliderPagerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationViewModel navigationViewModel;
    private DrawerLayout drawerLayout;
    TabLayoutPagerAdapter pagerAdapter;
    TabLayout tabLayout;
    CustomViewPager viewPager;

    private TextView driverNameNavText;
    private TextView driverCompanyNavText;
    TaskInfoRepository mTaskInfoRepository;
    private Calendar mCalender;
    private int year, month, date;
    private DatePickerDialog.OnDateSetListener mDateListener;
    LoginResponse loginResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_slider_pager);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationViewModel = new ViewModelProvider(this).get(NavigationViewModel.class);

        View headerView = navigationView.getHeaderView(0);
        driverNameNavText = headerView.findViewById(R.id.driver_name_nav_text);
        driverCompanyNavText = headerView.findViewById(R.id.driver_company_nav_text);

        pagerAdapter = new TabLayoutPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tab_layout_main);
        viewPager.setSwipeable(false);
        mTaskInfoRepository = new TaskInfoRepository((Application) getApplicationContext());
//        mTaskInfoRepository.deleteAll();
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(pagerAdapter);

        init();

    }

    private void init() {

        mCalender = Calendar.getInstance();
        year = mCalender.get(Calendar.YEAR);
        month = mCalender.get(Calendar.MONTH);
        date = mCalender.get(Calendar.DATE);
        mDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                stopService(new Intent(NewSliderPagerActivity.this, SyncRemoteServerService.class));
                String selectedDate = new SimpleDateFormat("yyyy-MM-dd").format(getDateFromDatePicker(datePicker));
                PreferenceManager.getDefaultSharedPreferences(NewSliderPagerActivity.this).edit()
                        .putString(Constants.PREF_KEY_SELECTED_DATE, selectedDate)
                        .apply();
                // Log.d(TAG, selectedDate);
                startService(new Intent(NewSliderPagerActivity.this, SyncRemoteServerService.class));
            }
        };
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}