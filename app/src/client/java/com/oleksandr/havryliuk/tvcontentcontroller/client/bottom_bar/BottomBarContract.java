package com.oleksandr.havryliuk.tvcontentcontroller.client.bottom_bar;

import android.support.v4.app.Fragment;
import android.view.View;

import com.oleksandr.havryliuk.tvcontentcontroller.client.weather.room.MyWeather;

import java.util.List;

public interface BottomBarContract {

    interface IBottomBarView {

        void init(Fragment fragment, View root);

        void setPresenter(IBottomBarPresenter presenter);

        void startDisplayDateTime();

        void stopDisplayDateTime();
    }

    interface IBottomBarPresenter {

        void startDisplayInfo();

        void stopDisplayInfo();

        List<MyWeather> getWeather();
    }
}
