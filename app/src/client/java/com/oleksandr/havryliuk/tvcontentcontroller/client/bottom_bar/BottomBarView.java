package com.oleksandr.havryliuk.tvcontentcontroller.client.bottom_bar;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.client.weather.room.MyWeather;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

public class BottomBarView implements BottomBarContract.IBottomBarView {

    private View root;
    private Fragment fragment;
    private BottomBarContract.IBottomBarPresenter presenter;
    private Thread time, weather;

    private TextView timeTextView, dateTextView;

    @Override
    public void init(Fragment fragment, View root) {
        this.root = root;
        this.fragment = fragment;

        initView();
    }

    @Override
    public void setPresenter(BottomBarContract.IBottomBarPresenter presenter) {
        this.presenter = presenter;
    }

    private void initView() {
        timeTextView = root.findViewById(R.id.time_text_view);
        dateTextView = root.findViewById(R.id.date_text_view);
    }

    private void initDataTime() {
        time = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        Objects.requireNonNull(fragment.getActivity()).runOnUiThread(() -> {
                            long date = System.currentTimeMillis();
                            SimpleDateFormat sdf;
                            String dateString;

                            sdf = new SimpleDateFormat("HH:mm:ss");
                            dateString = sdf.format(date);
                            timeTextView.setText(dateString);

                            sdf = new SimpleDateFormat("dd.MM");
                            dateString = sdf.format(date);
                            dateTextView.setText(dateString);
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
    }

    @Override
    public void startDisplayDateTime() {
        initDataTime();
        time.start();

        initWeatherThread();
        weather.start();
    }

    @Override
    public void stopDisplayDateTime() {
        if (time.isAlive()) {
            time.interrupt();
        }

        if (weather.isAlive()) {
            weather.interrupt();
        }
    }

    private void initWeatherThread() {
        weather = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(60 * 60 * 1000);
                        Objects.requireNonNull(fragment.getActivity()).runOnUiThread(() -> {
                            List<MyWeather> weatherList = presenter.getWeather();

                            if (weatherList.isEmpty()) {
                                // TODO: 08.06.19 marked weather as not up to date
                            } else {
                                // TODO: 08.06.19 set weather view
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
    }
}
