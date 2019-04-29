package com.oleksandr.havryliuk.editor.edit_post;

import android.net.Uri;
import android.text.TextUtils;

import com.oleksandr.havryliuk.editor.MainActivity;
import com.oleksandr.havryliuk.editor.model.Post;
import com.oleksandr.havryliuk.editor.repository.Repository;

import static com.oleksandr.havryliuk.editor.model.Post.IMAGE;
import static com.oleksandr.havryliuk.editor.model.Post.TEXT;

public class EditPostPresenter implements EditPostContract.IEditPostPresenter, MainActivity.IImagePicker {

    public final static String SAVE = "Save";
    public final static String CANCEL = "Cancel";

    private EditPostContract.IEditPostView view;
    private EditPostFragment fragment;
    private String type;
    private Post editedPost;
    private Uri uri;
    private String result = CANCEL;

    public EditPostPresenter(EditPostContract.IEditPostView view, EditPostFragment fragment) {
        this.view = view;
        this.fragment = fragment;
    }

    @Override
    public void initEditPost(Post post) {
        editedPost = post;
        view.setEditPost(post);
    }

    @Override
    public void setImageClick() {
        ((MainActivity) fragment.getActivity()).pickImageFromGallery(this);
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
    public void saveClick(String title, String about, String text, int duration, boolean state) {
        if (validateInput(title, text) == false) {
            return;
        }

        editedPost.setTitle(title);
        editedPost.setAbout(about);
        editedPost.setText(text);
        editedPost.setType(type);
        editedPost.setImageUri(uri);
        editedPost.setState(state);
        editedPost.setDuration(duration);

        Repository.getInstance().setPost(editedPost);
        result = SAVE;
        ((MainActivity) fragment.getActivity()).openAllPostsFragment();
    }

    @Override
    public void cancelClick() {
        ((MainActivity) fragment.getActivity()).openAllPostsFragment();
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

    public String getResult() {
        return result;
    }
}
