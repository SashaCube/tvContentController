package com.oleksandr.havryliuk.tvcontentcontroller.editor.main.configuration;

import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsRepository;

import java.util.List;
import java.util.Map;

import static com.oleksandr.havryliuk.tvcontentcontroller.utils.Constants.CITY_WEATHER_CONF;
import static com.oleksandr.havryliuk.tvcontentcontroller.utils.Constants.SHOW_AD_CONF;
import static com.oleksandr.havryliuk.tvcontentcontroller.utils.Constants.SHOW_WEATHER_CONF;

public class ConfigurationPresenter implements ConfigurationContract.IConfigurationPresenter {
    private ConfigurationContract.IConfigurationView view;
    private PostsRepository mRepository;

    public ConfigurationPresenter(final ConfigurationContract.IConfigurationView view, PostsRepository postsRepository) {
        this.view = view;
        mRepository = postsRepository;
    }

    @Override
    public void setAdShowing(boolean showAd) {
        mRepository.saveConf(SHOW_AD_CONF, showAd);
        view.showAdConfigurationChange();
    }

    @Override
    public void setWeatherShowing(boolean showWeather) {
        mRepository.saveConf(SHOW_WEATHER_CONF, showWeather);
        view.showWeatherConfigurationChange();
    }

    @Override
    public void setWeatherCity(String city) {
        mRepository.saveConf(CITY_WEATHER_CONF, city);
        view.showWeatherCityChange(city);
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
        }
    }
}
