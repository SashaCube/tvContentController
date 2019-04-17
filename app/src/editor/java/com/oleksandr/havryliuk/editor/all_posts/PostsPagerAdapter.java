package com.oleksandr.havryliuk.editor.all_posts;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.oleksandr.havryliuk.editor.all_posts.fragment.AllPostsADFragment;
import com.oleksandr.havryliuk.editor.all_posts.fragment.AllPostsAllFragment;
import com.oleksandr.havryliuk.editor.all_posts.fragment.AllPostsNewsFragment;

public class PostsPagerAdapter extends FragmentStatePagerAdapter {
    private static int NUM_ITEMS = 3;

    public PostsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new AllPostsAllFragment();
            case 1:
                return new AllPostsNewsFragment();
            case 2:
                return new AllPostsADFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
