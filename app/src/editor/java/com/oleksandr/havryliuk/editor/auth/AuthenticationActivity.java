package com.oleksandr.havryliuk.editor.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.oleksandr.havryliuk.editor.MainActivity;
import com.oleksandr.havryliuk.editor.auth.signin.SignInFragment;
import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.utils.ActivityUtils;

public class AuthenticationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        if (Auth.isUserAuth()) {
            startMainActivity();
        } else {
            showFragment(new SignInFragment(), SignInFragment.class.getName());
        }
    }

    public void showFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (ActivityUtils.isFragmentInBackstack(getSupportFragmentManager(), tag)) {
            getSupportFragmentManager().popBackStackImmediate(tag, 0);
        } else {
            transaction.replace(R.id.fragment, fragment)
                    .addToBackStack(tag)
                    .commit();
        }
    }

    public void signIn(String email, String password) {
        Auth.signIn(email, password, this);
    }

    public void signUp(String login, String email, String password) {
        Auth.signUp(email, password, this);
    }

    public void googleSignIn() {
        Auth.googleSignIn(this);
    }

    public void sendRecoverCode(String email) {
        Auth.sendRecoverCode(email, this);
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Auth.onActivityResult(requestCode, resultCode, data, this);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
