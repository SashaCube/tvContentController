package com.oleksandr.havryliuk.editor.all_posts;

import android.support.v4.app.FragmentManager;
import android.view.View;

import com.oleksandr.havryliuk.editor.model.Post;

import java.util.List;

public interface AllPostsContract {

    interface IAllPostsView {

        void init(View root);

        void setPresenter(IAllPostsPresenter presenter, FragmentManager fragmentManager);

        void updatePosts();

    }

    interface IAllPostsPresenter {

        List<Post> getPosts(String type);

        void clickDelete(Post post);

        void clickEdit(Post post);

        void clickSetPost(Post oldPost);

        void setSorting(String type);
    }
}
