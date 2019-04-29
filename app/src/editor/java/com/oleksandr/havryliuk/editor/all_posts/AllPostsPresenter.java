package com.oleksandr.havryliuk.editor.all_posts;

import android.support.v4.app.Fragment;

import com.oleksandr.havryliuk.editor.model.Post;
import com.oleksandr.havryliuk.editor.repository.Repository;

import java.util.List;

import static com.oleksandr.havryliuk.editor.model.Post.AD;
import static com.oleksandr.havryliuk.editor.model.Post.ALL;
import static com.oleksandr.havryliuk.editor.model.Post.NEWS;

public class AllPostsPresenter implements AllPostsContract.IAllPostsPresenter {

    private AllPostsContract.IAllPostsView view;
    private Fragment fragment;
    private volatile Repository repository;

    public AllPostsPresenter(AllPostsContract.IAllPostsView view, Fragment fragment) {
        this.view = view;
        this.fragment = fragment;
        repository = Repository.getInstance();
    }

    @Override
    public List<Post> getPosts(String type) {

        switch (type) {
            case ALL:
                return repository.getAllPosts();
            case NEWS:
                return repository.getPostsByType(NEWS);
            case AD:
                return repository.getPostsByType(AD);
        }

        return null;
    }

    @Override
    public void clickDelete(final Post post) {
        repository.deletePost(post);
        view.updatePosts();
    }

    @Override
    public void clickEdit(Post post) {
        //TODO: open constructor
        view.updatePosts();
    }

    @Override
    public void clickSetPost(final Post post) {
        repository.setPost(post);
        view.updatePosts();
    }
}
