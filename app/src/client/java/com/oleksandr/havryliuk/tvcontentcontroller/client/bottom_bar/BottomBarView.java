package com.oleksandr.havryliuk.tvcontentcontroller.client.bottom_bar;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeather;
import com.oleksandr.havryliuk.tvcontentcontroller.client.utils.Utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.oleksandr.havryliuk.tvcontentcontroller.client.utils.Utils.getUpToDateWeather;
import static com.oleksandr.havryliuk.tvcontentcontroller.client.utils.Utils.isUpToDate;

public class BottomBarView implements BottomBarContract.IBottomBarView {

    private View root;
    private Fragment fragment;
    private BottomBarContract.IBottomBarPresenter presenter;
    private Thread time;
    private List<MyWeather> weatherList;

    private TextView timeTextView, dateTextView, temperatureTextView, weatherTextView,
            cloudenessTextView, humidityTextView;

    @Override
    public void init(Fragment fragment, View root) {
        this.root = root;
        this.fragment = fragment;

        initView();
    }

    @Override
    public void setPresenter(BottomBarContract.IBottomBarPresenter presenter) {
        this.presenter = presenter;
    }

    private void initView() {
        timeTextView = root.findViewById(R.id.time_text_view);
        dateTextView = root.findViewById(R.id.date_text_view);
        temperatureTextView = root.findViewById(R.id.temperature_text_view);
        humidityTextView = root.findViewById(R.id.humidity_text_view);
        cloudenessTextView = root.findViewById(R.id.cloudiness_text_view);
        weatherTextView = root.findViewById(R.id.weather_text_view);
    }

    private void initDataTime() {
        time = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        Objects.requireNonNull(fragment.getActivity()).runOnUiThread(() ->
                                setDateTimeView());
                    }
                } catch (InterruptedException e) {
                }
            }
        };
    }

    private void setDateTimeView() {
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf;
        String dateString;

        sdf = new SimpleDateFormat("HH:mm:ss");
        dateString = sdf.format(date);
        timeTextView.setText(dateString);

        sdf = new SimpleDateFormat("dd.MM");
        dateString = sdf.format(date);
        dateTextView.setText(dateString);
    }


    @Override
    public void startDisplayDateTime() {
        initDataTime();
        time.start();
    }

    @Override
    public void stopDisplayDateTime() {
        if (time.isAlive()) {
            time.interrupt();
        }
    }

    @Override
    public void setWeather(List<MyWeather> weatherList) {
        this.weatherList = weatherList;

        if (weatherList != null && !weatherList.isEmpty()) {
            setWeatherView();
        } else{
            showEmptyWeather();
        }
    }

    private void showEmptyWeather(){
        // TODO: 16.06.19 add empty view for forecast when weather null
    }

    private void setWeatherView() {
        if ((weatherList != null && !weatherList.isEmpty()) &&
                isUpToDate(weatherList.get(weatherList.size() - 1).getTime())) {

            MyWeather weather = getUpToDateWeather(weatherList, new Date());

            assert weather != null;
            weatherTextView.setText(weather.getMain());

            DecimalFormat df = new DecimalFormat("#");
            temperatureTextView.setText(df.format(Utils.kelvinToCelsius(weather.getTemp())) + "°C");

            humidityTextView.setText(weather.getHumidity() + "%");
            cloudenessTextView.setText(weather.getCloudiness() + "%");
        }
    }
}
