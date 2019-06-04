package com.oleksandr.havryliuk.tvcontentcontroller.editor.auth.signup;

import android.text.TextUtils;

public class SignUpPresenter implements SignUpContract.ISignUpPresenter {

    private SignUpContract.ISignUpView view;

    public SignUpPresenter(SignUpContract.ISignUpView view) {
        this.view = view;
    }

    @Override
    public void signUpClick() {
        validateInput();
    }

    @Override
    public void showSignInClick() {
        view.showSignIn();
    }

    private void validateInput() {

        view.hideLoginError();
        view.hideEmailError();
        view.hidePasswordError();
        view.hideConfirmError();

        String login = view.getLogin();
        String email = view.getEmail();
        String password = view.getPassword();
        String confirmPassword = view.getConfirmPassword();

        if (TextUtils.isEmpty(login)) {
            view.showLoginError();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            view.showEmailError();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            view.showPasswordError();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            view.showConfirmError();
            return;
        }

        if (!password.equals(confirmPassword)) {
            view.showConfirmError();
            return;
        }


        view.signUp(login, email, password);

    }
}
