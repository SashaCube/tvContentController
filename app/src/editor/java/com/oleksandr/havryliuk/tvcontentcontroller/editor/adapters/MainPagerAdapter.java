package com.oleksandr.havryliuk.tvcontentcontroller.editor.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.oleksandr.havryliuk.tvcontentcontroller.editor.main.active_posts.ActivePostsFragment;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.main.configuration.ConfigurationFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private static int NUM_ITEMS = 2;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new ActivePostsFragment();
            case 1:
                return new ConfigurationFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}