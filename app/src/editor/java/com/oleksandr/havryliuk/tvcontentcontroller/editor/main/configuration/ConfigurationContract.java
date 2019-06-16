package com.oleksandr.havryliuk.tvcontentcontroller.editor.main.configuration;

import android.support.v4.app.Fragment;
import android.view.View;

import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsRepositoryObserver;

public interface ConfigurationContract {

    interface IConfigurationView {

        void init(Fragment fragment, View root);

        void setPresenter(IConfigurationPresenter presenter);

        void showAdConfigurationChange();

        void showWeatherConfigurationChange();

        void showWeatherCityChange(String city);

        void setWeatherCityView(String city);

        void setShowingWeatherView(boolean showWeather);

        void setShowingADView(boolean showAD);
    }

    interface IConfigurationPresenter extends PostsRepositoryObserver {

        void setAdShowing(boolean showAd);

        void setWeatherShowing(boolean showWeather);

        void setWeatherCity(String city);

        void start();

        void stop();
    }
}
