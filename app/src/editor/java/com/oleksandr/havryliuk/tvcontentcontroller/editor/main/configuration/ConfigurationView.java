package com.oleksandr.havryliuk.tvcontentcontroller.editor.main.configuration;

import androidx.fragment.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.utils.ActivityUtils;

import java.util.Objects;

public class ConfigurationView implements ConfigurationContract.IConfigurationView, View.OnClickListener {

    private Fragment fragment;
    private ConfigurationContract.IConfigurationPresenter presenter;
    private View root;
    private Switch showAdSwitch, showWeatherSwitch;
    private ImageView editButton;
    private TextView cityTextView;

    private ViewGroup editCityLayout;
    private TextView saveButton, cancelButton;
    private EditText editCityEditText;

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

        initEditCityLayout();
    }

    private void initEditCityLayout() {
        editCityLayout = root.findViewById(R.id.city_edit_layout);
        editCityEditText = root.findViewById(R.id.input_city_edit_text);
        saveButton = root.findViewById(R.id.save_button);
        cancelButton = root.findViewById(R.id.cancel_button);

        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void setPresenter(ConfigurationContract.IConfigurationPresenter presenter) {
        this.presenter = presenter;
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

    private void editCity() {
        showEditCityLayout();
        editCityEditText.setText(cityTextView.getText());
    }

    private void showEditCityLayout() {
        editCityLayout.setVisibility(View.VISIBLE);
        showAdSwitch.setClickable(false);
        showWeatherSwitch.setClickable(false);
    }

    private void hideEditCityLayout() {
        ActivityUtils.hideKeyboard(Objects.requireNonNull(fragment.getActivity()));
        editCityLayout.setVisibility(View.GONE);
        showAdSwitch.setClickable(true);
        showWeatherSwitch.setClickable(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_button:
                presenter.setWeatherCity(editCityEditText.getText().toString());
                cityTextView.setText(editCityEditText.getText().toString());
                hideEditCityLayout();
                break;
            case R.id.cancel_button:
                hideEditCityLayout();
                break;
        }
    }
}
