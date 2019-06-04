package com.oleksandr.havryliuk.tvcontentcontroller.editor.auth.signin;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.auth.AuthenticationActivity;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.auth.forgotpassword.ForgotPasswordFragment;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.auth.signup.SignUpFragment;

import java.util.Objects;

public class SignInView implements SignInContract.ISignInView {
    private Button signInBtn;
    private TextView signUpTv, forgotPasswordTv;
    private EditText loginEt, passwordEt;
    private ImageView googleImg;
    private Fragment fragment;


    private SignInContract.ISignInPresenter presenter;

    public void setPresenter(SignInContract.ISignInPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void init(Fragment fragment, View root) {
        this.fragment = fragment;

        googleImg = root.findViewById(R.id.google_signIn_button);
        signInBtn = root.findViewById(R.id.signin_btn);
        signUpTv = root.findViewById(R.id.sign_up_txt);
        forgotPasswordTv = root.findViewById(R.id.forgot_password_txt);
        loginEt = root.findViewById(R.id.login_et);
        passwordEt = root.findViewById(R.id.password_et);
        initListeners();
    }

    private void initListeners() {
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.signInClick();
            }
        });

        googleImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.googleSignInClick();
            }
        });

        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showSignUpClick();
            }
        });

        forgotPasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showForgotPasswordClick();
            }
        });
    }

    @Override
    public void hideLoginError() {
        loginEt.setError(null);
    }

    @Override
    public void hidePasswordError() {
        passwordEt.setError(null);
    }

    @Override
    public void showLoginError() {
        loginEt.setError(fragment.getString(R.string.please_enter_login));
    }

    @Override
    public void showPasswordError() {
        passwordEt.setError(fragment.getString(R.string.please_enter_password));
    }

    @Override
    public String getLoginText() {
        return loginEt.getText().toString();
    }

    @Override
    public String getPasswordText() {
        return passwordEt.getText().toString();
    }

    @Override
    public void signIn(String login, String password) {
        ((AuthenticationActivity) Objects.requireNonNull(fragment.getActivity()))
                .signIn(login, password);
    }

    @Override
    public void googleSignIn() {
        ((AuthenticationActivity) Objects.requireNonNull(fragment.getActivity()))
                .googleSignIn();
    }

    @Override
    public void showSignUp() {
        ((AuthenticationActivity) Objects.requireNonNull(fragment.getActivity()))
                .showFragment(new SignUpFragment(),
                        SignUpFragment.class.getName());
    }

    @Override
    public void showForgotPassword() {
        ((AuthenticationActivity) Objects.requireNonNull(fragment.getActivity()))
                .showFragment(new ForgotPasswordFragment(),
                        ForgotPasswordFragment.class.getName());
    }
}