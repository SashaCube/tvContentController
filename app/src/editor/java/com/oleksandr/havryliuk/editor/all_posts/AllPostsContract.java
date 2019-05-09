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

        void showPostDeleted();

    }

    interface IAllPostsPresenter {

        void loadPosts(boolean showLoadingUI);

        void clickDelete(Post post);

        void clickEdit(Post post);

        void clickSetPost(Post oldPost);

    }
}
