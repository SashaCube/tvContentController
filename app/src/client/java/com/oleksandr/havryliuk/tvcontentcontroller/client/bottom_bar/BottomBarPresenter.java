package com.oleksandr.havryliuk.tvcontentcontroller.client.bottom_bar;

import android.os.Handler;
import android.util.Log;

import com.oleksandr.havryliuk.tvcontentcontroller.client.data.WeatherDataSource;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.WeatherRepository;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeather;

import java.util.List;

public class BottomBarPresenter implements BottomBarContract.IBottomBarPresenter {

    private final static String TAG = BottomBarPresenter.class.getName();

    private BottomBarContract.IBottomBarView view;
    private WeatherRepository repository;

    class WeatherCallback implements WeatherDataSource.LoadWeatherCallback {
        @Override
        public void onDataLoaded(List<MyWeather> data) {

            view.setWeather(data);
        }

        @Override
        public void onDataNotAvailable() {
        }
    }

    public BottomBarPresenter(BottomBarContract.IBottomBarView view, WeatherRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void loadWeather() {
        repository.loadWeather("Lviv", new WeatherCallback());
    }

    private void getSavedWeather() {
        repository.getWeatherByCity("Lviv", new WeatherCallback());
    }

    @Override
    public void startDisplayInfo() {
        Log.i(TAG, "Start showing Bottom Bar");
        view.startDisplayDateTime();
        Log.i(TAG, "Weather forecast -> data not available");

        Handler handler = new Handler();
        Runnable r = this::getSavedWeather;
        handler.post(r);
    }

    @Override
    public void stopDisplayInfo() {
        Log.i(TAG, "Stop showing Bottom Bar");
        view.stopDisplayDateTime();
    }
}
