package com.oleksandr.havryliuk.editor.new_edit_post.new_post;

import android.net.Uri;
import android.widget.Toast;

import com.oleksandr.havryliuk.editor.MainActivity;
import com.oleksandr.havryliuk.editor.data.Post;
import com.oleksandr.havryliuk.editor.data.source.PostsRepository;
import com.oleksandr.havryliuk.editor.data.source.image_manager.ImageManager;
import com.oleksandr.havryliuk.editor.new_edit_post.validator.IValidateView;
import com.oleksandr.havryliuk.editor.new_edit_post.validator.PostValidator;
import com.oleksandr.havryliuk.tvcontentcontroller.utils.ActivityUtils;

import java.util.Date;
import java.util.Objects;

import static com.oleksandr.havryliuk.editor.data.Post.IMAGE;
import static com.oleksandr.havryliuk.editor.data.Post.TEXT;

public class NewPostPresenter implements NewPostContract.INewPostPresenter,
        MainActivity.IImagePicker {

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
    public void doneClick(String title, String about, String text, int duration, boolean state) {
        if (PostValidator.validateInput((IValidateView) view, title, text, uri, type)) {

            Post post = new Post(title, about, ActivityUtils.dateTimeConverter(new Date()),
                    type, ActivityUtils.UriPath(uri), text, state, duration);

            if (post.getType() != TEXT && post.getImagePath() != null) {
                post.setImagePath(ImageManager.uploadImage(uri));
            }

            PostsRepository.getInstance(Objects.requireNonNull(fragment.getContext())).savePost(post);
            Toast.makeText(fragment.getContext(), "Post " + title + " added", Toast.LENGTH_SHORT)
                    .show();

            finish();
        }
    }

    private void finish() {
        ((MainActivity) Objects.requireNonNull(fragment.getActivity())).openMainFragment();
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
