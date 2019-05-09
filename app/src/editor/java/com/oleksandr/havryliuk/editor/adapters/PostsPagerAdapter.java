package com.oleksandr.havryliuk.editor.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.oleksandr.havryliuk.editor.all_posts.fragments.AllPostsItemFragment;

import static com.oleksandr.havryliuk.editor.data.Post.AD;
import static com.oleksandr.havryliuk.editor.data.Post.ALL;
import static com.oleksandr.havryliuk.editor.data.Post.NEWS;

public class PostsPagerAdapter extends FragmentStatePagerAdapter {

    private static int NUM_ITEMS = 3;

    public PostsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return (new AllPostsItemFragment()).initType(ALL);
            case 1:
                return (new AllPostsItemFragment()).initType(NEWS);
            case 2:
                return (new AllPostsItemFragment()).initType(AD);
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    public AllPostsItemFragment getFragment(int item) {
        return (AllPostsItemFragment) getItem(item);
    }
}
