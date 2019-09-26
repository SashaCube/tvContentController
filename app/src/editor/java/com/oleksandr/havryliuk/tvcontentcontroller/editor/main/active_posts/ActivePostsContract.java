package com.oleksandr.havryliuk.tvcontentcontroller.editor.main.active_posts;

import androidx.fragment.app.Fragment;
import android.view.View;

import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsRepositoryObserver;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.adapters.IPostAdapterPresenter;

import java.util.List;

public interface ActivePostsContract {

    interface IActivePostsView {

        void init(View root, Fragment fragment);

        void setPresenter(IActivePostPresenter presenter);

        void setPosts(List<Post> posts);

        void showNoPosts();

        void showPostDeleted();

        void showEditScreen(Post post);

        boolean isActive();
    }

    interface IActivePostPresenter extends IPostAdapterPresenter, PostsRepositoryObserver {

        void start();

        void stop();

    }
}
