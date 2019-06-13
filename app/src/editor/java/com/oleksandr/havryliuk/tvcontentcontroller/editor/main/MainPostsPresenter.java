package com.oleksandr.havryliuk.tvcontentcontroller.editor.main;


import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsDataSource;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsRepository;

import java.util.List;
import java.util.Map;

public class MainPostsPresenter implements MainPostsContract.IMainFragmentPresenter {

    private MainPostsContract.IMainFragmentView view;
    private PostsRepository repository;

    public MainPostsPresenter(MainPostsContract.IMainFragmentView view, PostsRepository repository) {
        this.view = view;
        this.repository = repository;

        repository.getPosts(new PostsDataSource.LoadPostsCallback() {
            @Override
            public void onPostsLoaded(List<Post> posts) {

            }

            @Override
            public void onDataNotAvailable() {

            }
        });

        repository.getConf(new PostsDataSource.LoadConfCallback() {
            @Override
            public void onConfigLoaded(Map<String, Object> configurations) {

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }
}
