package com.oleksandr.havryliuk.tvcontentcontroller.client.content;

import android.util.Log;

import com.oleksandr.havryliuk.tvcontentcontroller.client.data.WeatherRepository;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeather;
import com.oleksandr.havryliuk.tvcontentcontroller.client.schedule.NULPScheduleHelper;
import com.oleksandr.havryliuk.tvcontentcontroller.client.schedule.model.Schedule;
import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsDataSource;
import com.oleksandr.havryliuk.tvcontentcontroller.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kotlinx.coroutines.GlobalScope;

public class ContentPresenter implements ContentContract.IContentPresenter {

    private final static String TAG = ContentPresenter.class.getName();

    private ContentContract.IContentView view;
    private PostsDataSource postsRepository;
    private WeatherRepository weatherRepository;
    private volatile String weatherCity = "";

    public ContentPresenter(ContentContract.IContentView view, PostsDataSource repository, WeatherRepository weatherRepository) {
        this.view = view;
        this.postsRepository = repository;
        this.weatherRepository = weatherRepository;
    }

    @Override
    public void onPostDataChanged(List<Post> posts) {
        updatePosts(posts);
    }

    @Override
    public void onConfDataChanged(Map<String, Object> conf) {
        if (conf != null && !conf.isEmpty()) {
            updateConf(conf);
        }
    }

    @Override
    public void onWeatherDataChanged(List<MyWeather> weatherList) {
        view.setWeather(weatherList);
        Log.i(TAG, "Update Weather");
    }

    private void updateConf(Map<String, Object> configurations) {
        Log.i(TAG, "Update configuration");

        Boolean showAD = (Boolean) configurations.get(Constants.SHOW_AD_CONF);
        if (showAD != null) {
            view.setADShowingState(showAD);
        }

        Boolean showWeather = (Boolean) configurations.get(Constants.SHOW_WEATHER_CONF);
        if (showWeather != null) {
            view.setWeatherShowingState(showWeather);
        }

        String newWeatherCity = (String) configurations.get(Constants.CITY_WEATHER_CONF);
        if (newWeatherCity == null) {
            newWeatherCity = "Lviv";
        }

        if (!weatherCity.equals(newWeatherCity)) {
            weatherCity = newWeatherCity;
            loadWeather();
            Log.i(TAG, "set Weather city " + weatherCity);
        }
    }

    private void updatePosts(List<Post> posts) {
        view.setPosts(posts);
        Log.i(TAG, "Update posts " + posts);
    }

    @Override
    public void startShowingPosts() {
        postsRepository.registerObserver(this);
        weatherRepository.registerObserver(this);

        postsRepository.notifyObserversConfChanged();
        postsRepository.notifyObserversPostsChanged();
        weatherRepository.notifyObserversWeatherChanged();

        view.startDisplayPosts();

        Log.i(TAG, "Start showing Content");
    }

    @Override
    public void stopShowingPosts() {
        view.stopDisplayPosts();

        postsRepository.removeObserver(this);
        weatherRepository.removeObserver(this);

        Log.i(TAG, "Stop showing Content");
    }

    @Override
    public void loadWeather() {
        weatherRepository.loadWeather(weatherCity, null);
        Log.i(TAG, "load Weather city " + weatherCity);
    }

    @Override
    public void loadSchedule() {
        view.setScheduleShowingState(true); // TODO: 26.09.19 Oleksandr : set to real value from firebase

        view.setSchedule(NULPScheduleHelper.INSTANCE.getScheduleList());
    }
}
