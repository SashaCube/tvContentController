package com.oleksandr.havryliuk.editor.main;

import com.oleksandr.havryliuk.editor.repository.Repository;

public class MainPostsPresenter implements ContractMainPosts.IMainFragmentPresenter {

    private ContractMainPosts.IMainFragmentView view;
    private Repository repository;

    public MainPostsPresenter(ContractMainPosts.IMainFragmentView view, Repository repository){
        this.view = view;
        this.repository = repository;
    }
}
