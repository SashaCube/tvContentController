package com.oleksandr.havryliuk.tvcontentcontroller.editor.all_posts;

import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsRepositoryObserver;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.adapters.IPostAdapterPresenter;

import java.util.List;

public interface AllPostsContract {

    interface IAllPostsView {

        void setPosts(List<Post> posts);

        void showNoPosts();

        String getType();


        void showPostDeleted();

        void showEditScreen(Post post);

        boolean isActive();
    }

    interface IAllPostsPresenter extends IPostAdapterPresenter, PostsRepositoryObserver {

        void start();

        void stop();
    }
}
