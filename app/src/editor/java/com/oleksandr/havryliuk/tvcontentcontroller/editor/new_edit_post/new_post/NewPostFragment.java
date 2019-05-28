package com.oleksandr.havryliuk.tvcontentcontroller.editor.new_edit_post.new_post;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsRepository;

import java.util.Objects;

public class NewPostFragment extends Fragment {

    private NewPostContract.INewPostPresenter presenter;
    private NewPostContract.INewPostView view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_post, container, false);

        initView(root);
        initPresenter();

        return root;
    }

    public void initView(View root) {
        view = new NewPostView();
        view.init(this, root);
    }

    public void initPresenter() {
        presenter = new NewPostPresenter(view, PostsRepository.getInstance(Objects.requireNonNull(getContext())));
        view.setPresenter(presenter);
    }
}
