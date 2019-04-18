package com.oleksandr.havryliuk.editor.all_posts;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.oleksandr.havryliuk.editor.adapters.PostsPagerAdapter;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

import static com.oleksandr.havryliuk.editor.model.Post.AD;
import static com.oleksandr.havryliuk.editor.model.Post.ALL;
import static com.oleksandr.havryliuk.editor.model.Post.NEWS;

public class AllPostsView implements AllPostsContract.IAllPostsView {

    private AllPostsContract.IAllPostsPresenter presenter;
    private FragmentStatePagerAdapter adapterViewPager;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    public void init(View root) {

        viewPager = root.findViewById(R.id.view_pager);
        tabLayout = root.findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText(ALL));
        tabLayout.addTab(tabLayout.newTab().setText(NEWS));
        tabLayout.addTab(tabLayout.newTab().setText(AD));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getText().toString()) {
                    case ALL:
                        viewPager.setCurrentItem(0);
                        break;
                    case NEWS:
                        viewPager.setCurrentItem(1);
                        break;
                    case AD:
                        viewPager.setCurrentItem(2);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void setPresenter(AllPostsContract.IAllPostsPresenter presenter, FragmentManager fragmentManager) {
        this.presenter = presenter;
        adapterViewPager = new PostsPagerAdapter(fragmentManager, presenter);
        viewPager.setAdapter(adapterViewPager);
    }


}
