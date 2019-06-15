package com.oleksandr.havryliuk.tvcontentcontroller.client.content;

import android.support.v4.app.Fragment;
import android.view.View;

import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeather;
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

        void setWeatherShowingState(boolean showWeather);

        void setADShowingState(boolean showWeather);
    }

    interface IContentPresenter extends PostsRepositoryObserver {

        void startShowingPosts();

        void stopShowingPosts();

        void loadWeather();
    }
}
