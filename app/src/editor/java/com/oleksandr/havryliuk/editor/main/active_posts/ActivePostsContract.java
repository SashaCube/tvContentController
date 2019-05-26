package com.oleksandr.havryliuk.editor.main.active_posts;

import android.support.v4.app.Fragment;
import android.view.View;

import com.oleksandr.havryliuk.editor.adapters.IPostAdapterPresenter;
import com.oleksandr.havryliuk.editor.data.Post;

import java.util.List;

public interface ActivePostsContract {

    interface IActivePostsView{

        void init(View root, Fragment fragment);

        void setPresenter(IActivePostPresenter presenter);

        void setPosts(List<Post> posts);

        void setLoadingIndicator(boolean value);

        void showNoPosts();

        void showLoadingTasksError();

        void showPostDeleted();

        void showEditScreen(Post post);

        boolean isActive();
    }

    interface IActivePostPresenter extends IPostAdapterPresenter {

        void loadPosts(boolean showLoadingUI);

    }
}
