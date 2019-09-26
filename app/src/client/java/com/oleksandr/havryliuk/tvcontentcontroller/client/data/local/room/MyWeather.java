package com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.oleksandr.havryliuk.tvcontentcontroller.client.data.remote.models.List;

@Entity(tableName = "weather_table")
public class MyWeather {

    public MyWeather(@NonNull String city, String time, String main, Double temp,
                     Integer humidity, Double pressure, Integer cloudiness, String iconId) {
        this.city = city;
        this.time = time;
        this.main = main;
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.cloudiness = cloudiness;
        this.iconId = iconId;
    }

    public MyWeather(String city, List list) {
        this.city = city;
        this.time = list.getDtTxt();
        this.main = list.getWeather().get(0).getMain();
        this.temp = list.getMain().getTemp();
        this.humidity = list.getMain().getHumidity();
        this.pressure = list.getMain().getPressure();
        this.cloudiness = list.getClouds().getAll();
        this.iconId = list.getWeather().get(0).getIcon();
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "main")
    private String main;

    @ColumnInfo(name = "temp")
    private Double temp;

    @ColumnInfo(name = "humidity")
    private Integer humidity;

    @ColumnInfo(name = "pressure")
    private Double pressure;

    @ColumnInfo(name = "iconId")
    private String iconId;

    @ColumnInfo(name = "cloudiness")
    private Integer cloudiness;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getCity() {
        return city;
    }

    public void setCity(@NonNull String city) {
        this.city = city;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Integer getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(Integer cloudiness) {
        this.cloudiness = cloudiness;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }
}
