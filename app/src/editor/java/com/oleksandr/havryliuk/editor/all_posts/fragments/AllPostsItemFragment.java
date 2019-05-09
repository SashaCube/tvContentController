package com.oleksandr.havryliuk.editor.all_posts.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oleksandr.havryliuk.editor.all_posts.AllPostsContract;
import com.oleksandr.havryliuk.editor.all_posts.AllPostsItemView;
import com.oleksandr.havryliuk.editor.all_posts.AllPostsPresenter;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

import static com.oleksandr.havryliuk.editor.all_posts.AllPostsPresenter.DATE;
import static com.oleksandr.havryliuk.editor.all_posts.AllPostsPresenter.TITLE;

public class AllPostsItemFragment extends Fragment {

    private AllPostsContract.IAllPostsPresenter presenter;
    private AllPostsContract.IAllPostsView view;

    private View root;
    private String type;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_all_posts_item, container, false);

        initView(root);
        initPresenter();

        return root;
    }

    public AllPostsItemFragment initType(String type) {
        this.type = type;
        return this;
    }

    public void initView(View root) {
        view = new AllPostsItemView();
        view.init(root, type);
    }

    public void initPresenter() {
        presenter = new AllPostsPresenter(view, this);
        view.setPresenter(presenter);
        presenter.loadPosts(true);
    }

    public void setSortingByDate() {
        presenter.setSorting(DATE);
    }


    public void setSortingByTitle() {
        presenter.setSorting(TITLE); // FIXME: 06.05.19 invoke on null???
    }
}
