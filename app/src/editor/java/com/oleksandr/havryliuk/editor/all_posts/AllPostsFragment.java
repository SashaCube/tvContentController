package com.oleksandr.havryliuk.editor.all_posts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oleksandr.havryliuk.editor.adapters.PostsPagerAdapter;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

import java.util.Objects;

import static com.oleksandr.havryliuk.editor.all_posts.AllPostsPresenter.DATE;
import static com.oleksandr.havryliuk.editor.all_posts.AllPostsPresenter.TITLE;

public class AllPostsFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView sortButton;
    private PopupMenu popupMenu;
    private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_all_posts, container, false);

        initView(root);

        return root;
    }

    private void initView(final View root) {
        sortButton = root.findViewById(R.id.sort_icon);
        viewPager = root.findViewById(R.id.view_pager);
        tabLayout = root.findViewById(R.id.tab_layout);

        initSortMenu();
        initViewPager();
        initTabLayout();
    }

    private void initSortMenu() {
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });

        popupMenu = new PopupMenu(Objects.requireNonNull(getContext()), sortButton);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.sort_by_date:
                        AllPostsPresenter.setSorting(DATE);
                        break;
                    case R.id.sort_by_title:
                        AllPostsPresenter.setSorting(TITLE);
                        break;
                }
                return true;
            }
        });

        popupMenu.inflate(R.menu.sorter_posts);
    }

    private void initViewPager() {
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        PostsPagerAdapter adapterViewPager = new PostsPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        viewPager.setCurrentItem(0);
    }

    private void initTabLayout() {
        tabLayout.addTab(tabLayout.newTab().setText(root.getContext().getString(R.string.tab_all)));
        tabLayout.addTab(tabLayout.newTab().setText(root.getContext().getString(R.string.tab_news)));
        tabLayout.addTab(tabLayout.newTab().setText(root.getContext().getString(R.string.tab_ad)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(new TabListener());
    }

    class TabListener implements TabLayout.OnTabSelectedListener {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }
}
