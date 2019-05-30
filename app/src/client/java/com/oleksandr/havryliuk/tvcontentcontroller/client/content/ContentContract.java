package com.oleksandr.havryliuk.tvcontentcontroller.client.content;

import android.support.v4.app.Fragment;
import android.view.View;

import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;

public interface ContentContract {

    interface IContentView {

        void init(Fragment fragment, View root);

        void setPresenter(IContentPresenter presenter);

        void showADPost(Post post);

        void showNewsPost(Post post);

        void showTextPost(Post post);

        void showImagePost(Post post);

        boolean isActive();

        void setLoadingIndicator(boolean value);

        void showLoadingTasksError();
    }

    interface IContentPresenter {

        void startShowingPosts();

        void stopShowingPosts();

        void loadPosts();
    }
}
