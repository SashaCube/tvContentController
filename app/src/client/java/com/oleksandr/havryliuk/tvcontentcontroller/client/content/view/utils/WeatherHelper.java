package com.oleksandr.havryliuk.tvcontentcontroller.client.content.view.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeather;
import com.oleksandr.havryliuk.tvcontentcontroller.client.utils.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.oleksandr.havryliuk.tvcontentcontroller.client.content.view.utils.ContentViewUtils.getFutureWeather;
import static com.oleksandr.havryliuk.tvcontentcontroller.client.content.view.utils.ContentViewUtils.getIconUrl;
import static com.oleksandr.havryliuk.tvcontentcontroller.client.utils.Utils.getOnlyDay;
import static com.oleksandr.havryliuk.tvcontentcontroller.client.utils.Utils.getOnlyTime;
import static com.oleksandr.havryliuk.tvcontentcontroller.client.utils.Utils.getUpToDateWeather;
import static com.oleksandr.havryliuk.tvcontentcontroller.client.utils.Utils.kelvinToCelsius;

public class WeatherHelper {

    private ImageView mainWeatherImage;
    private TextView aboutWeatherText, mainTemperatureText, humidityText, cloudinessText,
            pressureText, cityWeatherText;

    private List<TextView> timeTextViews, futureTemperatures, futureDays;
    private List<ImageView> futuresImages;
    private List<TemperatureView> temperatureViews;
    private List<MyWeather> weatherList;
    private boolean showWeatherState;
    private Context context;

    public WeatherHelper(View root, Context context) {
        this.context = context;
        initWeatherForecastView(root);
    }

    private void initWeatherForecastView(View root) {
        //main weather
        mainWeatherImage = root.findViewById(R.id.main_weather_image_view);
        aboutWeatherText = root.findViewById(R.id.about_weather_text_view);
        mainTemperatureText = root.findViewById(R.id.temperature_text_view);
        humidityText = root.findViewById(R.id.humidity_text_view);
        cloudinessText = root.findViewById(R.id.cloudiness_text_view);
        pressureText = root.findViewById(R.id.pressure_text_view);
        cityWeatherText = root.findViewById(R.id.city_weather_text_view);

        //main temperature
        temperatureViews = new ArrayList<>();
        temperatureViews.add(root.findViewById(R.id.tv1));
        temperatureViews.add(root.findViewById(R.id.tv2));
        temperatureViews.add(root.findViewById(R.id.tv3));
        temperatureViews.add(root.findViewById(R.id.tv4));
        temperatureViews.add(root.findViewById(R.id.tv5));
        temperatureViews.add(root.findViewById(R.id.tv6));
        temperatureViews.add(root.findViewById(R.id.tv7));
        temperatureViews.add(root.findViewById(R.id.tv8));

        timeTextViews = new ArrayList<>();
        timeTextViews.add(root.findViewById(R.id.time_text_view_1));
        timeTextViews.add(root.findViewById(R.id.time_text_view_2));
        timeTextViews.add(root.findViewById(R.id.time_text_view_3));
        timeTextViews.add(root.findViewById(R.id.time_text_view_4));
        timeTextViews.add(root.findViewById(R.id.time_text_view_5));
        timeTextViews.add(root.findViewById(R.id.time_text_view_6));
        timeTextViews.add(root.findViewById(R.id.time_text_view_7));
        timeTextViews.add(root.findViewById(R.id.time_text_view_8));

        for (TemperatureView tv : temperatureViews) {
            tv.setBottomPartColor(Objects.requireNonNull(context).getResources().getColor(R.color.light_orange));
            tv.setSeparatorColor(context.getResources().getColor(R.color.orange));
            tv.setTextColor(context.getResources().getColor(R.color.slate_gray));
            tv.setShowSeparator(true);
            tv.setShowStartValue(true);
        }

        //future weather
        futureDays = new ArrayList<>();
        futureDays.add(root.findViewById(R.id.day_future_weather_text_1));
        futureDays.add(root.findViewById(R.id.day_future_weather_text_2));
        futureDays.add(root.findViewById(R.id.day_future_weather_text_3));

        futureTemperatures = new ArrayList<>();
        futureTemperatures.add(root.findViewById(R.id.temperature_future_weather_text_1));
        futureTemperatures.add(root.findViewById(R.id.temperature_future_weather_text_2));
        futureTemperatures.add(root.findViewById(R.id.temperature_future_weather_text_3));

        futuresImages = new ArrayList<>();
        futuresImages.add(root.findViewById(R.id.future_weather_image_1));
        futuresImages.add(root.findViewById(R.id.future_weather_image_2));
        futuresImages.add(root.findViewById(R.id.future_weather_image_3));
    }

    public void showMainWeather() {
        MyWeather weather = getUpToDateWeather(weatherList, new Date());

        assert weather != null;
        cityWeatherText.setText(weather.getCity());
        aboutWeatherText.setText(weather.getMain());

        DecimalFormat df = new DecimalFormat("#");
        mainTemperatureText.setText(df.format(Utils.kelvinToCelsius(weather.getTemp())) + "°C");

        humidityText.setText(" " + weather.getHumidity() + "%");
        cloudinessText.setText(" " + weather.getCloudiness() + "%");
        pressureText.setText(" " + weather.getPressure());

        Glide.with(context).load(getIconUrl(weather.getIconId())).into(mainWeatherImage);
    }

    public void showMainTemperature() {
        MyWeather weather = getUpToDateWeather(weatherList, new Date());
        int startTemp, endTemp, index, i = 0, minTemp, maxTemp;

        endTemp = (kelvinToCelsius(weather.getTemp())).intValue();
        minTemp = endTemp;
        maxTemp = endTemp;

        for (TemperatureView tv : temperatureViews) {
            index = weatherList.indexOf(weather);
            startTemp = endTemp;

            if (index + 1 < weatherList.size()) {
                endTemp = kelvinToCelsius(weatherList.get(index + 1).getTemp()).intValue();
            }

            tv.setStartValue(startTemp);
            tv.setEndValue(endTemp);

            timeTextViews.get(i++).setText(getOnlyTime(weather.getTime()));
            weather = weatherList.get(index + 1);


            if (endTemp < minTemp) {
                minTemp = endTemp;
            }
            if (endTemp > maxTemp) {
                maxTemp = endTemp;
            }
        }

        minTemp = (int) (minTemp * 0.8);
        maxTemp = (int) (maxTemp * 1.2);

        for (TemperatureView tv : temperatureViews) {
            tv.setMaxValue(maxTemp);
            tv.setMinValue(minTemp);
        }
    }

    public void showFutureWeather() {
        if (weatherList == null || weatherList.isEmpty()) {
            return;
        }
        List<MyWeather> futureWeather = getFutureWeather(weatherList);
        MyWeather weather;

        for (int i = 0; i < 3; i++) {
            weather = futureWeather.get(i);

            Glide.with(context)
                    .load(getIconUrl(weather.getIconId()))
                    .into(futuresImages.get(i));

            futureDays.get(i).setText(getOnlyDay(weather.getTime()));

            DecimalFormat df = new DecimalFormat("#.##");
            futureTemperatures.get(i)
                    .setText(df.format(Utils.kelvinToCelsius(weather.getTemp())) + "°C");
        }
    }

    public List<MyWeather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<MyWeather> weatherList) {
        this.weatherList = weatherList;
    }

    public boolean isShowWeatherState() {
        return showWeatherState;
    }

    public void setShowWeatherState(boolean showWeatherState) {
        this.showWeatherState = showWeatherState;
    }
}
