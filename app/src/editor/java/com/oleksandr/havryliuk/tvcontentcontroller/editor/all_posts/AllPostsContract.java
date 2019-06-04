package com.oleksandr.havryliuk.tvcontentcontroller.editor.all_posts;

import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.adapters.IPostAdapterPresenter;

import java.util.List;

public interface AllPostsContract {

    interface IAllPostsView {

        void setPosts(List<Post> posts);

        void setLoadingIndicator(boolean value);

        void showNoPosts();

        String getType();

        void showLoadingTasksError();

        void showPostDeleted();

        void showEditScreen(Post post);

        boolean isActive();
    }

    interface IAllPostsPresenter extends IPostAdapterPresenter {

        void loadPosts(boolean showLoadingUI);

    }
}
