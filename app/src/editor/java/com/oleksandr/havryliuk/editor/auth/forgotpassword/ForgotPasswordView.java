package com.oleksandr.havryliuk.editor.auth.forgotpassword;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oleksandr.havryliuk.editor.auth.AuthenticationActivity;
import com.oleksandr.havryliuk.editor.auth.signin.SignInFragment;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

import java.util.Objects;


public class ForgotPasswordView implements ForgotPasswordContract.IForgotPasswordView {

    private ForgotPasswordContract.IForgotPasswordPresenter presenter;
    private EditText emailEt;
    private Fragment fragment;

    @Override
    public void init(Fragment fragment, View root) {
        this.fragment = fragment;

        emailEt = root.findViewById(R.id.email_et);

        Button sendRecoverCodeBtn = root.findViewById(R.id.send_recover_code_btn);
        TextView backToSignInTv = root.findViewById(R.id.back_signin_txt);

        sendRecoverCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.sendRecoverCodeClick();
            }
        });

        backToSignInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showSignInClick();
            }
        });
    }

    @Override
    public void hideEmailError() {
        emailEt.setError(null);
    }

    @Override
    public void showEmailError() {
        emailEt.setError(fragment.getString(R.string.please_enter_email));
    }

    @Override
    public String getEmail() {
        return emailEt.getText().toString();
    }

    @Override
    public void setPresenter(ForgotPasswordContract.IForgotPasswordPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showSignIn() {
        ((AuthenticationActivity) Objects.requireNonNull(fragment.getActivity()))
                .showFragment(new SignInFragment(),
                        SignInFragment.class.getName());
    }

    @Override
    public void sendRecoverCode(String email) {
        ((AuthenticationActivity) Objects.requireNonNull(fragment.getActivity()))
                .sendRecoverCode(email);
    }
}
