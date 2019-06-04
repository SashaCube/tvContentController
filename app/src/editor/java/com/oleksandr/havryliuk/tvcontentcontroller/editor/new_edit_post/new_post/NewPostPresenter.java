package com.oleksandr.havryliuk.tvcontentcontroller.editor.new_edit_post.new_post;

import android.net.Uri;

import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsRepository;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.image_manager.ImageManager;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.MainActivity;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.new_edit_post.validator.IValidateView;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.new_edit_post.validator.PostValidator;
import com.oleksandr.havryliuk.tvcontentcontroller.utils.ActivityUtils;

import java.util.Date;

import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.IMAGE;
import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.TEXT;

public class NewPostPresenter implements NewPostContract.INewPostPresenter,
        MainActivity.IImagePicker {

    private NewPostContract.INewPostView view;
    private String type;
    private Uri uri;
    private PostsRepository repository;

    public NewPostPresenter(NewPostContract.INewPostView view, PostsRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void setImageClick() {
        view.showImagePicker(this);
    }

    @Override
    public void setUri(Uri uri) {
        this.uri = uri;
        view.setImage(uri);
    }

    @Override
    public void setPath(String path) {

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
        if (PostValidator.validateInput((IValidateView) view, title, text, uri, null, type)) {

            Post post = new Post(title, about, ActivityUtils.dateTimeConverter(new Date()),
                    type, ActivityUtils.UriPath(uri), text, state, duration);

            if (!post.getType().equals(TEXT) && post.getImagePath() != null) {
                post.setImagePath(ImageManager.uploadImage(uri));
            }

            repository.savePost(post);
            view.showPostAdded();

            finish();
        }
    }

    private void finish() {
        view.showMainPostsScreen();
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
