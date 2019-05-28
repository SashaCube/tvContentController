package com.oleksandr.havryliuk.editor.main;

import com.oleksandr.havryliuk.editor.data.source.PostsRepository;

public class MainPostsPresenter implements MainPostsContract.IMainFragmentPresenter {

    private MainPostsContract.IMainFragmentView view;
    private PostsRepository repository;

    public MainPostsPresenter(MainPostsContract.IMainFragmentView view, PostsRepository repository) {
        this.view = view;
        this.repository = repository;
    }
}