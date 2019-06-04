package com.oleksandr.havryliuk.tvcontentcontroller.client.bottom_bar;

import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsDataSource;

public class BottomBarPresenter implements BottomBarContract.IBottomBarPresenter {

    private final static String TAG = BottomBarPresenter.class.getName();

    private BottomBarContract.IBottomBarView view;
    private PostsDataSource repository;

    public BottomBarPresenter(BottomBarContract.IBottomBarView view, PostsDataSource repository) {
        this.view = view;
        this.repository = repository;
    }

    public void startDisplayInfo() {
        view.startDisplayDateTime();
    }

    @Override
    public void stopDisplayInfo() {
        view.stopDisplayDateTime();
    }
}
