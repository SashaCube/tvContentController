package com.oleksandr.havryliuk.tvcontentcontroller.client.data.remote;

import android.support.annotation.NonNull;

import com.oleksandr.havryliuk.tvcontentcontroller.client.data.WeatherDataSource;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeather;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.remote.api.APIInterface;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.remote.api.RetrofitClient;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.remote.models.Data;
import com.oleksandr.havryliuk.tvcontentcontroller.client.utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRemoteDataSource implements WeatherDataSource {

    private static WeatherRemoteDataSource INSTANCE;
    private APIInterface apiInterface;

    public static WeatherRemoteDataSource getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new WeatherRemoteDataSource();
            INSTANCE.apiInterface = RetrofitClient.getApi(APIInterface.BASE_URL);
        }
        return INSTANCE;

    }

    public void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void loadWeather(String city, @NonNull LoadWeatherCallback callback) {
        Call<Data> call = apiInterface.getWeaterByCity(city, APIInterface.APP_ID);
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.body() == null) {
                    return;
                }
                Data data = response.body();
                callback.onDataLoaded(Utils.weatherConverter(data));
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getWeatherByCity(String city, @NonNull LoadWeatherCallback callback) {

    }

    @Override
    public void deleteAllWeatherInfo() {

    }

    @Override
    public void insertWeather(List<MyWeather> weatherList) {

    }

    @Override
    public void deleteWeatherByCity(@NonNull String city) {

    }
}
