package com.oleksandr.havryliuk.tvcontentcontroller.client.content;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.oleksandr.havryliuk.tvcontentcontroller.client.data.WeatherRepositoryObserver;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeather;
import com.oleksandr.havryliuk.tvcontentcontroller.client.schedule.model.Schedule;
import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsRepositoryObserver;

import java.util.List;

public interface ContentContract {

    interface IContentView {

        void init(Fragment fragment, View root);

        void setPresenter(IContentPresenter presenter);

        void setPosts(List<Post> posts);

        boolean isActive();

        void startDisplayPosts();

        void stopDisplayPosts();

        void setWeather(List<MyWeather> weatherList);

        void setSchedule(List<Schedule> scheduleList);

        void setWeatherShowingState(boolean showWeather);

        void setScheduleShowingState(boolean showSchedule);

        void setADShowingState(boolean showWeather);
    }

    interface IContentPresenter extends PostsRepositoryObserver, WeatherRepositoryObserver {

        void startShowingPosts();

        void stopShowingPosts();

        void loadWeather();

        void loadSchedule();
    }
}
