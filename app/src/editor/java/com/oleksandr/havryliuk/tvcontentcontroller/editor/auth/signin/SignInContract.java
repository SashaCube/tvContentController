package com.oleksandr.havryliuk.tvcontentcontroller.editor.auth.signin;

import android.support.v4.app.Fragment;
import android.view.View;

public interface SignInContract {

    interface ISignInView {
        void init(Fragment fragment, View root);

        void setPresenter(ISignInPresenter presenter);

        void hideLoginError();

        void hidePasswordError();

        void showLoginError();

        void showPasswordError();

        String getLoginText();

        String getPasswordText();

        void signIn(String login, String password);

        void googleSignIn();

        void showSignUp();

        void showForgotPassword();
    }

    interface ISignInPresenter {
        void signInClick();

        void googleSignInClick();

        void showSignUpClick();

        void showForgotPasswordClick();
    }
}
