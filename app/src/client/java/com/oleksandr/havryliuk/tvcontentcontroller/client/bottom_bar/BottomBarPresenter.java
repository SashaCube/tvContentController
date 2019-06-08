package com.oleksandr.havryliuk.tvcontentcontroller.client.bottom_bar;

import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;
import android.util.Log;

import com.oleksandr.havryliuk.tvcontentcontroller.client.weather.WeatherRepository;
import com.oleksandr.havryliuk.tvcontentcontroller.client.weather.room.MyWeather;

import java.util.List;

public class BottomBarPresenter implements BottomBarContract.IBottomBarPresenter {

    private final static String TAG = BottomBarPresenter.class.getName();

    private BottomBarContract.IBottomBarView view;
    private WeatherRepository repository;
    private Handler updateHandler;
    private MutableLiveData<List<MyWeather>> weatherForecast;

    public BottomBarPresenter(BottomBarContract.IBottomBarView view, WeatherRepository repository) {
        this.view = view;
        this.repository = repository;

        initWeather();
    }

    private void initWeather() {
        repository.loadForecast("lviv");
        weatherForecast = new MutableLiveData<>();
        weatherForecast.setValue(repository.getSavedForecast("lviv").getValue());
    }

    @Override
    public void startDisplayInfo() {
        Log.i(TAG, "Start showing Bottom Bar");
        view.startDisplayDateTime();
        startLoadingWeather();
    }

    @Override
    public void stopDisplayInfo() {
        Log.i(TAG, "Stop showing Bottom Bar");
        view.stopDisplayDateTime();
        updateHandler = null;
    }

    private void startLoadingWeather() {
        updateHandler = new Handler();

        Runnable r = new Runnable() {
            public void run() {
                repository.loadForecast("lviv"); // TODO: 08.06.19  replace static "lviv" to location from preference
                if (updateHandler != null) {
                    updateHandler.postDelayed(this, 60 * 60 * 1000);
                }
            }
        };
        updateHandler.post(r);
    }

    @Override
    public List<MyWeather> getWeather() {
        return weatherForecast.getValue();
    }
}
