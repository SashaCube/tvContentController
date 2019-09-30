package com.oleksandr.havryliuk.tvcontentcontroller.editor.main.configuration;

import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsRepository;
import com.oleksandr.havryliuk.tvcontentcontroller.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.oleksandr.havryliuk.tvcontentcontroller.utils.Constants.CITY_WEATHER_CONF;
import static com.oleksandr.havryliuk.tvcontentcontroller.utils.Constants.SHOW_AD_CONF;
import static com.oleksandr.havryliuk.tvcontentcontroller.utils.Constants.SHOW_SCHEDULE_CONF;
import static com.oleksandr.havryliuk.tvcontentcontroller.utils.Constants.SHOW_WEATHER_CONF;

public class ConfigurationPresenter implements ConfigurationContract.IConfigurationPresenter {
    private ConfigurationContract.IConfigurationView view;
    private PostsRepository mRepository;
    private List<String> scheduleGroups = new ArrayList<>();
    private List<String> scheduleTeachers = new ArrayList<>();

    ConfigurationPresenter(final ConfigurationContract.IConfigurationView view, PostsRepository postsRepository) {
        this.view = view;
        mRepository = postsRepository;
    }

    @Override
    public void setAdShowing(boolean showAd) {
        mRepository.saveConf(SHOW_AD_CONF, showAd);
    }

    @Override
    public void setWeatherShowing(boolean showWeather) {
        mRepository.saveConf(SHOW_WEATHER_CONF, showWeather);
    }

    @Override
    public void setWeatherCity(String city) {
        mRepository.saveConf(CITY_WEATHER_CONF, city);
    }

    @Override
    public void start() {
        mRepository.registerObserver(this);
        mRepository.notifyObserversConfChanged();
    }

    @Override
    public void stop() {
        mRepository.removeObserver(this);
    }

    @Override
    public void onPostDataChanged(List<Post> posts) {

    }

    @Override
    public void setScheduleShowing(boolean showSchedule) {
        mRepository.saveConf(SHOW_SCHEDULE_CONF, showSchedule);
    }

    @Override
    public void addSchedulerName(String scheduleName) {
        if (isGroup(scheduleName)) {
            scheduleGroups.add(scheduleName);
            mRepository.saveConf(Constants.SCHEDULE_GROUP_CONF, scheduleGroups);
        } else {
            scheduleTeachers.add(scheduleName.replace(" ", "+"));
            mRepository.saveConf(Constants.SCHEDULE_TEACHER_CONF, scheduleTeachers);
        }
    }

    private boolean isGroup(String name) {
        return name.startsWith("КН");
    }

    @Override
    public void deleteSchedulerName(String scheduleName) {
        if (isGroup(scheduleName)) {
            scheduleGroups.remove(scheduleName);
            mRepository.saveConf(Constants.SCHEDULE_GROUP_CONF, scheduleGroups);
        } else {
            scheduleTeachers.remove(scheduleName.replace(" ", "+"));
            mRepository.saveConf(Constants.SCHEDULE_TEACHER_CONF, scheduleTeachers);
        }
    }

    @Override
    public void onConfDataChanged(Map<String, Object> conf) {
        updateConf(conf);
    }

    private void updateConf(Map<String, Object> conf) {
        if (!conf.isEmpty()) {
            Boolean value;

            value = (Boolean) conf.get(SHOW_AD_CONF);
            if (value != null) {
                view.setShowingADView(value);
            }

            value = (Boolean) conf.get(SHOW_WEATHER_CONF);
            if (value != null) {
                view.setShowingWeatherView(value);
            }

            String weatherCity = (String) conf.get(CITY_WEATHER_CONF);
            if (weatherCity != null) {
                view.setWeatherCityView(weatherCity);
            }

            List<String> schedules = new ArrayList<>();

            List<String> teacherList = (List<String>) conf.get(Constants.SCHEDULE_TEACHER_CONF);
            if (teacherList != null) {
                scheduleTeachers.clear();
                scheduleTeachers.addAll(teacherList);
            }

            List<String> groupList = (List<String>) conf.get(Constants.SCHEDULE_GROUP_CONF);
            if (groupList != null) {
                scheduleGroups.clear();
                scheduleGroups.addAll(groupList);
            }

            schedules.addAll(scheduleTeachers);
            schedules.addAll(scheduleGroups);

            if (!schedules.isEmpty()) {
                view.setScheduleNames(schedules);
            }
        }
    }
}