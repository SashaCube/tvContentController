package com.oleksandr.havryliuk.editor.auth.forgotpassword;

import android.view.View;

public interface ForgotPasswordContract {

    interface IForgotPasswordView{
        void init(View root);

        void hideEmailError();

        void showEmailError();

        String getEmail();

        void setPresenter(IForgotPasswordPresenter presenter);
    }

    interface IForgotPasswordPresenter{
        void showSignIn();

        void sendRecoverCodeClick();
    }
}
