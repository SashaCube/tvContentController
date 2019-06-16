package com.oleksandr.havryliuk.tvcontentcontroller.client.content.view;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.client.content.ContentContract;
import com.oleksandr.havryliuk.tvcontentcontroller.client.content.ContentPresenter;
import com.oleksandr.havryliuk.tvcontentcontroller.client.data.local.room.MyWeather;
import com.oleksandr.havryliuk.tvcontentcontroller.client.utils.Utils;
import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.image_manager.ImageManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.oleksandr.havryliuk.tvcontentcontroller.client.content.view.ContentViewUtils.animation;
import static com.oleksandr.havryliuk.tvcontentcontroller.client.content.view.ContentViewUtils.getFutureWeather;
import static com.oleksandr.havryliuk.tvcontentcontroller.client.content.view.ContentViewUtils.getIconUrl;
import static com.oleksandr.havryliuk.tvcontentcontroller.client.content.view.ContentViewUtils.getPostsWithoutAD;
import static com.oleksandr.havryliuk.tvcontentcontroller.client.utils.Utils.getOnlyDay;
import static com.oleksandr.havryliuk.tvcontentcontroller.client.utils.Utils.getOnlyTime;
import static com.oleksandr.havryliuk.tvcontentcontroller.client.utils.Utils.getUpToDateWeather;
import static com.oleksandr.havryliuk.tvcontentcontroller.client.utils.Utils.isUpToDate;
import static com.oleksandr.havryliuk.tvcontentcontroller.client.utils.Utils.kelvinToCelsius;
import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.AD;
import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.IMAGE;
import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.NEWS;
import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.TEXT;

public class ContentView implements ContentContract.IContentView {

    private final static String TAG = ContentPresenter.class.getName();
    private final static int LOAD = -1;

    private ContentContract.IContentPresenter presenter;
    private Fragment fragment;
    private View root;

    private ViewGroup adPostView, newsPostView, imagePostView, textPostView, weatherPostView;
    private ImageView adPostImage, imagePostImage, newsPostImage, emptyImage;
    private TextView textPostText, adPostText, newsPostText;

    private Thread displayPostThread;
    private int postIndex = LOAD;
    private static volatile List<Post> posts;

    //for AD
    private List<Post> allPosts;
    private boolean showADState;

    //for weather forecast
    private ImageView mainWeatherImage;
    private TextView aboutWeatherText, mainTemperatureText, humidityText, cloudinessText,
            pressureText;

    private List<TextView> timeTextViews, futureTemperatures, futureDays;
    private List<ImageView> futuresImages;
    private List<TemperatureView> temperatureViews;
    private List<MyWeather> weatherList;
    private boolean showWeatherState;

    @Override
    public void init(Fragment fragment, View root) {
        this.fragment = fragment;
        this.root = root;

        initView();
        hideAll();
        animation(emptyImage);
    }

    private void initView() {
        adPostView = root.findViewById(R.id.post_ad);
        newsPostView = root.findViewById(R.id.post_news);
        imagePostView = root.findViewById(R.id.post_image);
        textPostView = root.findViewById(R.id.post_text);
        weatherPostView = root.findViewById(R.id.post_weather_forecast);


        adPostImage = root.findViewById(R.id.ad_post_image);
        newsPostImage = root.findViewById(R.id.news_post_image);
        imagePostImage = root.findViewById(R.id.image_post_image);

        adPostText = root.findViewById(R.id.ad_post_text);
        newsPostText = root.findViewById(R.id.news_post_text);
        textPostText = root.findViewById(R.id.text_post_text);

        emptyImage = root.findViewById(R.id.empty_image_view);

        initWeatherForecastView();
    }

