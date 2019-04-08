package com.oleksandr.havryliuk.editor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.oleksandr.havryliuk.editor.all_posts.AllPostsFragment;
import com.oleksandr.havryliuk.editor.main.MainFragment;
import com.oleksandr.havryliuk.editor.new_post.NewPostFragment;
import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.utils.ActivityUtils;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_main:
                                openMainFragment();
                                break;
                            case R.id.action_new_post:
                                openNewPostFragment();
                                break;
                            case R.id.action_all_posts:
                                openAllPostsFragment();
                                break;
                        }
                        return true;
                    }
                });
    }

    public void openMainFragment() {
        if (ActivityUtils.isFragmentInBackstack(getSupportFragmentManager(),
                MainFragment.class.getName())) {
            getSupportFragmentManager().popBackStackImmediate(MainFragment.class.getName(), 0);
        } else {
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    new MainFragment(), R.id.fragment);
        }
    }

    public void openAllPostsFragment() {
        if (ActivityUtils.isFragmentInBackstack(getSupportFragmentManager(),
                AllPostsFragment.class.getName())) {
            getSupportFragmentManager().popBackStackImmediate(AllPostsFragment.class.getName(), 0);
        } else {
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    new AllPostsFragment(), R.id.fragment);
        }
    }

    public void openNewPostFragment() {
        if (ActivityUtils.isFragmentInBackstack(getSupportFragmentManager(),
                NewPostFragment.class.getName())) {
            getSupportFragmentManager().popBackStackImmediate(NewPostFragment.class.getName(), 0);
        } else {
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    new NewPostFragment(), R.id.fragment);
        }
    }
}
