package com.oleksandr.havryliuk.tvcontentcontroller.client.connect;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.client.MainActivity;
import com.oleksandr.havryliuk.tvcontentcontroller.utils.auth.Auth;

public class ConnectionActivity extends AppCompatActivity {

    private final static String email = "tv@tv.ua";
    private final static String password = "12345678";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        if (Auth.isUserAuth()) {
            startActivity(getStartMainActivityIntent());
            finish();
        } else {
            Auth.signIn(email, password, this, getStartMainActivityIntent());
        }
    }

    private Intent getStartMainActivityIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
}