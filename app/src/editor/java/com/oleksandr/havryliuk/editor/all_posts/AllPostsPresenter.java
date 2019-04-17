package com.oleksandr.havryliuk.editor.all_posts;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.oleksandr.havryliuk.editor.model.Post;
import com.oleksandr.havryliuk.editor.repository.Repository;

import java.util.List;

import static com.oleksandr.havryliuk.editor.model.Post.AD;
import static com.oleksandr.havryliuk.editor.model.Post.ALL;
import static com.oleksandr.havryliuk.editor.model.Post.NEWS;

public class AllPostsPresenter implements AllPostsContract.IAllPostsPresenter {

    private AllPostsContract.IAllPostsView view;
    private Fragment fragment;

    public AllPostsPresenter(AllPostsContract.IAllPostsView view, Fragment fragment) {
        this.view = view;
        this.fragment = fragment;
    }

    @Override
    public void clickEditButton(Post post) {
        Toast.makeText(fragment.getContext(), "Edit " + post.getTitle(), Toast.LENGTH_LONG).show();
    }

    @Override
    public List<Post> getPosts(String type) {
        Repository repository = Repository.getInstance();

        switch(type){
            case ALL:
                return repository.getAllPosts();
            case NEWS:
                return repository.getPostsByType(NEWS);
            case AD:
                return repository.getPostsByType(AD);
        }

        return null;
    }
}
