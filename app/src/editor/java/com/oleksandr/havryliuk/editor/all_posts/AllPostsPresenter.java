package com.oleksandr.havryliuk.editor.all_posts;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.oleksandr.havryliuk.editor.MainActivity;
import com.oleksandr.havryliuk.editor.data.Post;
import com.oleksandr.havryliuk.editor.data.source.PostsDataSource;
import com.oleksandr.havryliuk.editor.data.source.PostsRepository;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.oleksandr.havryliuk.editor.data.Post.AD;
import static com.oleksandr.havryliuk.editor.data.Post.ALL;
import static com.oleksandr.havryliuk.editor.data.Post.NEWS;

public class AllPostsPresenter implements AllPostsContract.IAllPostsPresenter {

    public final static String TITLE = "Title";
    public final static String DATE = "Date";

    private AllPostsContract.IAllPostsView view;
    private Fragment fragment;
    private static String mSortedPostsBy = TITLE;
    private boolean mFirstLoad = false;
    private PostsRepository mRepository;

    public AllPostsPresenter(AllPostsContract.IAllPostsView view, Fragment fragment) {
        this.view = view;
        this.fragment = fragment;
        mRepository = PostsRepository.getInstance(Objects.requireNonNull(fragment.getContext()));

        start();
    }

    @Override
    public void loadPosts(boolean forceUpdate, final boolean showLoadingUI) {

        if (showLoadingUI) {
            view.setLoadingIndicator(true);
        }
        if (forceUpdate) {
            mRepository.refreshPosts();
            // FIXME: 04.05.19 what must do this method
        }

        mRepository.getPosts(new PostsDataSource.LoadPostsCallback() {
            @Override
            public void onPostsLoaded(List<Post> posts) {
                processPosts(posts);

                if (!fragment.isAdded()) {
                    return;
                }

                if (showLoadingUI) {
                    view.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!fragment.isAdded()) {
                    return;
                }
                view.showLoadingTasksError();
            }
        });
    }

    @Override
    public void clickDelete(final Post post) {
        mRepository.deletePost(post.getId());
        Toast.makeText(fragment.getContext(), post.getTitle() + " deleted successfully", Toast.LENGTH_SHORT).show();
        loadPosts(false, true);
    }

    @Override
    public void clickEdit(Post post) {
        ((MainActivity) Objects.requireNonNull(fragment.getActivity())).openEditPostFragment(post);
    }

    @Override
    public void clickSetPost(final Post post) {
        mRepository.savePost(post);
    }

    @Override
    public void setSorting(String type) {
        mSortedPostsBy = type;
        loadPosts(true, true);
    }


    private List<Post> sortPosts(List<Post> posts) {

        switch (mSortedPostsBy) {
            case TITLE:
                Collections.sort(posts, new Comparator<Post>() {
                    @Override
                    public int compare(Post o1, Post o2) {
                        return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase());
                    }
                });
                break;
            case DATE:
                Collections.sort(posts, new Comparator<Post>() {
                    @Override
                    public int compare(Post o1, Post o2) {
                        return new Date(o1.getCreateDate()).compareTo(new Date(o2.getCreateDate()));
                    }
                });
                break;
        }
        return posts;
    }

    @Override
    public void start() {
        loadPosts(false);
    }

    @Override
    public void loadPosts(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        loadPosts(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void processPosts(List<Post> posts) {
        posts = filterPosts(posts);

        if (posts.isEmpty()) {
            switch (view.getType()) {
                case ALL:
                    view.showNoPosts(R.string.no_posts_all);
                    break;
                case NEWS:
                    view.showNoPosts(R.string.no_posts_news);
                    break;
                case AD:
                    view.showNoPosts(R.string.no_posts_ad);
                    break;
            }
        } else {
            posts = sortPosts(posts);
            view.setPosts(posts);
        }
    }

    private List<Post> filterPosts(List<Post> posts) {
        if (view.getType().equals(ALL)) {
            return posts;
        } else {
            List<Post> filteredPosts = new ArrayList<>();
            for (Post p : posts) {
                if (p.getType().equals(view.getType())) {
                    filteredPosts.add(p);
                }
            }
            return filteredPosts;
        }
    }
}
