package com.oleksandr.havryliuk.tvcontentcontroller.editor.new_edit_post.edit_post;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.view.View;

import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.MainActivity;

public interface EditPostContract {

    interface IEditPostView {

        void setPresenter(IEditPostPresenter presenter);

        void init(Fragment fragment, View root);

        void setImage(Uri uri);

        void setImage(String path);

        void setEditPost(Post post);

        void hideAddImageLayout();

        void hideAddTextLayout();

        void showAddImageLayout();

        void showAddTextLayout();

        void showImagePicker(MainActivity.IImagePicker imagePicker);

        void showAllPostsScreen();
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
