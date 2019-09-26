package com.oleksandr.havryliuk.tvcontentcontroller.editor.new_edit_post.new_post;

import android.net.Uri;
import androidx.fragment.app.Fragment;
import android.view.View;

import com.oleksandr.havryliuk.tvcontentcontroller.editor.MainActivity;

public interface NewPostContract {

    interface INewPostView {

        void setPresenter(INewPostPresenter presenter);

        void init(Fragment fragment, View root);

        void setImage(Uri uri);

        void hideAddImageLayout();

        void hideAddTextLayout();

        void showAddImageLayout();

        void showAddTextLayout();

        void showImagePicker(MainActivity.IImagePicker imagePicker);

        void showAllPostsScreen();

        void showPostAdded();
    }

    interface INewPostPresenter {

        void setImageClick();

        void setTypeClick(String type);

        void doneClick(String title, String about, String text, int duration, boolean state);
    }
}
