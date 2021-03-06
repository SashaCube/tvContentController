package com.oleksandr.havryliuk.tvcontentcontroller.editor.main;

import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.MainActivity;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.adapters.MainPagerAdapter;

import java.util.Objects;

public class MainPostsView implements MainPostsContract.IMainFragmentView, View.OnClickListener {

    private Fragment fragment;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MainPostsContract.IMainFragmentPresenter presenter;

    @Override
    public void init(Fragment fragment, View root) {
        this.fragment = fragment;

        ImageView settingsImage = root.findViewById(R.id.settings_image_view);
        settingsImage.setOnClickListener(this);

        viewPager = root.findViewById(R.id.view_pager_main);
        tabLayout = root.findViewById(R.id.tab_layout);

        initViewPager();
        initTabLayout();
    }

    private void initViewPager() {
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        MainPagerAdapter adapterViewPager = new MainPagerAdapter(fragment.getFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        viewPager.setCurrentItem(0);
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(fragment.getString(R.string.active_posts)));
        tabLayout.addTab(tabLayout.newTab().setText(fragment.getString(R.string.configuration)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(new TabListener());
    }

    class TabListener implements TabLayout.OnTabSelectedListener {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
            viewPager.getAdapter().notifyDataSetChanged();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            viewPager.getAdapter().notifyDataSetChanged();
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            viewPager.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void setPresenter(MainPostsContract.IMainFragmentPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_image_view:
                Objects.requireNonNull((MainActivity) fragment.getActivity()).
                        openSettingsFragment();
                break;
        }
    }
}
