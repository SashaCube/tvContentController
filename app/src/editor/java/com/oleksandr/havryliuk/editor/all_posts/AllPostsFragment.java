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

import static com.oleksandr.havryliuk.editor.data.Post.AD;
import static com.oleksandr.havryliuk.editor.data.Post.ALL;
import static com.oleksandr.havryliuk.editor.data.Post.NEWS;

public class AllPostsFragment extends Fragment {

    public final static String TITLE = "Title";
    public final static String DATE = "Date";

    private AllPostsContract.IAllPostsPresenter presenter;

    private PostsPagerAdapter adapterViewPager;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView sortButton;
    private PopupMenu popupMenu;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_all_posts, container, false);

        initView(root);
        initPresenter();

        return root;
    }


    private void initView(final View root) {

        sortButton = root.findViewById(R.id.sort_icon);
        viewPager = root.findViewById(R.id.view_pager);
        tabLayout = root.findViewById(R.id.tab_layout);

        initSortMenu();
        initViewPager();
    }

    private void initSortMenu(){
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
                        presenter.setSorting(DATE);
                        break;
                    case R.id.sort_by_title:
                        presenter.setSorting(TITLE);
                        break;
                }
                return true;
            }
        });

        popupMenu.inflate(R.menu.sorter_posts);
    }

    private void initViewPager(){
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        adapterViewPager = new PostsPagerAdapter(getFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        viewPager.setCurrentItem(0);

        tabLayout.addTab(tabLayout.newTab().setText(ALL));
        tabLayout.addTab(tabLayout.newTab().setText(NEWS));
        tabLayout.addTab(tabLayout.newTab().setText(AD));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(new TabListener());
    }

    private void initPresenter() {

        presenter = new AllPostsPresenter(this);
        adapterViewPager.setPresenter(presenter);

        showAllView();
    }

    class TabListener implements TabLayout.OnTabSelectedListener {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (Objects.requireNonNull(tab.getText()).toString()) {
                case ALL:
                    showAllView();
                    break;
                case NEWS:
                    showNewsView();
                    break;
                case AD:
                    showADView();
                    break;
            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }

    private void showAllView(){
        viewPager.setCurrentItem(0);
        presenter.setView((AllPostsContract.IAllPostsView) adapterViewPager.getItem(0));
    }

    private void showNewsView(){
        viewPager.setCurrentItem(1);
        presenter.setView((AllPostsContract.IAllPostsView) adapterViewPager.getItem(1));
    }

    private void showADView(){
        viewPager.setCurrentItem(2);
        presenter.setView((AllPostsContract.IAllPostsView) adapterViewPager.getItem(2));
    }
}
