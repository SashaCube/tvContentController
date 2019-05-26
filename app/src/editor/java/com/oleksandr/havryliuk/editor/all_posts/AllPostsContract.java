package com.oleksandr.havryliuk.editor.all_posts;

import com.oleksandr.havryliuk.editor.data.Post;

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

    interface IAllPostsPresenter {

        void loadPosts(boolean showLoadingUI);

        void clickDelete(Post post);

        void clickEdit(Post post);

        void clickSetPost(Post oldPost);

    }
}
