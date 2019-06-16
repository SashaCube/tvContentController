package com.oleksandr.havryliuk.tvcontentcontroller.editor.main.active_posts;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsRepository;

import java.util.Objects;

public class ActivePostsFragment extends Fragment {

    private ActivePostsContract.IActivePostsView view;
    private ActivePostsContract.IActivePostPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tab_item, container, false);

        initView(root);
        initPresenter();

        return root;
    }

    public void initView(View root) {
        view = new ActivePostsView();
        view.init(root, this);
    }

    public void initPresenter() {
        presenter = new ActivePostsPresenter(view,
                PostsRepository.getInstance(Objects.requireNonNull(getContext())));
        view.setPresenter(presenter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(presenter != null){
            presenter.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(presenter != null){
            presenter.stop();
        }
    }
}
