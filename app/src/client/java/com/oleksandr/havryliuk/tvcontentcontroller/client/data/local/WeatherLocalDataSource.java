package com.oleksandr.havryliuk.tvcontentcontroller.client.data.local;

import android.content.Context;

import androidx.annotation.NonNull;

import com.oleksandr.havryliuk.tvcontentcontroller.client.data.WeatherDataSource;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.WeatherRepositoryObserver;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeather;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeatherDao;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.WeatherRoomDatabase;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.local.PostsLocalDataSource;
import com.oleksandr.havryliuk.tvcontentcontroller.utils.AppExecutors;

import java.util.List;

public class WeatherLocalDataSource implements WeatherDataSource {

    private static volatile WeatherLocalDataSource INSTANCE;

    private MyWeatherDao myWeatherDao;

    private AppExecutors appExecutors;

    private WeatherLocalDataSource(@NonNull AppExecutors appExecutors,
                                   @NonNull MyWeatherDao myWeatherDao) {
        this.appExecutors = appExecutors;
        this.myWeatherDao = myWeatherDao;
    }

    public static WeatherLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (PostsLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WeatherLocalDataSource(new AppExecutors(),
                            WeatherRoomDatabase.getDatabase(context).myWeatherDao());
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void destroyInstance() {
        deleteAllWeatherInfo();
        INSTANCE = null;
    }

    @Override
    public void loadWeather(String city, @NonNull LoadWeatherCallback callback) {

    }

    @Override
    public void getWeatherByCity(String city, @NonNull LoadWeatherCallback callback) {
        Runnable runnable = () -> {
            final List<MyWeather> weatherList = myWeatherDao.getWeatherByCity(city);
            appExecutors.mainThread().execute(() -> {
                if (weatherList.isEmpty()) {
                    callback.onDataNotAvailable();
                } else {
                    callback.onDataLoaded(weatherList);
                }
            });
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteAllWeatherInfo() {
        Runnable clearPostsRunnable = () -> myWeatherDao.deleteAll();
        appExecutors.diskIO().execute(clearPostsRunnable);
    }

    @Override
    public void insertWeather(List<MyWeather> weatherList) {
        Runnable clearPostsRunnable = () -> {
            for (MyWeather w : weatherList) myWeatherDao.insert(w);
        };
        appExecutors.diskIO().execute(clearPostsRunnable);
    }

    @Override
    public void deleteWeatherByCity(@NonNull String city) {
        Runnable clearPostsRunnable = () -> myWeatherDao.deleteByCity(city);
        appExecutors.diskIO().execute(clearPostsRunnable);
    }

    @Override
    public void registerObserver(WeatherRepositoryObserver repositoryObserver) {

    }

    @Override
    public void removeObserver(WeatherRepositoryObserver repositoryObserver) {

    }

    @Override
    public void notifyObserversWeatherChanged() {

    }
}
