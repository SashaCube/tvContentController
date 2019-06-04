package com.oleksandr.havryliuk.tvcontentcontroller.editor.new_edit_post.validator;

import android.net.Uri;
import android.text.TextUtils;

import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.AD;
import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.IMAGE;
import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.TEXT;

public class PostValidator {

    private final static boolean VALID = true;
    private final static boolean INVALID = false;

    public static boolean validateInput(IValidateView view, String title, String text, Uri uri, String path, String type) {
        hideAllError(view);
        boolean validate = validateTitle(view, title);

        if (validate) {
            switch (type) {
                case IMAGE:
                    validate = validateImage(view, uri, path);
                    break;
                case TEXT:
                    validate = validateText(view, text);
                    break;
                case AD:
                    validate = validateImage(view, uri, path);
                    break;
                default:
                    validate = validateImage(view, uri, path);
                    if (validate) {
                        validate = validateText(view, text);
                    }
            }
        }

        return validate;
    }

    private static boolean validateImage(IValidateView view, Uri uri, String path) {
        if (uri == null && path == null) {
            view.showImageError();
            return INVALID;
        }
        return VALID;
    }

    private static boolean validateText(IValidateView view, String text) {
        if (TextUtils.isEmpty(text)) {
            view.showTextError();
            return INVALID;
        }
        return VALID;
    }

    private static boolean validateTitle(IValidateView view, String title) {
        if (TextUtils.isEmpty(title)) {
            view.showTitleError();
            return INVALID;
        }
        return VALID;
    }

    private static void hideAllError(IValidateView view) {
        view.hideTextError();
        view.hideTitleError();
        view.hideImageError();
    }
}
