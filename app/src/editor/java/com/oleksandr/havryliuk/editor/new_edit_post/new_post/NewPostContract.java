package com.oleksandr.havryliuk.editor.new_edit_post.new_post;

import android.net.Uri;
import android.view.View;

public interface NewPostContract {

    interface INewPostView {

        void setPresenter(INewPostPresenter presenter);

        void init(View root);

        void setImage(Uri uri);

        void hideAddImageLayout();

        void hideAddTextLayout();

        void showAddImageLayout();

        void showAddTextLayout();
    }

    interface INewPostPresenter {

        void setImageClick();

        void setTypeClick(String type);

        void doneClick(String title, String about, String text, int duration, boolean state);
    }
}
