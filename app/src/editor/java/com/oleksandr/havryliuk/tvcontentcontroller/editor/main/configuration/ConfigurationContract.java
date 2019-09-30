package com.oleksandr.havryliuk.tvcontentcontroller.editor.main.configuration;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsRepositoryObserver;

import java.util.List;

public interface ConfigurationContract {

    interface IConfigurationView {

        void init(Fragment fragment, View root);

        void setPresenter(IConfigurationPresenter presenter);

        void setWeatherCityView(String city);

        void setShowingWeatherView(boolean showWeather);

        void setShowingADView(boolean showAD);

        void setScheduleShowing(boolean showSchedule);

        void setScheduleNames(List<String> names);

    }

    interface IConfigurationPresenter extends PostsRepositoryObserver {

        void setAdShowing(boolean showAd);

        void setScheduleShowing(boolean showSchedule);

        void setWeatherShowing(boolean showWeather);

        void setWeatherCity(String city);

        void start();

        void stop();

        void addSchedulerName(String scheduleName);

        void deleteSchedulerName(String scheduleName);
    }
}