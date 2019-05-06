package com.oleksandr.havryliuk.editor.all_posts;

import android.view.View;

import com.oleksandr.havryliuk.editor.data.Post;

import java.util.List;

public interface AllPostsContract {

    interface IAllPostsView {

        void init(View root, String type);

        void setPresenter(IAllPostsPresenter mPresenter);

        void setPosts(List<Post> posts);

        void setLoadingIndicator(boolean value);

        void showNoPosts(int string);

        String getType();

        void showLoadingTasksError();

    }

    interface IAllPostsPresenter {

        void loadPosts(boolean forceUpdate, boolean showLoadingUI);

        void loadPosts(boolean forceUpdate);

        void clickDelete(Post post);

        void clickEdit(Post post);

        void clickSetPost(Post oldPost);

        void setSorting(String type);

        void start();
    }
}
