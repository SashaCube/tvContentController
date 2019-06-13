package com.oleksandr.havryliuk.tvcontentcontroller.editor.main.configuration;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.oleksandr.havryliuk.tvcontentcontroller.R;

public class ConfigurationView implements ConfigurationContract.IConfigurationView {

    private Fragment fragment;
    private ConfigurationContract.IConfigurationPresenter presenter;
    private View root;
    private Switch showAdSwitch, showWeatherSwitch;
    private ImageView editButton;
    private TextView cityTextView;

    @Override
    public void init(Fragment fragment, View root) {
        this.fragment = fragment;
        this.root = root;
    }


    @Override
    public void setPresenter(ConfigurationContract.IConfigurationPresenter presenter) {
        this.presenter = presenter;
        presenter.loadConfiguration();
    }

    @Override
    public void initAdConfiguration(boolean showAd) {
        if (showAdSwitch == null) {
            showAdSwitch = root.findViewById(R.id.show_ad_switch);
            showAdSwitch.setChecked(showAd);
            showAdSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> presenter.setAdConfiguration(isChecked));
        }
    }

    @Override
    public void showAdConfigurationChange() {
        showMessage(fragment.getString(R.string.ad_configuration_changed));
    }

    @Override
    public void initWeatherConfiguration(boolean showWeather, String city) {
        if (showWeatherSwitch == null) {
            showWeatherSwitch = root.findViewById(R.id.show_weather_switch);
            showWeatherSwitch.setChecked(showWeather);
            showWeatherSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> presenter.setWeatherConfiguration(isChecked));
        }
        cityTextView = root.findViewById(R.id.weather_city_text_view);
        editButton = root.findViewById(R.id.edit_button_image);

        editButton.setOnClickListener(v -> editCity());
        cityTextView.setText(city);
    }

    @Override
    public void showWeatherConfigurationChange() {
        showMessage(fragment.getString(R.string.weather_configuration_changed));
    }

    @Override
    public void showWeatherCityChange(String city) {
        showMessage(fragment.getString(R.string.weather_city_changed) + " : " + city);
    }

    @Override
    public boolean isActive() {
        return fragment.isAdded();
    }

    private void showMessage(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show();
    }

    private void editCity(){

        // TODO: 13.06.19 new small window to edit city

        String city = "Bogo";
        presenter.setWeatherCity(city);
        cityTextView.setText(city);
    }
}