    private void initWeatherForecastView() {
        //main weather
        mainWeatherImage = root.findViewById(R.id.main_weather_image_view);
        aboutWeatherText = root.findViewById(R.id.about_weather_text_view);
        mainTemperatureText = root.findViewById(R.id.temperature_text_view);
        humidityText = root.findViewById(R.id.humidity_text_view);
        cloudinessText = root.findViewById(R.id.cloudiness_text_view);
        pressureText = root.findViewById(R.id.pressure_text_view);

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
            tv.setBottomPartColor(Objects.requireNonNull(fragment.getContext()).getResources().getColor(R.color.light_orange));
            tv.setSeparatorColor(fragment.getContext().getResources().getColor(R.color.orange));
            tv.setTextColor(fragment.getContext().getResources().getColor(R.color.slate_gray));
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

    private void showWeatherForecast() {

        if (weatherList == null || weatherList.isEmpty()) {
            presenter.loadWeather();
            return;
        }

        if(isUpToDate(weatherList.get(0).getTime())) {
            presenter.loadWeather();
            return;
        }

        showMainWeather();
        showMainTemperature();
        showFutureWeather();

        hideAll();
        weatherPostView.setVisibility(View.VISIBLE);
    }

    private void showMainWeather() {
        MyWeather weather = getUpToDateWeather(weatherList, new Date());

        assert weather != null;
        aboutWeatherText.setText(weather.getMain());

        DecimalFormat df = new DecimalFormat("#");
        mainTemperatureText.setText(df.format(Utils.kelvinToCelsius(weather.getTemp())) + "°C");

        humidityText.setText(" " + weather.getHumidity() + "%");
        cloudinessText.setText(" " + weather.getCloudiness() + "%");
        pressureText.setText(" " + weather.getPressure());

        Glide.with(fragment).load(getIconUrl(weather.getIconId())).into(mainWeatherImage);
    }

    private void showMainTemperature() {
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

    private void showFutureWeather() {
        if (weatherList == null || weatherList.isEmpty()) {
            return;
        }
        List<MyWeather> futureWeather = getFutureWeather(weatherList);
        MyWeather weather;

        for (int i = 0; i < 3; i++) {
            weather = futureWeather.get(i);

            Glide.with(fragment)
                    .load(getIconUrl(weather.getIconId()))
                    .into(futuresImages.get(i));

            futureDays.get(i).setText(getOnlyDay(weather.getTime()));

            DecimalFormat df = new DecimalFormat("#.##");
            futureTemperatures.get(i)
                    .setText(df.format(Utils.kelvinToCelsius(weather.getTemp())) + "°C");
        }
    }

    @Override
    public void setWeather(List<MyWeather> weatherList) {
        this.weatherList = weatherList;
    }

    @Override
    public void setWeatherShowingState(boolean showWeather) {
        this.showWeatherState = showWeather;
        Log.i(TAG, (showWeather ? "add" : "remove") + " weather forecast");
    }

    @Override
    public void setADShowingState(boolean showAD) {
        this.showADState = showAD;
        if (showAD) {
            addAD();
        } else {
            removeAD();
        }
    }

    private void removeAD() {
        posts = getPostsWithoutAD(allPosts);
        Log.i(TAG, "remove AD posts");
    }

    private void addAD() {
        posts = allPosts;
        Log.i(TAG, "add AD posts");
    }

    @Override
    public void setPresenter(ContentContract.IContentPresenter presenter) {
        this.presenter = presenter;
    }

    private void showADPost(Post post) {
        ImageManager.loadInto(root.getContext(), post.getImagePath(), adPostImage, null);

        if (post.getText().isEmpty()) {
            adPostText.setVisibility(View.GONE);
        } else {
            adPostText.setVisibility(View.VISIBLE);
            adPostText.setText(post.getText());
        }

        hideAll();
        adPostView.setVisibility(View.VISIBLE);
    }

    private void showNewsPost(Post post) {
        ImageManager.loadInto(root.getContext(), post.getImagePath(), newsPostImage, null);
        newsPostText.setText(post.getText());

        hideAll();
        newsPostView.setVisibility(View.VISIBLE);
    }


    private void showTextPost(Post post) {
        textPostText.setText(post.getText());

        hideAll();
        textPostView.setVisibility(View.VISIBLE);
    }


    private void showImagePost(Post post) {
        ImageManager.loadInto(root.getContext(), post.getImagePath(), imagePostImage, null);

        hideAll();
        imagePostView.setVisibility(View.VISIBLE);
    }

    private void hideAll() {
        adPostView.setVisibility(View.GONE);
        newsPostView.setVisibility(View.GONE);
        imagePostView.setVisibility(View.GONE);
        textPostView.setVisibility(View.GONE);
        weatherPostView.setVisibility(View.GONE);
    }

    @Override
    public boolean isActive() {
        return fragment.isAdded();
    }

    @Override
    public void setPosts(List<Post> posts) {
        this.allPosts = posts;
        if(posts != null) {
            setADShowingState(showADState);

            if (postIndex == LOAD || postIndex >= posts.size()) {
                postIndex = 0;
            }
        }
    }

    private void showPost(Post post) {
        switch (post.getType()) {
            case NEWS:
                showNewsPost(post);
                break;
            case AD:
                showADPost(post);
                break;
            case IMAGE:
                showImagePost(post);
                break;
            case TEXT:
                showTextPost(post);
                break;
        }
    }

    @Override
    public void startDisplayPosts() {
        Log.i(TAG, "start display posts");
        initDisplayPostThread();
        displayPostThread.start();
    }

    @Override
    public void stopDisplayPosts() {
        Log.i(TAG, "stop display posts");
        if (isActive()) {
            displayPostThread.interrupt();
        }
    }

    private void initDisplayPostThread() {
        displayPostThread = new Thread() {
            @Override
            public void run() {
                try {
                    final long[] duration = {2000};
                    while (!isInterrupted()) {
                        if (fragment.getActivity() == null) {
                            return;
                        }

                        Objects.requireNonNull(fragment.getActivity()).runOnUiThread(() -> {
                            duration[0] = displayPost();
                        });

                        Thread.sleep(duration[0]);
                    }
                } catch (InterruptedException e) {

                }
            }
        };
    }

    private long displayPost() {
        if ((posts != null && !posts.isEmpty()) && postIndex != LOAD) {
            Post post;
            if (postIndex < posts.size()) {
                post = posts.get(postIndex);
                postIndex++;
            } else {
                postIndex = 0;

                if (showWeatherState) {
                    showWeatherForecast();
                    return 10 * 1000;
                }

                post = posts.get(postIndex);

            }

            showPost(post);
            Log.i(TAG, "display post : " + post);
            return post.getDuration() * 1000;
        } else {
            showEmptyScreen();
            Log.i(TAG, "show empty screen");
            return 10 * 1000;
        }
    }

    private void showEmptyScreen() {
        hideAll();
        if (showWeatherState) {
            showWeatherForecast();
        }
    }
}
