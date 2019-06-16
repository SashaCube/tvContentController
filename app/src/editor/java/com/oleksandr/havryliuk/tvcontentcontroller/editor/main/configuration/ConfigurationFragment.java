package com.oleksandr.havryliuk.tvcontentcontroller.editor.main.configuration;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsRepository;

import java.util.Objects;

public class ConfigurationFragment extends Fragment {

    private ConfigurationContract.IConfigurationView view;
    private ConfigurationContract.IConfigurationPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_configuration, container, false);

        initView(root);
        initPresenter();

        return root;
    }

    public void initView(View root) {
        view = new ConfigurationView();
        view.init(this, root);
    }

    public void initPresenter() {
        presenter = new ConfigurationPresenter(view,
                PostsRepository.getInstance(Objects.requireNonNull(getContext())));
        view.setPresenter(presenter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.stop();
        }
    }
}
