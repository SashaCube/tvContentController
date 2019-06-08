package com.oleksandr.havryliuk.tvcontentcontroller.client.weather.api;


import com.oleksandr.havryliuk.tvcontentcontroller.client.weather.models.Data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    String BASE_URL = "http://api.openweathermap.org/";
    String APP_ID = "98eaf2c433ce1765a54018269be854e8";

    @GET("data/2.5/forecast")
    Call<Data> getWeaterByCity(@Query("q") String city, @Query("appid") String appId);

}
