package com.oleksandr.havryliuk.editor.main.configuration;

import android.support.v4.app.Fragment;
import android.view.View;

public interface ConfigurationContract {

    interface IConfigurationView{

        void init(Fragment fragment, View root);

        void setPresenter(IConfigurationPresenter presenter);

        void initAdConfiguration(boolean showAd);

        void showAdConfigurationChange();
    }

    interface IConfigurationPresenter{

        void setAdConfiguration(boolean showAd);

    }
}
