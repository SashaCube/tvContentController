package com.oleksandr.havryliuk.tvcontentcontroller.editor.main.configuration;

import android.support.v4.app.Fragment;
import android.view.View;

public interface ConfigurationContract {

    interface IConfigurationView{

        void init(Fragment fragment, View root);

        void setPresenter(IConfigurationPresenter presenter);

        void initAdConfiguration(boolean showAd);

        void showAdConfigurationChange();

        void initWeatherConfiguration(boolean showWeather, String city);

        void showWeatherConfigurationChange();

        void showWeatherCityChange(String city);

        boolean isActive();
    }

    interface IConfigurationPresenter{

        void setAdConfiguration(boolean showAd);

        void setWeatherConfiguration(boolean showWeather);

        void setWeatherCity(String city);

        void loadConfiguration();

    }
}
