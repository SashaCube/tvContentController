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
    private String type;
    private Post editedPost;
    private Uri uri;
    private String path;
    private String result = CANCEL;
    private PostsRepository mRepository;

    EditPostPresenter(EditPostContract.IEditPostView view, PostsRepository mRepository) {
        this.view = view;
        this.mRepository = mRepository;
    }

    @Override
    public void initEditPost(Post post) {
        editedPost = post;
        view.setEditPost(post);
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
        this.path = path;
        view.setImage(path);
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
        if (PostValidator.validateInput((IValidateView) view, title, text, uri, path, type)) {

            editedPost.setTitle(title);
            editedPost.setAbout(about);
            editedPost.setText(text);
            editedPost.setType(type);

            if(path != null){
                editedPost.setImagePath(path);
            }else{
                if(uri != null){
                    editedPost.setImagePath(ActivityUtils.UriPath(uri));
                }
            }

            editedPost.setState(state);
            editedPost.setDuration(duration);

            mRepository.savePost(editedPost);

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
        view.showAllPostsScreen();
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
