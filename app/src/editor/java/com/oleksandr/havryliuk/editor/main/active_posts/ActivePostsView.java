package com.oleksandr.havryliuk.editor.main.active_posts;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.oleksandr.havryliuk.editor.MainActivity;
import com.oleksandr.havryliuk.editor.adapters.PostsAdapter;
import com.oleksandr.havryliuk.editor.all_posts.ScrollChildSwipeRefreshLayout;
import com.oleksandr.havryliuk.editor.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

import java.util.List;
import java.util.Objects;

public class ActivePostsView implements ActivePostsContract.IActivePostsView {

    private ActivePostsContract.IActivePostPresenter mPresenter;
    private Fragment fragment;
    private PostsAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private View root, mPostsView, mNoPostsView;
    private TextView mNoPostsMainView;
    private ScrollChildSwipeRefreshLayout mSwipeRefreshLayout;

    public void init(View root, Fragment fragment) {
        this.fragment = fragment;
        this.root = root;

        mRecyclerView = root.findViewById(R.id.recycler_view);
        mNoPostsView = root.findViewById(R.id.no_posts_layout);
        mPostsView = root.findViewById(R.id.all_posts_layout);
        mNoPostsMainView = root.findViewById(R.id.no_posts_main);
        mSwipeRefreshLayout = root.findViewById(R.id.refresh_layout);

        initRecyclerView();
        initScrollRefreshLayout();
    }

    public void setPresenter(ActivePostsContract.IActivePostPresenter presenter) {
        mPresenter = presenter;
        mAdapter = new PostsAdapter(root.getContext());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setPresenter(mPresenter);
        mPresenter.loadPosts(true);

    }

    public void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
    }

    public void initScrollRefreshLayout() {
        // Set up progress indicator
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(root.getContext(), R.color.independence),
                ContextCompat.getColor(root.getContext(), R.color.slate_gray),
                ContextCompat.getColor(root.getContext(), R.color.dark_purple)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        mSwipeRefreshLayout.setScrollUpChild(mRecyclerView);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadPosts(true);
            }
        });

    }

    @Override
    public void setPosts(List<Post> posts) {

        if (mAdapter != null) {
            mAdapter.setPosts(posts);
        }

        mPostsView.setVisibility(View.VISIBLE);
        mNoPostsView.setVisibility(View.GONE);
    }

    @Override
    public void showNoPosts() {
        mPostsView.setVisibility(View.GONE);
        mNoPostsView.setVisibility(View.VISIBLE);

        mNoPostsMainView.setText(R.string.no_posts_ad);
    }

    @Override
    public void showLoadingTasksError() {
        showMessage(root.getContext().getString(R.string.loading_posts_error));
    }

    @Override
    public void showPostDeleted() {
        showMessage(root.getContext().getString(R.string.deleted_successfully));
    }

    @Override
    public void showEditScreen(Post post) {
        ((MainActivity) Objects.requireNonNull(fragment.getActivity())).openEditPostFragment(post);
    }

    @Override
    public boolean isActive() {
        return fragment.isAdded();
    }

    @Override
    public void setLoadingIndicator(final boolean active) {

        if (root == null) {
            return;
        }
        // Make sure setRefreshing() is called after the layout is done with everything else.
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(active);
            }
        });
    }

    private void showMessage(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show();
    }
}
