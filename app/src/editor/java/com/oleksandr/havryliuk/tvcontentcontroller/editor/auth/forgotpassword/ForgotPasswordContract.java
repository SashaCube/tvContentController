package com.oleksandr.havryliuk.tvcontentcontroller.editor.auth.forgotpassword;

import androidx.fragment.app.Fragment;
import android.view.View;

public interface ForgotPasswordContract {

    interface IForgotPasswordView {
        void init(Fragment fragment, View root);

        void hideEmailError();

        void showEmailError();

        String getEmail();

        void setPresenter(IForgotPasswordPresenter presenter);

        void showSignIn();

        void sendRecoverCode(String email);


    }

    interface IForgotPasswordPresenter {
        void showSignInClick();

        void sendRecoverCodeClick();
    }
}
