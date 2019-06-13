package com.oleksandr.havryliuk.tvcontentcontroller.editor.main.configuration;

import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsDataSource;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsRepository;

import java.util.Map;

import static com.oleksandr.havryliuk.tvcontentcontroller.utils.Constants.CITY_WEATHER_CONF;
import static com.oleksandr.havryliuk.tvcontentcontroller.utils.Constants.SHOW_AD_CONF;
import static com.oleksandr.havryliuk.tvcontentcontroller.utils.Constants.SHOW_WEATHER_CONF;

public class ConfigurationPresenter implements ConfigurationContract.IConfigurationPresenter {
    private ConfigurationContract.IConfigurationView view;
    private PostsRepository mRepository;
    private boolean showAd, showWeather;
    private String weatherCity;

    public ConfigurationPresenter(final ConfigurationContract.IConfigurationView view, PostsRepository postsRepository) {
        this.view = view;
        mRepository = postsRepository;

        //default
        showAd = true;
        showWeather = true;
        weatherCity = "Lviv";
    }

    @Override
    public void setAdConfiguration(boolean showAd) {
        mRepository.saveConf(SHOW_AD_CONF, showAd);
        view.showAdConfigurationChange();
    }

    @Override
    public void setWeatherConfiguration(boolean showWeather) {
        mRepository.saveConf(SHOW_WEATHER_CONF, showWeather);
        view.showWeatherConfigurationChange();
    }

    @Override
    public void setWeatherCity(String city) {
        mRepository.saveConf(CITY_WEATHER_CONF, city);
        view.showWeatherCityChange(city);
    }

    public void loadConfiguration() {
        mRepository.getConf(new PostsDataSource.LoadConfCallback() {
            @Override
            public void onConfigLoaded(Map<String, Object> configurations) {
                if (!configurations.isEmpty()) {
                    Boolean value;

                    value = (Boolean) configurations.get(SHOW_AD_CONF);
                    if (value != null) {
                        showAd = value;
                    }

                    value = (Boolean) configurations.get(SHOW_WEATHER_CONF);
                    if (value != null) {
                        showWeather = value;
                    }

                    String str = (String) configurations.get(CITY_WEATHER_CONF);
                    if (str != null) {
                        weatherCity = str;
                    }

                    if (view.isActive()) {
                        view.initAdConfiguration(showAd);
                        view.initWeatherConfiguration(showWeather, weatherCity);
                    }
                }
            }

            @Override
            public void onDataNotAvailable() {
                view.initAdConfiguration(showAd);
                view.initWeatherConfiguration(showWeather, weatherCity);
            }
        });
    }
}
