package com.oleksandr.havryliuk.tvcontentcontroller.client.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.WeatherLocalDataSource;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeather;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.remote.WeatherRemoteDataSource;

import java.util.ArrayList;
import java.util.List;


public class WeatherRepository implements WeatherDataSource {

    private static WeatherRepository INSTANCE = null;

    private WeatherDataSource mWeatherRemoteDataSource;

    private WeatherDataSource mWeatherLocalDataSource;

    private List<MyWeather> weatherList;

    private List<WeatherRepositoryObserver> weatherRepositoryObservers;

    private WeatherRepository(@NonNull Context context) {
        mWeatherRemoteDataSource = WeatherRemoteDataSource.getInstance();
        mWeatherLocalDataSource = WeatherLocalDataSource.getInstance(context);

        weatherRepositoryObservers = new ArrayList<>();
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

                if (weatherList != null && !weatherList.isEmpty()) {
                    deleteWeatherByCity(city);
                    mWeatherLocalDataSource.insertWeather(weatherList);
                }
                notifyObserversWeatherChanged();
            }

            @Override
            public void onDataNotAvailable() {

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

    @Override
    public void registerObserver(WeatherRepositoryObserver repositoryObserver) {
        weatherRepositoryObservers.add(repositoryObserver);
    }

    @Override
    public void removeObserver(WeatherRepositoryObserver repositoryObserver) {
        weatherRepositoryObservers.remove(repositoryObserver);
    }

    @Override
    public void notifyObserversWeatherChanged() {
        for (WeatherRepositoryObserver observer : weatherRepositoryObservers) {
            observer.onWeatherDataChanged(weatherList);
        }
    }
}