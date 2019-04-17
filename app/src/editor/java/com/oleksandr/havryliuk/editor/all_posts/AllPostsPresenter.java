package com.oleksandr.havryliuk.editor.all_posts;

import android.support.v4.app.Fragment;

public class AllPostsPresenter implements AllPostsContract.IAllPostsPresenter {

    private AllPostsContract.IAllPostsView view;
    private Fragment fragment;

    public AllPostsPresenter(AllPostsContract.IAllPostsView view, Fragment fragment) {
        this.view = view;
        this.fragment = fragment;
    }
}
