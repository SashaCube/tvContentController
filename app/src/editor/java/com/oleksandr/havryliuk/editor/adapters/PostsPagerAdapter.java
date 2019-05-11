package com.oleksandr.havryliuk.editor.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.oleksandr.havryliuk.editor.all_posts.view.ADTypeFragment;
import com.oleksandr.havryliuk.editor.all_posts.view.AllTypeFragment;
import com.oleksandr.havryliuk.editor.all_posts.view.NewsTypeFragment;

public class PostsPagerAdapter extends FragmentStatePagerAdapter {

    private static int NUM_ITEMS = 3;

    public PostsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new AllTypeFragment();
            case 1:
                return new NewsTypeFragment();
            case 2:
                return new ADTypeFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
