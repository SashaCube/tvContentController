package com.oleksandr.havryliuk.tvcontentcontroller.client.data;

public interface WeatherSubject {

    void registerObserver(WeatherRepositoryObserver repositoryObserver);

    void removeObserver(WeatherRepositoryObserver repositoryObserver);

    void notifyObserversWeatherChanged();
}