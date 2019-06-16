package com.oleksandr.havryliuk.tvcontentcontroller.client.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.client.content.view.ContentView;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.WeatherRepository;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsRepository;

import java.util.Objects;

public class ContentFragment extends Fragment {

    private ContentContract.IContentPresenter presenter;
    private ContentContract.IContentView view;
    private View root;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_content, container, false);

        initView();
        initPresenter();

        return root;
    }

    private void initView() {
        view = new ContentView();
        view.init(this, root);
    }

    private void initPresenter() {
        presenter = new ContentPresenter(view,
                PostsRepository.getInstance(Objects.requireNonNull(getContext())),
                WeatherRepository.getInstance(getContext()));
        view.setPresenter(presenter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.startShowingPosts();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.stopShowingPosts();
        }
    }
}
