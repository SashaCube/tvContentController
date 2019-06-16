package com.oleksandr.havryliuk.tvcontentcontroller.client.bottom_bar;

import android.util.Log;

import com.oleksandr.havryliuk.tvcontentcontroller.client.data.WeatherRepository;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeather;

import java.util.List;

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
        view.startDisplayDateTime();
        repository.registerObserver(this);

        Log.i(TAG, "Start showing Bottom Bar");
    }

    @Override
    public void stopDisplayInfo() {
        repository.removeObserver(this);
        view.stopDisplayDateTime();

        Log.i(TAG, "Stop showing Bottom Bar");
    }

    @Override
    public void onWeatherDataChanged(List<MyWeather> weatherList) {
        view.setWeather(weatherList);

        Log.i(TAG, "Update Weather");
    }
}
