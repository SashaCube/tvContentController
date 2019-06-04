package com.oleksandr.havryliuk.tvcontentcontroller.editor.new_edit_post.validator;

public interface IValidateView {

    void showTitleError();

    void showImageError();

    void showTextError();

    void hideTitleError();

    void hideImageError();

    void hideTextError();
}
