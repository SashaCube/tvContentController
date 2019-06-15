package com.oleksandr.havryliuk.tvcontentcontroller.client.content;

import android.util.Log;

import com.oleksandr.havryliuk.tvcontentcontroller.client.data.WeatherDataSource;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.WeatherRepository;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeather;
import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsDataSource;
import com.oleksandr.havryliuk.tvcontentcontroller.utils.Constants;

import java.util.List;
import java.util.Map;

public class ContentPresenter implements ContentContract.IContentPresenter {

    private final static String TAG = ContentPresenter.class.getName();

    private ContentContract.IContentView view;
    private PostsDataSource postsRepository;
    private WeatherRepository weatherRepository;
    private String weatherCity = "";

    public ContentPresenter(ContentContract.IContentView view, PostsDataSource repository, WeatherRepository weatherRepository) {
        this.view = view;
        this.postsRepository = repository;
        this.weatherRepository = weatherRepository;
    }

    @Override
    public void onPostDataChanged(List<Post> posts) {
        if (posts == null || posts.isEmpty()) {
            // TODO: 15.06.19 handle that
        } else {
            updatePosts(posts);
        }
    }

    @Override
    public void onConfDataChanged(Map<String, Object> conf) {
        if (conf == null || conf.isEmpty()) {
            // TODO: 15.06.19 handle that
        } else {
            updateConf(conf);
        }
    }

    class WeatherCallback implements WeatherDataSource.LoadWeatherCallback {
        @Override
        public void onDataLoaded(List<MyWeather> data) {
            view.setWeather(data);
        }

        @Override
        public void onDataNotAvailable() {

        }
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
            // TODO: 15.06.19 init weather city by default from preferences and an comment loadWeather()
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
        Log.i(TAG, "Update posts " + posts.size());
    }

    @Override
    public void startShowingPosts() {
        postsRepository.registerObserver(this);
        postsRepository.notifyObserversConfChanged();
        postsRepository.notifyObserversPostsChanged();
        view.startDisplayPosts();
        Log.i(TAG, "Start showing Posts");
    }

    @Override
    public void stopShowingPosts() {
        view.stopDisplayPosts();
        postsRepository.removeObserver(this);
        Log.i(TAG, "Stop showing Posts");
    }

    @Override
    public void loadWeather() {
        weatherRepository.loadWeather(weatherCity, new WeatherCallback());
        Log.i(TAG, "load Weather city " + weatherCity);
    }
}
