package com.oleksandr.havryliuk.tvcontentcontroller.client.content;

import android.os.Handler;
import android.util.Log;

import com.oleksandr.havryliuk.tvcontentcontroller.client.data.WeatherDataSource;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.WeatherRepository;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeather;
import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsDataSource;
import com.oleksandr.havryliuk.tvcontentcontroller.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.AD;

public class ContentPresenter implements ContentContract.IContentPresenter {

    private final static String TAG = ContentPresenter.class.getName();
    public final static String WEATHER = "weather";

    private ContentContract.IContentView view;
    private PostsDataSource postsRepository;
    private WeatherRepository weatherRepository;
    private boolean showAD = false, showWeather = false;
    private volatile List<Post> posts;
    private volatile String  weatherCity = "Lviv";
    private volatile String newWeatherCity = "Lviv";
    private Handler updateHandler;

    class WeatherCallback implements WeatherDataSource.LoadWeatherCallback {
        @Override
        public void onDataLoaded(List<MyWeather> data) {
            view.setWeather(data);
        }

        @Override
        public void onDataNotAvailable() {

        }
    }

    public ContentPresenter(ContentContract.IContentView view, PostsDataSource repository, WeatherRepository weatherRepository) {
        this.view = view;
        this.postsRepository = repository;
        this.weatherRepository = weatherRepository;
    }

    @Override
    public void loadPosts() {

        postsRepository.getConf(new PostsDataSource.LoadConfCallback() {
            @Override
            public void onConfigLoaded(Map<String, Object> configurations) {
                // TODO: 08.06.19 save all configuration in Preference
                if (!configurations.isEmpty()) {
                    updateConf(configurations);
                }
            }

            @Override
            public void onDataNotAvailable() {
                //
            }
        });

        postsRepository.getPosts(new PostsDataSource.LoadPostsCallback() {
            @Override
            public void onPostsLoaded(final List<Post> posts) {
                processPosts(getFilteredPosts(posts));
            }

            @Override
            public void onDataNotAvailable() {
            }
        });
    }

    private void updateConf(Map<String, Object> configurations) {
        Log.i(TAG, "Update configuration " + configurations.size());
        showAD = (Boolean) configurations.get(Constants.SHOW_AD_CONF);
        showWeather = (Boolean) configurations.get(Constants.SHOW_WEATHER_CONF);
        newWeatherCity = (String) configurations.get(Constants.CITY_WEATHER_CONF);

        view.showWeather(showWeather);

        if (!weatherCity.equals(newWeatherCity)) {
            weatherCity = newWeatherCity;
            loadWeather();
        }
    }

    private void processPosts(List<Post> posts) {
        Log.i(TAG, "Update posts " + posts.size());

        this.posts = posts;
        view.setPosts(posts);
    }

    private List<Post> getFilteredPosts(List<Post> allPosts) {
        ArrayList<Post> activePosts = new ArrayList<>();

        for (Post p : allPosts) {
            if (p.isState()) {
                if (!(p.getType().equals(AD) && !showAD)) {
                    activePosts.add(p);
                }
            }
        }

        return activePosts;
    }


    @Override
    public void startShowingPosts() {
        Log.i(TAG, "Start showing Posts");

        updateHandler = new Handler();

        Runnable r = new Runnable() {
            public void run() {
                loadPosts();
                if (updateHandler != null) {
                    updateHandler.postDelayed(this, 60 * 1000);
                }
            }
        };
        updateHandler.post(r);

        view.startDisplayPosts();
    }

    @Override
    public void stopShowingPosts() {
        Log.i(TAG, "Stop showing Posts");
        view.stopDisplayPosts();

        updateHandler = null;
    }

    @Override
    public void loadWeather() {
        weatherRepository.getWeatherByCity(weatherCity, new WeatherCallback());
    }
}
