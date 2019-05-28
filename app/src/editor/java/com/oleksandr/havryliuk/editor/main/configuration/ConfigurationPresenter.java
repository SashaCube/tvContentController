package com.oleksandr.havryliuk.editor.main.configuration;

import com.oleksandr.havryliuk.editor.data.source.PostsDataSource;
import com.oleksandr.havryliuk.editor.data.source.PostsRepository;

import java.util.Map;

public class ConfigurationPresenter implements ConfigurationContract.IConfigurationPresenter {
    public final static String show_ad_conf = "show_ad";

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
                    Boolean value = configurations.get(show_ad_conf);
                    if (value != null) {
                        show_ad = value;
                    }
                } else {
                    mRepository.saveConf(show_ad_conf, true);

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
        mRepository.saveConf(show_ad_conf, showAd);
        view.showAdConfigurationChange();
    }

    public void loadConfiguration() {
        view.initAdConfiguration(show_ad);
    }
}
