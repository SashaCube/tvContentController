package com.oleksandr.havryliuk.tvcontentcontroller.client.bottom_bar;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.oleksandr.havryliuk.tvcontentcontroller.client.data.WeatherRepositoryObserver;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeather;

import java.util.List;

public interface BottomBarContract {

    interface IBottomBarView {

        void init(Fragment fragment, View root);

        void setPresenter(IBottomBarPresenter presenter);

        void startDisplayDateTime();

        void stopDisplayDateTime();

        void setWeather(List<MyWeather> weatherList);
    }

    interface IBottomBarPresenter extends WeatherRepositoryObserver {

        void startDisplayInfo();

        void stopDisplayInfo();
    }
}
