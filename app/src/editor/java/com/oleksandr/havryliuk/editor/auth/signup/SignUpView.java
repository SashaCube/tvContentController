package com.oleksandr.havryliuk.editor.auth.signup;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oleksandr.havryliuk.editor.auth.AuthenticationActivity;
import com.oleksandr.havryliuk.editor.auth.signin.SignInFragment;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

import java.util.Objects;

public class SignUpView implements SignUpContract.ISignUpView {

    private EditText loginEt, emailEt, passwordEt, confirmPasswordEt;
    private SignUpContract.ISignUpPresenter presenter;
    private Fragment fragment;

    @Override
    public void init(Fragment fragment, View root) {
        this.fragment = fragment;

        Button signUpBtn = root.findViewById(R.id.sign_up_btn);
        TextView signInTv = root.findViewById(R.id.sign_in_txt);
        loginEt = root.findViewById(R.id.login_et);
        emailEt = root.findViewById(R.id.email_et);
        passwordEt = root.findViewById(R.id.password_et);
        confirmPasswordEt = root.findViewById(R.id.confirm_password_et);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.signUpClick();
            }
        });

        signInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showSignInClick();
            }
        });
    }

    @Override
    public void setPresenter(SignUpContract.ISignUpPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void hideLoginError() {
        loginEt.setError(null);
    }

    @Override
    public void showLoginError() {
        loginEt.setError(fragment.getString(R.string.please_enter_login));
    }

    @Override
    public void hideEmailError() {
        emailEt.setError(null);
    }

    @Override
    public void showEmailError() {
        loginEt.setError(fragment.getString(R.string.please_enter_email));
    }

    @Override
    public void showPasswordError() {
        passwordEt.setError(fragment.getString(R.string.please_enter_password));
    }

    @Override
    public void hidePasswordError() {
        passwordEt.setError(null);
    }

    @Override
    public void showConfirmError() {
        confirmPasswordEt.setError(fragment.getString(R.string.password_arent_equal));
    }

    @Override
    public void hideConfirmError() {
        confirmPasswordEt.setError(null);
    }

    @Override
    public String getLogin() {
        return loginEt.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwordEt.getText().toString();
    }

    @Override
    public String getEmail() {
        return emailEt.getText().toString();
    }

    @Override
    public String getConfirmPassword() {
        return confirmPasswordEt.getText().toString();
    }

    @Override
    public void showSignIn() {
        ((AuthenticationActivity) Objects.requireNonNull(fragment.getActivity()))
                .showFragment(new SignInFragment(), SignInFragment.class.getName());
    }

    @Override
    public void signUp(String login, String email, String password) {
        ((AuthenticationActivity) Objects.requireNonNull(fragment.getActivity()))
                .signUp(login, email, password);
    }
}
