package com.oleksandr.havryliuk.editor.new_edit_post.edit_post;

import android.net.Uri;
import android.view.View;

import com.oleksandr.havryliuk.editor.data.Post;

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
