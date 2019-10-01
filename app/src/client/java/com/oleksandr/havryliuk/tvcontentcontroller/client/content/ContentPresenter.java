package com.oleksandr.havryliuk.tvcontentcontroller.client.content;

import android.os.AsyncTask;
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

public class ContentPresenter implements ContentContract.IContentPresenter {

    private final static String TAG = ContentPresenter.class.getName();

    private ContentContract.IContentView view;
    private PostsDataSource postsRepository;
    private WeatherRepository weatherRepository;
    private volatile String weatherCity = "";
    private List<Schedule> scheduleList = new ArrayList<>();

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
            Log.i(TAG, "setADShowingState " + showAD);
        }

        (new LoadScheduleTask()).execute(configurations);

        Boolean showWeather = (Boolean) configurations.get(Constants.SHOW_WEATHER_CONF);
        if (showWeather != null) {
            view.setWeatherShowingState(showWeather);
            Log.i(TAG, "setWeatherShowingState " + showWeather);
        }

        String newWeatherCity = (String) configurations.get(Constants.CITY_WEATHER_CONF);
        if (newWeatherCity == null) {
            newWeatherCity = "Lviv";
        }

        Boolean showSchedule = (Boolean) configurations.get(Constants.SHOW_SCHEDULE_CONF);
        if (showSchedule != null) {
            view.setScheduleShowingState(showSchedule);
            Log.i(TAG, "setScheduleShowingState " + showSchedule);
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
    }

    class LoadScheduleTask extends AsyncTask<Map<String, Object>, Integer, List<Schedule>> {

        @Override
        protected List<Schedule> doInBackground(Map<String, Object>... configurations) {
            List<String> teacherScheduler = new ArrayList<>();
            teacherScheduler.addAll((List<String>) configurations[0].get(Constants.SCHEDULE_TEACHER_CONF));
            if (teacherScheduler != null) {
                for (String s : teacherScheduler) {
                    scheduleList.add(new Schedule(s, NULPScheduleHelper.INSTANCE.getTeacherSchedule(s)));
                }
            }
            Log.i(TAG, "Schedule Load Teachers " + teacherScheduler);

            List<String> groupScheduler = new ArrayList<>();
            groupScheduler.addAll((List<String>) configurations[0].get(Constants.SCHEDULE_GROUP_CONF));
            if (groupScheduler != null) {
                for (String s : groupScheduler) {
                    scheduleList.add(new Schedule(s, NULPScheduleHelper.INSTANCE.getGroupSchedule(s)));
                }
            }
            Log.i(TAG, "Schedule Load Groups " + teacherScheduler);

            return scheduleList;
        }

        @Override
        protected void onPostExecute(List<Schedule> schedules) {
            super.onPostExecute(schedules);

            if (!schedules.isEmpty()) {
                view.setSchedule(schedules);
            }
            Log.i(TAG, "All Schedules " + scheduleList);
        }
    }
}