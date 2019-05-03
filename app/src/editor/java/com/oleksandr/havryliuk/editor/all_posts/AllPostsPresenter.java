package com.oleksandr.havryliuk.editor.all_posts;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.oleksandr.havryliuk.editor.MainActivity;
import com.oleksandr.havryliuk.editor.model.Post;
import com.oleksandr.havryliuk.editor.repository.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static com.oleksandr.havryliuk.editor.all_posts.AllPostsFragment.DATE;
import static com.oleksandr.havryliuk.editor.all_posts.AllPostsFragment.TITLE;
import static com.oleksandr.havryliuk.editor.model.Post.AD;
import static com.oleksandr.havryliuk.editor.model.Post.ALL;
import static com.oleksandr.havryliuk.editor.model.Post.NEWS;

public class AllPostsPresenter implements AllPostsContract.IAllPostsPresenter {

    private AllPostsContract.IAllPostsView view;
    private Fragment fragment;
    private static String sortedPostsBy = TITLE;
    private volatile Repository repository;

    AllPostsPresenter(AllPostsContract.IAllPostsView view, Fragment fragment) {
        this.view = view;
        this.fragment = fragment;
        repository = Repository.getInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<Post> getPosts(String type) {

        switch (type) {
            case ALL:
                return sortedPosts(repository.getAllPosts());
            case NEWS:
                return sortedPosts(repository.getPostsByType(NEWS));
            case AD:
                return sortedPosts(repository.getPostsByType(AD));
        }
        return null;
    }

    @Override
    public void clickDelete(final Post post) {
        repository.deletePost(post);
        Toast.makeText(fragment.getContext(), post.getTitle() + " deleted successfully", Toast.LENGTH_SHORT).show();
        view.updatePosts();
    }

    @Override
    public void clickEdit(Post post) {
        ((MainActivity) Objects.requireNonNull(fragment.getActivity())).openEditPostFragment(post);
    }

    @Override
    public void clickSetPost(final Post post) {
        repository.setPost(post);
        view.updatePosts();
    }

    @Override
    public void setSorting(String type) {
        sortedPostsBy = type;
        view.updatePosts();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<Post> sortedPosts(List<Post> posts) {
        switch (sortedPostsBy) {
            case TITLE:
                posts.sort(new Comparator<Post>() {
                    @Override
                    public int compare(Post o1, Post o2) {
                        return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase());
                    }
                });
                break;
            case DATE:
                posts.sort(new Comparator<Post>() {
                    @Override
                    public int compare(Post o1, Post o2) {
                        return o1.getCreateDate().compareTo(o2.getCreateDate());
                    }
                });
                break;
        }
        return posts;
    }
}
