package com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MyWeatherDao {

    @Insert
    void insert(MyWeather weather);

    @Query("DELETE FROM weather_table")
    void deleteAll();

    @Query("SELECT * FROM weather_table ORDER BY time ASC")
    List<MyWeather> getAllWeather();

    @Query("SELECT * FROM weather_table WHERE city = :city")
    List<MyWeather> getWeatherByCity(String city);

    @Query("DELETE FROM weather_table WHERE city = :city")
    void deleteByCity(String city);
}
