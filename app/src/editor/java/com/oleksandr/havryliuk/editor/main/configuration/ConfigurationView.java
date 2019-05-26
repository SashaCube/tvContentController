package com.oleksandr.havryliuk.editor.main.configuration;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.oleksandr.havryliuk.tvcontentcontroller.R;

public class ConfigurationView implements ConfigurationContract.IConfigurationView {

    private Fragment fragment;
    private ConfigurationContract.IConfigurationPresenter presenter;
    private View root;
    private Switch showAdSwitch;

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
        if(showAdSwitch == null){
            showAdSwitch = root.findViewById(R.id.show_ad_switch);
            showAdSwitch.setChecked(showAd);
            showAdSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    presenter.setAdConfiguration(isChecked);
                }
            });
        }
    }

    @Override
    public void showAdConfigurationChange() {
        showMessage(fragment.getString(R.string.ad_configuration_changed));
    }

    @Override
    public boolean isActive() {
       return fragment.isAdded();
    }

    private void showMessage(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show();
    }
}
