package com.oleksandr.havryliuk.editor.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oleksandr.havryliuk.editor.repository.Repository;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

public class MainPostsFragment extends Fragment {

    private ContractMainPosts.IMainFragmentView view;
    private ContractMainPosts.IMainFragmentPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        initView(root);
        initPresenter();

        return root;
    }

    public void initView(View root) {
        view = new MainPostsView();
        view.init(this, root);
    }

    public void initPresenter(){
        presenter = new MainPostsPresenter(view, Repository.getInstance());
        view.setPresenter(presenter);
    }
}
