package com.oleksandr.havryliuk.editor.auth.signin;

import android.text.TextUtils;

import com.oleksandr.havryliuk.editor.auth.AuthenticationActivity;
import com.oleksandr.havryliuk.editor.auth.forgotpassword.ForgotPasswordFragment;
import com.oleksandr.havryliuk.editor.auth.signup.SignUpFragment;

public class SignInPresenter implements SignInContract.ISignInPresenter {

    private SignInContract.ISignInView view;
    private SignInFragment fragment;

    public SignInPresenter(SignInContract.ISignInView view, SignInFragment fragment) {
        this.view = view;
        this.fragment = fragment;
    }

    @Override
    public void signInClick() {
        validateInput();
    }


    @Override
    public void googleSignInClick() {
        ((AuthenticationActivity) fragment.getActivity()).googleSignIn();
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

        ((AuthenticationActivity) fragment.getActivity()).signIn(login, password);
    }

    @Override
    public void showSignUp() {
        ((AuthenticationActivity) fragment.getActivity()).showFragment(new SignUpFragment(),
                SignUpFragment.class.getName());
    }

    @Override
    public void showForgotPassword() {
        ((AuthenticationActivity) fragment.getActivity()).showFragment(new ForgotPasswordFragment(),
                ForgotPasswordFragment.class.getName());
    }
}