package com.oleksandr.havryliuk.tvcontentcontroller.client.bottom_bar;

import android.util.Log;

import com.oleksandr.havryliuk.tvcontentcontroller.client.data.WeatherRepository;

public class BottomBarPresenter implements BottomBarContract.IBottomBarPresenter {

    private final static String TAG = BottomBarPresenter.class.getName();

    private BottomBarContract.IBottomBarView view;
    private WeatherRepository repository;

    public BottomBarPresenter(BottomBarContract.IBottomBarView view, WeatherRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void startDisplayInfo() {
        Log.i(TAG, "Start showing Bottom Bar");
        view.startDisplayDateTime();
        Log.i(TAG, "Weather forecast -> data not available");
    }

    @Override
    public void stopDisplayInfo() {
        Log.i(TAG, "Stop showing Bottom Bar");
        view.stopDisplayDateTime();
    }
}
