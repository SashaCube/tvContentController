package com.oleksandr.havryliuk.editor.main.configuration;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oleksandr.havryliuk.editor.data.source.PostsRepository;
import com.oleksandr.havryliuk.editor.main.MainPostsContract;
import com.oleksandr.havryliuk.editor.main.MainPostsPresenter;
import com.oleksandr.havryliuk.editor.main.MainPostsView;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

public class ConfigurationFragment extends Fragment {

    private ConfigurationContract.IConfigurationView view;
    private ConfigurationContract.IConfigurationPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tab_item, container, false);

        initView(root);
        initPresenter();

        return root;
    }

    public void initView(View root) {
//        view = new MainPostsView();
//        view.init(this, root);
    }

    public void initPresenter() {
//        presenter = new MainPostsPresenter(view, PostsRepository.getInstance(getContext()));
//        view.setPresenter(presenter);
    }
}
