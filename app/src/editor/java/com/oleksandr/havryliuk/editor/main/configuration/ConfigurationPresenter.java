package com.oleksandr.havryliuk.editor.main.configuration;

import com.oleksandr.havryliuk.editor.data.source.PostsRepository;

public class ConfigurationPresenter implements ConfigurationContract.IConfigurationPresenter {
    private ConfigurationContract.IConfigurationView view;
    private PostsRepository mRepository;

    public ConfigurationPresenter(ConfigurationContract.IConfigurationView view, PostsRepository postsRepository) {
        this.view = view;
        mRepository = postsRepository;
        loadConfiguration();
    }

    @Override
    public void setAdConfiguration(boolean showAd) {
        // TODO: 26.05.19 set Configuration at repository
        view.showAdConfigurationChange();
    }

    private void loadConfiguration(){
        // TODO: 26.05.19 get Configuration from repository
        view.initAdConfiguration(true); //for test
    }
}
