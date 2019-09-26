package com.oleksandr.havryliuk.tvcontentcontroller.editor.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.oleksandr.havryliuk.tvcontentcontroller.editor.all_posts.view.ADTypeFragment;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.all_posts.view.AllTypeFragment;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.all_posts.view.NewsTypeFragment;

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

    @Override
    public int getItemPosition(Object object) {
        // POSITION_NONE makes it possible to reload the PagerAdapter
        return POSITION_NONE;
    }
}
