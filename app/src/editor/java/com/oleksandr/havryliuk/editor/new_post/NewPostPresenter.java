package com.oleksandr.havryliuk.editor.new_post;

import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.oleksandr.havryliuk.editor.MainActivity;
import com.oleksandr.havryliuk.editor.model.Post;
import com.oleksandr.havryliuk.editor.repository.Repository;

import java.util.Date;

import static com.oleksandr.havryliuk.editor.model.Post.IMAGE;
import static com.oleksandr.havryliuk.editor.model.Post.TEXT;

public class NewPostPresenter implements NewPostContract.INewPostPresenter {

    private NewPostContract.INewPostView view;
    private NewPostFragment fragment;
    private String type;
    private Uri uri;

    public NewPostPresenter(NewPostContract.INewPostView view, NewPostFragment fragment) {
        this.view = view;
        this.fragment = fragment;
    }

    @Override
    public void setImageClick() {
        fragment.pickImageFromGallery();
    }

    @Override
    public void setUri(Uri uri) {
        this.uri = uri;
        view.setImage(uri);
    }

    @Override
    public void setTypeClick(String type) {
        this.type = type;

        switch (type) {
            case IMAGE:
                view.hideAddTextLayout();
                view.showAddImageLayout();
                break;
            case TEXT:
                view.hideAddImageLayout();
                view.showAddTextLayout();
                break;
            default:
                view.showAddTextLayout();
                view.showAddImageLayout();
        }
    }

    @Override
    public void doneClick(String title, String about, String text, int duration, boolean state) {
        if (validateInput(title, text) == false) {
            return;
        }

        Post post = new Post(title, about, new Date(), type, uri, text, state, duration);
        Repository.getInstance().addPost(post);
        Toast.makeText(fragment.getContext(), "Post " + title + " added", Toast.LENGTH_SHORT)
                .show();

        ((MainActivity) fragment.getActivity()).openMainFragment();
    }

    private void hideAllError() {
        view.hideTextError();
        view.hideTitleError();
        view.hideImageError();
    }

    private boolean validateInput(String title, String text) {
        hideAllError();
        if (TextUtils.isEmpty(title)) {
            view.showTitleError();
            return false;
        }

        switch (type) {
            case IMAGE:
                if (uri == null) {
                    view.showImageError();
                    return false;
                }
                break;
            case TEXT:
                if (TextUtils.isEmpty(text)) {
                    view.showTextError();
                    return false;
                }
                break;
            default:
                if (uri == null) {
                    view.showImageError();
                    return false;
                }
                if (TextUtils.isEmpty(text)) {
                    view.showTextError();
                    return false;
                }
        }
        return true;
    }
}
