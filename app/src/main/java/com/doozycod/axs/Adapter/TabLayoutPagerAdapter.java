package com.doozycod.axs.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.doozycod.axs.Fragments.MapsFragment;
import com.doozycod.axs.Fragments.SummaryFragment;
import com.doozycod.axs.Fragments.ToDoTaskListFragment;

public class TabLayoutPagerAdapter extends FragmentStatePagerAdapter {

    public TabLayoutPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position){
            case 0:
                fragment = new ToDoTaskListFragment();
                break;
            case 1:
                fragment = new MapsFragment();
                break;
            case 2:
                fragment = new SummaryFragment();
                break;
            default:
                fragment = new ToDoTaskListFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title;
        switch (position){
            case 0:
                title = ToDoTaskListFragment.TO_DO_TASK_LIST_FRAGMENT_TITLE;
                break;
            case 1:
                title = MapsFragment.MAP_FRAGMENT_TITLE;
                break;
            case 2:
                title = SummaryFragment.SUMMARY_FRAGMENT_TITLE;
                break;
            default:
                title = ToDoTaskListFragment.TO_DO_TASK_LIST_FRAGMENT_TITLE;
        }
        return title;
    }
}
