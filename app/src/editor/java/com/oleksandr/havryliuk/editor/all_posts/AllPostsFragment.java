package com.oleksandr.havryliuk.editor.all_posts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oleksandr.havryliuk.editor.all_posts.fragment.AllPostsAllFragment;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

public class AllPostsFragment extends Fragment{

    private AllPostsContract.IAllPostsView view;
    private AllPostsContract.IAllPostsPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_all_posts, container, false);

        initView(root);
        initPresenter();

        return root;
    }

    private void initView(View root){
        view = new AllPostsView();
        view.init(root, getFragmentManager());
    }

    private void initPresenter(){
        presenter = new AllPostsPresenter(view, this);
        view.setPresenter(presenter);
    }
}
