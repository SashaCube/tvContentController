package com.oleksandr.havryliuk.editor.auth.signin;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.oleksandr.havryliuk.tvcontentcontroller.R;

public class SignInView implements SignInContract.ISignInView {
    private Button signInBtn;
    private TextView signUpTv, forgotPasswordTv;
    private EditText loginEt, passwordEt;
    private ImageView googleImg;
    //private LoginButton facebookBtn;

    private SignInContract.ISignInPresenter presenter;

    public SignInView() {

    }

    public void setPresenter(SignInContract.ISignInPresenter presenter) {
        this.presenter = presenter;
    }

    public void init(View root) {
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
                presenter.showSignUp();
            }
        });

        forgotPasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showForgotPassword();
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
        loginEt.setError("Please enter login!");
    }

    @Override
    public void showPasswordError() {
        passwordEt.setError("Please enter password!");
    }

    @Override
    public String getLoginText() {
        return loginEt.getText().toString();
    }

    @Override
    public String getPasswordText() {
        return passwordEt.getText().toString();
    }
}