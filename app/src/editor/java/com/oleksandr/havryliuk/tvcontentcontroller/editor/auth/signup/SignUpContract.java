package com.oleksandr.havryliuk.tvcontentcontroller.editor.auth.signup;

import android.support.v4.app.Fragment;
import android.view.View;

public interface SignUpContract {

    interface ISignUpView{
        void init(Fragment fragment, View root);

        void setPresenter(ISignUpPresenter presenter);

        void hideLoginError();

        void showLoginError();

        void hideEmailError();

        void showEmailError();

        void showPasswordError();

        void hidePasswordError();

        void showConfirmError();

        void hideConfirmError();

        String getLogin();

        String getPassword();

        String getEmail();

        String getConfirmPassword();

        void showSignIn();

        void signUp(String login, String email, String  password);
    }

    interface ISignUpPresenter{
        void signUpClick();

        void showSignInClick();
    }
}
