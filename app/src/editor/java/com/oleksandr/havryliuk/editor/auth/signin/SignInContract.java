package com.oleksandr.havryliuk.editor.auth.signin;

import android.view.View;

public interface SignInContract {

    interface ISignInView {
        void init(View root);

        void setPresenter(ISignInPresenter presenter);

        void hideLoginError();

        void hidePasswordError();

        void showLoginError();

        void showPasswordError();

        String getLoginText();

        String getPasswordText();
    }

    interface ISignInPresenter {
        void signInClick();

        void googleSignInClick();

        void showSignUp();

        void showForgotPassword();
    }
}
