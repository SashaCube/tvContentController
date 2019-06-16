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

        initView();
    }

    private void initView() {
        cityTextView = root.findViewById(R.id.weather_city_text_view);

        editButton = root.findViewById(R.id.edit_button_image);
        editButton.setOnClickListener(v -> editCity());

        showWeatherSwitch = root.findViewById(R.id.show_weather_switch);
        showWeatherSwitch.setOnCheckedChangeListener((buttonView, isChecked)
                -> presenter.setWeatherShowing(isChecked));

        showAdSwitch = root.findViewById(R.id.show_ad_switch);
        showAdSwitch.setOnCheckedChangeListener((buttonView, isChecked)
                -> presenter.setAdShowing(isChecked));
    }

    @Override
    public void setPresenter(ConfigurationContract.IConfigurationPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showAdConfigurationChange() {
        showMessage(fragment.getString(R.string.ad_configuration_changed));
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
    public void setWeatherCityView(String city) {
        cityTextView.setText(city);
    }

    @Override
    public void setShowingWeatherView(boolean showWeather) {
        if (showWeatherSwitch.isChecked() != showWeather) {
            showWeatherSwitch.toggle();
        }
    }

    @Override
    public void setShowingADView(boolean showAD) {
        if (showAdSwitch.isChecked() != showAD) {
            showAdSwitch.toggle();
        }
    }

    private void showMessage(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show();
    }

    private void editCity() {

        // TODO: 13.06.19 new small window to edit city

        String city = "Bogo";
        presenter.setWeatherCity(city);
        cityTextView.setText(city);
    }
}
