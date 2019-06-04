package com.oleksandr.havryliuk.tvcontentcontroller.editor.main.configuration;

import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsDataSource;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsRepository;

import java.util.Map;

import static com.oleksandr.havryliuk.tvcontentcontroller.utils.Constants.SHOW_AD_CONF;

public class ConfigurationPresenter implements ConfigurationContract.IConfigurationPresenter {
    private ConfigurationContract.IConfigurationView view;
    private PostsRepository mRepository;
    private boolean show_ad;

    public ConfigurationPresenter(final ConfigurationContract.IConfigurationView view, PostsRepository postsRepository) {
        this.view = view;
        mRepository = postsRepository;

        mRepository.getConf(new PostsDataSource.LoadConfCallback() {
            @Override
            public void onConfigLoaded(Map<String, Boolean> configurations) {
                if (!configurations.isEmpty()) {
                    Boolean value = configurations.get(SHOW_AD_CONF);
                    if (value != null) {
                        show_ad = value;
                    }
                } else {
                    mRepository.saveConf(SHOW_AD_CONF, true);

                    if (view.isActive()) {
                        view.initAdConfiguration(true);
                    }
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }


    @Override
    public void setAdConfiguration(boolean showAd) {
        mRepository.saveConf(SHOW_AD_CONF, showAd);
        view.showAdConfigurationChange();
    }

    public void loadConfiguration() {
        view.initAdConfiguration(show_ad);
    }
}
