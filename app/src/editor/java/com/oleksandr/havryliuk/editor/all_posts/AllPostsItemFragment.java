package com.oleksandr.havryliuk.editor.all_posts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oleksandr.havryliuk.editor.adapters.PostsAdapter;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

public class AllPostsItemFragment extends Fragment {

    private AllPostsContract.IAllPostsPresenter presenter;
    private PostsAdapter adapter;
    private String type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_all_posts_item, container, false);

        initView(root);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.setPresenter(presenter);
        adapter.setPosts(presenter.getPosts(type));
    }

    private void initView(View root) {

        RecyclerView recyclerView = root.findViewById(R.id.recycler_view);
        adapter = new PostsAdapter(root.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
    }

    public void init(String type, AllPostsContract.IAllPostsPresenter presenter) {
        this.type = type;
        this.presenter = presenter;
    }
}