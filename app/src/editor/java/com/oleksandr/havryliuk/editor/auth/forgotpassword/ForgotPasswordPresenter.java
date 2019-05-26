package com.oleksandr.havryliuk.editor.auth.forgotpassword;

import android.text.TextUtils;

public class ForgotPasswordPresenter implements ForgotPasswordContract.IForgotPasswordPresenter {

    private ForgotPasswordContract.IForgotPasswordView view;

    public ForgotPasswordPresenter(ForgotPasswordContract.IForgotPasswordView view) {
        this.view = view;
    }

    @Override
    public void sendRecoverCodeClick() {
        validateInput();
    }

    @Override
    public void showSignInClick() {
        view.showSignIn();
    }

    private void validateInput() {
        view.hideEmailError();

        String email = view.getEmail();

        if (TextUtils.isEmpty(email)) {
            view.showEmailError();
            return;
        }

        view.sendRecoverCode(view.getEmail());
    }
}
