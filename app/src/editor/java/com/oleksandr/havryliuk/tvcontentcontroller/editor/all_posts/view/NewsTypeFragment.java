package com.oleksandr.havryliuk.tvcontentcontroller.editor.all_posts.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsRepository;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.MainActivity;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.adapters.PostsAdapter;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.all_posts.AllPostsContract;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.all_posts.AllPostsPresenter;

import java.util.List;
import java.util.Objects;

import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.NEWS;

public class NewsTypeFragment extends Fragment implements AllPostsContract.IAllPostsView {

    private final String TYPE = NEWS;
    private AllPostsContract.IAllPostsPresenter mPresenter;
    private PostsAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private View root, mPostsView, mNoPostsView;
    private TextView mNoPostsMainView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_tab_item, container, false);

        initView();
        initPresenter();

        return root;
    }

    public void initView() {
        mRecyclerView = root.findViewById(R.id.recycler_view);
        mNoPostsView = root.findViewById(R.id.no_posts_layout);
        mPostsView = root.findViewById(R.id.all_posts_layout);
        mNoPostsMainView = root.findViewById(R.id.no_posts_main);

        initRecyclerView();
    }

    public void initPresenter() {
        mPresenter = new AllPostsPresenter(this,
                PostsRepository.getInstance(Objects.requireNonNull(getContext())));
        mAdapter.setPresenter(mPresenter);
    }

    public void initRecyclerView() {
        mAdapter = new PostsAdapter(root.getContext());
        mRecyclerView.setAdapter(mAdapter);
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

        mNoPostsMainView.setText(R.string.no_posts_news);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void showPostDeleted() {
        showMessage(root.getContext().getString(R.string.deleted_successfully));
    }

    @Override
    public void showEditScreen(Post post) {
        ((MainActivity) Objects.requireNonNull(getActivity())).openEditPostFragment(post);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    private void showMessage(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.stop();
    }
}

