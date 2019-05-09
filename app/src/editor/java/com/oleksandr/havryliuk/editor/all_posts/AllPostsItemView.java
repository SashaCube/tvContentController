package com.oleksandr.havryliuk.editor.all_posts;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.oleksandr.havryliuk.editor.adapters.PostsAdapter;
import com.oleksandr.havryliuk.editor.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

import java.util.List;

public class AllPostsItemView implements AllPostsContract.IAllPostsView {

    private AllPostsContract.IAllPostsPresenter mPresenter;
    private PostsAdapter mAdapter;
    private String type;
    private RecyclerView mRecyclerView;

    private View root, mPostsView, mNoPostsView;
    private TextView mNoPostsMainView;
    private ScrollChildSwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void init(View root, String type) {
        this.root = root;
        this.type = type;

        mRecyclerView = root.findViewById(R.id.recycler_view);
        mNoPostsView = root.findViewById(R.id.no_posts_layout);
        mPostsView = root.findViewById(R.id.all_posts_layout);
        mNoPostsMainView = root.findViewById(R.id.no_posts_main);
        swipeRefreshLayout = root.findViewById(R.id.refresh_layout);

        initRecyclerView();
        initScrollRefreshLayout();
    }

    public void initRecyclerView() {
        mAdapter = new PostsAdapter(root.getContext());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
    }

    public void initScrollRefreshLayout() {
        // Set up progress indicator
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(root.getContext(), R.color.independence),
                ContextCompat.getColor(root.getContext(), R.color.slate_gray),
                ContextCompat.getColor(root.getContext(), R.color.dark_purple)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(mRecyclerView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadPosts(true);
            }
        });

    }

    @Override
    public void setPresenter(AllPostsContract.IAllPostsPresenter mPresenter) {
        this.mPresenter = mPresenter;
        mAdapter.setPresenter(mPresenter);
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
    public void showNoPosts(int string) {
        mPostsView.setVisibility(View.GONE);
        mNoPostsView.setVisibility(View.VISIBLE);

        mNoPostsMainView.setText(string);
    }

    @Override
    public String getType() {
        return type;
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
    public void setLoadingIndicator(final boolean active) {

        if (root == null) {
            return;
        }
        // Make sure setRefreshing() is called after the layout is done with everything else.
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(active);
            }
        });
    }

    private void showMessage(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show();
    }
}
