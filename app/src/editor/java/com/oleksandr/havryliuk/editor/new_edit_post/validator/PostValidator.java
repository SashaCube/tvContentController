package com.oleksandr.havryliuk.editor.new_edit_post.validator;

import android.net.Uri;
import android.text.TextUtils;

import static com.oleksandr.havryliuk.editor.data.Post.IMAGE;
import static com.oleksandr.havryliuk.editor.data.Post.TEXT;

public class PostValidator {

    private final static boolean VALID = true;
    private final static boolean INVALID = false;

    public static boolean validateInput(IValidateView view, String title, String text, Uri uri, String type) {
        hideAllError(view);
        boolean validate = validateTitle(view, title);

        if (validate) {
            switch (type) {
                case IMAGE:
                    validate = validateImage(view, uri);
                    break;
                case TEXT:
                    validate = validateText(view, text);
                    break;
                default:
                    validate = validateImage(view, uri);
                    if (validate) {
                        validate = validateText(view, text);
                    }
            }
        }

        return validate;
    }

    private static boolean validateImage(IValidateView view, Uri uri) {
        if (uri == null) {
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
