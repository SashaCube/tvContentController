package com.oleksandr.havryliuk.editor.all_posts;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.oleksandr.havryliuk.editor.all_posts.fragment.AllPostsItemFragment;

import static com.oleksandr.havryliuk.editor.model.Post.AD;
import static com.oleksandr.havryliuk.editor.model.Post.ALL;
import static com.oleksandr.havryliuk.editor.model.Post.NEWS;

public class PostsPagerAdapter extends FragmentStatePagerAdapter {
    private static int NUM_ITEMS = 3;
    private AllPostsContract.IAllPostsPresenter presenter;

    public PostsPagerAdapter(FragmentManager fm, AllPostsContract.IAllPostsPresenter presenter) {
        super(fm);
        this.presenter = presenter;
    }

    @Override
    public Fragment getItem(int i) {
        AllPostsItemFragment fragment = new AllPostsItemFragment();
        switch (i) {
            case 0:
                fragment.init(ALL, presenter);
                break;
            case 1:
                fragment.init(NEWS, presenter);
                break;
            case 2:
                fragment.init(AD, presenter);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
