package com.oleksandr.havryliuk.tvcontentcontroller.client.data;

import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeather;

import java.util.List;

public interface WeatherRepositoryObserver {

    void onWeatherDataChanged(List<MyWeather> weatherList);

}