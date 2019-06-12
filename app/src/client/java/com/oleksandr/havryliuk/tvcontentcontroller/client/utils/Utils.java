package com.oleksandr.havryliuk.tvcontentcontroller.client.utils;


import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeather;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.remote.models.Data;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.remote.models.List;

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

    public static Double kelvinToCelsius(double kelvin) {
        return kelvin - TEMP;
    }

    public static String dateTimeConverter(String time) {
        Date date = stringToDateConverter(time);

        if (date == null) {
            return "";
        }

        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM HH:mm", Locale.ENGLISH);
        return format.format(date);
    }

    public static Date stringToDateConverter(String time) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        Date date;
        try {
            date = format.parse(time);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    public static String getOnlyTime(String date) {
        DateFormat format = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        return format.format(stringToDateConverter(date));
    }

    public static String getOnlyDay(String date) {
        DateFormat format = new SimpleDateFormat("EEE", Locale.ENGLISH);
        return format.format(stringToDateConverter(date));
    }

    public static boolean isUpToDate(String weatherTime) {
        long fourDays = 1000 * 60 * 60 * 24 * 4;
        Date now = new Date(),
                weather = stringToDateConverter(weatherTime);

        assert weather != null;
        if ((weather.getTime() - now.getTime()) > fourDays) {
            return true;
        } else {
            return false;
        }
    }

    public static MyWeather getUpToDateWeather(java.util.List<MyWeather> weatherList, Date date) {
        long threeHours = 1000 * 60 * 60 * 3;

        for (MyWeather w : weatherList) {
            Date weather = stringToDateConverter(w.getTime());

            assert weather != null;
            if (Math.abs((weather.getTime() - date.getTime())) <= threeHours) {
                return w;
            }
        }

        return null;
    }
}