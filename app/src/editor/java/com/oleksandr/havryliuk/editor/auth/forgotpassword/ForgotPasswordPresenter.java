package com.oleksandr.havryliuk.editor.auth.forgotpassword;

import android.text.TextUtils;

import com.oleksandr.havryliuk.editor.auth.AuthenticationActivity;
import com.oleksandr.havryliuk.editor.auth.signin.SignInFragment;

public class ForgotPasswordPresenter implements ForgotPasswordContract.IForgotPasswordPresenter {

    private ForgotPasswordFragment fragment;
    private ForgotPasswordContract.IForgotPasswordView view;

    public ForgotPasswordPresenter(ForgotPasswordFragment fragment, ForgotPasswordContract.IForgotPasswordView view) {
        this.fragment = fragment;
        this.view = view;
    }

    @Override
    public void sendRecoverCodeClick() {
        validateInput();
    }

    @Override
    public void showSignIn() {
        ((AuthenticationActivity) fragment.getActivity()).showFragment(new SignInFragment(),
                SignInFragment.class.getName());
    }

    private void validateInput() {
        view.hideEmailError();

        String email = view.getEmail();

        if (TextUtils.isEmpty(email)) {
            view.showEmailError();
            return;
        }

        ((AuthenticationActivity) fragment.getActivity()).sendRecoverCode(view.getEmail());
    }
}
