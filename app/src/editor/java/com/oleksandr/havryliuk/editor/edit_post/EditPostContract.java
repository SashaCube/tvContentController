package com.oleksandr.havryliuk.editor.edit_post;

import android.net.Uri;
import android.view.View;

import com.oleksandr.havryliuk.editor.model.Post;

public interface EditPostContract {

    interface IEditPostView {

        void setPresenter(IEditPostPresenter presenter);

        void init(View root);

        void setImage(Uri uri);

        void setEditPost(Post post);

        void hideAddImageLayout();

        void hideAddTextLayout();

        void showAddImageLayout();

        void showAddTextLayout();

        void showTitleError();

        void showImageError();

        void showTextError();

        void hideTitleError();

        void hideImageError();

        void hideTextError();
    }

    interface IEditPostPresenter {

        void initEditPost(Post post);

        void setImageClick();

        void setTypeClick(String type);

        void saveClick(String title, String about, String text, int duration, boolean state);

        void cancelClick();

        String getResult();
    }
}
