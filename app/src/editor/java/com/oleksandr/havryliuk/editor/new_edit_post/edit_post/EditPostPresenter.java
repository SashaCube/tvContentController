package com.oleksandr.havryliuk.editor.new_edit_post.edit_post;

import android.net.Uri;

import com.oleksandr.havryliuk.editor.MainActivity;
import com.oleksandr.havryliuk.editor.data.Post;
import com.oleksandr.havryliuk.editor.data.source.PostsRepository;
import com.oleksandr.havryliuk.editor.new_edit_post.validator.IValidateView;
import com.oleksandr.havryliuk.editor.new_edit_post.validator.PostValidator;
import com.oleksandr.havryliuk.tvcontentcontroller.utils.ActivityUtils;

import java.util.Objects;

import static com.oleksandr.havryliuk.editor.data.Post.IMAGE;
import static com.oleksandr.havryliuk.editor.data.Post.TEXT;

public class EditPostPresenter implements EditPostContract.IEditPostPresenter, MainActivity.IImagePicker {

    final static String SAVE = "Save";
    final static String CANCEL = "Cancel";

    private EditPostContract.IEditPostView view;
    private EditPostFragment fragment;
    private String type;
    private Post editedPost;
    private Uri uri;
    private String result = CANCEL;

    EditPostPresenter(EditPostContract.IEditPostView view, EditPostFragment fragment) {
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
        ((MainActivity) Objects.requireNonNull(fragment.getActivity()))
                .pickImageFromGallery(this);
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
                showImageType();
                break;
            case TEXT:
                showTextType();
                break;
            default:
                showDefault();
        }
    }

    @Override
    public void saveClick(String title, String about, String text, int duration, boolean state) {
        if (PostValidator.validateInput((IValidateView) view, title, text, uri, type)) {

            editedPost.setTitle(title);
            editedPost.setAbout(about);
            editedPost.setText(text);
            editedPost.setType(type);
            editedPost.setImagePath(ActivityUtils.UriPath(uri));
            editedPost.setState(state);
            editedPost.setDuration(duration);

            PostsRepository.getInstance(Objects.requireNonNull(fragment.getContext())).savePost(editedPost);

            finishWithResult(SAVE);
        }
    }

    @Override
    public void cancelClick() {
        finishWithResult(CANCEL);
    }

    public String getResult() {
        return result;
    }

    private void finishWithResult(String result) {
        this.result = result;
        ((MainActivity) Objects.requireNonNull(fragment.getActivity())).openAllPostsFragment();
    }

    private void showImageType() {
        view.hideAddTextLayout();
        view.showAddImageLayout();
    }

    private void showTextType() {
        view.hideAddImageLayout();
        view.showAddTextLayout();
    }

    private void showDefault() {
        view.showAddTextLayout();
        view.showAddImageLayout();
    }
}
