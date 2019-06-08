package com.oleksandr.havryliuk.tvcontentcontroller.client.weather;


import com.oleksandr.havryliuk.tvcontentcontroller.client.weather.models.Data;
import com.oleksandr.havryliuk.tvcontentcontroller.client.weather.models.List;
import com.oleksandr.havryliuk.tvcontentcontroller.client.weather.room.MyWeather;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Utils {
    private static final double TEMP = 273.15;

    public static java.util.List<MyWeather> weatherConverter(Data data) {
        java.util.List<MyWeather> weatherList = new ArrayList<>();
        MyWeather myWeather;

        for (List l : data.getList()) {
            myWeather = new MyWeather(data.getCity().getName(), l);
            weatherList.add(myWeather);
        }

        return weatherList;
    }

    public static double kelvinToCelsius(double kelvin) {
        return kelvin - TEMP;
    }

    public static String dateTimeConverter(String time) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        Date date;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            return "";
        }

        format = new SimpleDateFormat("EEE, d MMM HH:mm", Locale.ENGLISH);
        return format.format(date);
    }
}