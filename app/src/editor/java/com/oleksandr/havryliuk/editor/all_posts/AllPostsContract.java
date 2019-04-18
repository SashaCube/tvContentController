package com.oleksandr.havryliuk.editor.all_posts;

import android.support.v4.app.FragmentManager;
import android.view.View;

import com.oleksandr.havryliuk.editor.model.Post;

import java.util.List;

public interface AllPostsContract {

    interface IAllPostsView {

        void init(View root);

        void setPresenter(IAllPostsPresenter presenter, FragmentManager fragmentManager);

    }

    interface IAllPostsPresenter {

        void clickEditButton(Post post);

        List<Post> getPosts(String type);
    }
}
