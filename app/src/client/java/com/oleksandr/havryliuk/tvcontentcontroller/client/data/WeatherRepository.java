package com.oleksandr.havryliuk.tvcontentcontroller.client.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.WeatherLocalDataSource;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeather;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.remote.WeatherRemoteDataSource;

import java.util.List;


public class WeatherRepository implements WeatherDataSource {

    private static WeatherRepository INSTANCE = null;

    private WeatherDataSource mWeatherRemoteDataSource;

    private WeatherDataSource mWeatherLocalDataSource;

    private WeatherRepository(@NonNull Context context) {
        mWeatherRemoteDataSource = WeatherRemoteDataSource.getInstance();
        mWeatherLocalDataSource = WeatherLocalDataSource.getInstance(context);
    }

    public static WeatherRepository getInstance(@NonNull final Context context) {
        if (INSTANCE == null) {
            INSTANCE = new WeatherRepository(context);
        }
        return INSTANCE;
    }

    @Override
    public void destroyInstance() {
        INSTANCE.mWeatherLocalDataSource.destroyInstance();
        INSTANCE.mWeatherRemoteDataSource.destroyInstance();
        INSTANCE = null;
    }

    @Override
    public void loadWeather(String city, @NonNull WeatherDataSource.LoadWeatherCallback callback) {
        mWeatherLocalDataSource.getWeatherByCity(city, callback);

        // Fetch new data from the network.
        mWeatherRemoteDataSource.loadWeather(city, new LoadWeatherCallback() {
            @Override
            public void onDataLoaded(List<MyWeather> weatherList) {
                callback.onDataLoaded(weatherList);

                if (weatherList != null && !weatherList.isEmpty()) {
                    deleteWeatherByCity(city);
                    mWeatherLocalDataSource.insertWeather(weatherList);
                }
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getWeatherByCity(String city, @NonNull WeatherDataSource.LoadWeatherCallback callback) {
        mWeatherLocalDataSource.getWeatherByCity(city, callback);
    }

    @Override
    public void deleteAllWeatherInfo() {
        mWeatherLocalDataSource.deleteAllWeatherInfo();
    }

    @Override
    public void insertWeather(List<MyWeather> weatherList) {

    }

    @Override
    public void deleteWeatherByCity(@NonNull String city) {
        mWeatherLocalDataSource.deleteWeatherByCity(city);
    }
}