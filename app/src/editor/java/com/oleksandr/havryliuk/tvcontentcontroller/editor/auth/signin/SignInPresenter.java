package com.oleksandr.havryliuk.tvcontentcontroller.editor.auth.signin;

import android.text.TextUtils;

public class SignInPresenter implements SignInContract.ISignInPresenter {

    private SignInContract.ISignInView view;

    public SignInPresenter(SignInContract.ISignInView view) {
        this.view = view;
    }

    @Override
    public void signInClick() {
        validateInput();
    }


    @Override
    public void googleSignInClick() {
        view.googleSignIn();
    }

    private void validateInput() {
        view.hideLoginError();
        view.hidePasswordError();

        String login = view.getLoginText();
        String password = view.getPasswordText();
        if (TextUtils.isEmpty(login)) {
            view.showLoginError();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            view.showPasswordError();
            return;
        }

        view.signIn(login, password);
    }

    @Override
    public void showSignUpClick() {
        view.showSignUp();
    }

    @Override
    public void showForgotPasswordClick() {
        view.showForgotPassword();
    }
}