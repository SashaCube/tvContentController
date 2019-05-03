package com.oleksandr.havryliuk.editor.all_posts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oleksandr.havryliuk.editor.adapters.PostsAdapter;
import com.oleksandr.havryliuk.editor.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

import java.util.List;

public class AllPostsItemFragment extends Fragment implements AllPostsContract.IAllPostsView {

    private AllPostsContract.IAllPostsPresenter presenter;
    private PostsAdapter adapter;
    private String type;
    private RecyclerView recyclerView;

    private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_all_posts_item, container, false);

        initView();

        return root;
    }

    public AllPostsItemFragment initType(String type) {
        this.type = type;
        return this;
    }

    public void initView() {
        recyclerView = root.findViewById(R.id.recycler_view);
        adapter = new PostsAdapter(root.getContext());
        adapter.setPresenter(presenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
    }

    @Override
    public void setPresenter(AllPostsContract.IAllPostsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setPosts(List<Post> posts) {
        if (adapter != null) {
            adapter.setPosts(posts);
        }
    }

    @Override
    public void setLoadingIndicator(boolean value) {
        // TODO: 03.05.19  show indicator 
    }

    @Override
    public void showNoPosts() {
        // TODO: 03.05.19 show message "no posts" 
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void showLoadingTasksError() {
        // TODO: 03.05.19 show message "error"
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadPosts(true, true);
    }
}
