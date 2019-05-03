package com.oleksandr.havryliuk.editor.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.oleksandr.havryliuk.editor.all_posts.AllPostsContract;
import com.oleksandr.havryliuk.editor.all_posts.AllPostsItemFragment;

import java.util.ArrayList;
import java.util.List;

import static com.oleksandr.havryliuk.editor.data.Post.AD;
import static com.oleksandr.havryliuk.editor.data.Post.ALL;
import static com.oleksandr.havryliuk.editor.data.Post.NEWS;

public class PostsPagerAdapter extends FragmentStatePagerAdapter {

    private static int NUM_ITEMS = 3;

    private List<AllPostsItemFragment> fragments;

    public PostsPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments = new ArrayList<>();
        fragments.add(new AllPostsItemFragment());
        fragments.add(new AllPostsItemFragment());
        fragments.add(new AllPostsItemFragment());
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return fragments.get(0).initType(ALL);
            case 1:
                return fragments.get(1).initType(NEWS);
            case 2:
                return fragments.get(2).initType(AD);
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    public void setPresenter(AllPostsContract.IAllPostsPresenter presenter){
        for (AllPostsContract.IAllPostsView f : fragments) {
           f.setPresenter(presenter);
        }
    }
}
