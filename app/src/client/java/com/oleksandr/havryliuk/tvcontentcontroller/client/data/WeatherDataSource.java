package com.oleksandr.havryliuk.tvcontentcontroller.client.data;

import android.support.annotation.NonNull;

import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeather;

import java.util.List;

public interface WeatherDataSource extends WeatherSubject {

    interface LoadWeatherCallback {

        void onDataLoaded(List<MyWeather> weatherList);

        void onDataNotAvailable();
    }

    void destroyInstance();

    void loadWeather(String city, LoadWeatherCallback callback);

    void getWeatherByCity(String city, @NonNull WeatherDataSource.LoadWeatherCallback callback);

    void deleteAllWeatherInfo();

    void insertWeather(List<MyWeather> weatherList);

    void deleteWeatherByCity(@NonNull String city);
}
