package com.oleksandr.havryliuk.editor.all_posts;

import android.support.v4.app.FragmentManager;
import android.view.View;

public interface AllPostsContract {

    interface IAllPostsView{

        void init(View root, FragmentManager fragmentManager);

        void setPresenter(IAllPostsPresenter presenter);

    }

    interface IAllPostsPresenter{

    }
}
