package com.oleksandr.havryliuk.tvcontentcontroller.editor.main.active_posts;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.MainActivity;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.adapters.PostsAdapter;

import java.util.List;
import java.util.Objects;

public class ActivePostsView implements ActivePostsContract.IActivePostsView {

    private ActivePostsContract.IActivePostPresenter mPresenter;
    private Fragment fragment;
    private PostsAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private View root, mPostsView, mNoPostsView;
    private TextView mNoPostsMainView;

    public void init(View root, Fragment fragment) {
        this.fragment = fragment;
        this.root = root;

        mRecyclerView = root.findViewById(R.id.recycler_view);
        mNoPostsView = root.findViewById(R.id.no_posts_layout);
        mPostsView = root.findViewById(R.id.all_posts_layout);
        mNoPostsMainView = root.findViewById(R.id.no_posts_main);

        initRecyclerView();
    }

    public void setPresenter(ActivePostsContract.IActivePostPresenter presenter) {
        mPresenter = presenter;
        mAdapter = new PostsAdapter(root.getContext());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setPresenter(mPresenter);
    }

    public void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
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

        mNoPostsMainView.setText(R.string.no_active_posts);
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


    private void showMessage(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show();
    }
}
