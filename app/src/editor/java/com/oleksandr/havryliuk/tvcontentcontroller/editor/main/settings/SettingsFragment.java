package com.oleksandr.havryliuk.tvcontentcontroller.editor.main.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;

import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.MainActivity;
import com.oleksandr.havryliuk.tvcontentcontroller.editor.auth.AuthenticationActivity;
import com.oleksandr.havryliuk.tvcontentcontroller.utils.auth.Auth;

import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.pref, rootKey);

        Preference signOut = findPreference("sign_out");
        signOut.setOnPreferenceClickListener(this);

        Preference about = findPreference("about");
        about.setOnPreferenceClickListener(this);

        Preference feedback = findPreference("feedback");
        feedback.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case "sign_out":
                signOut();
                break;
            case "about":
                about();
                break;
            case "feedback":
                feedback();
                break;
        }
        return false;
    }

    private void about() {
        ((MainActivity) Objects.requireNonNull(getActivity())).openAboutFragment();
    }

    private void feedback() {
        String mailto = getString(R.string.feedback_email);

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setDataAndType(Uri.parse(mailto), "text/plain");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_subject));
        emailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.feedback_text));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, Auth.getEmail());

        try {
            Objects.requireNonNull(getActivity()).startActivity(emailIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), getString(R.string.no_email_app_msg),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void signOut() {
        Auth.signOut();
        Intent intent = new Intent(getContext(), AuthenticationActivity.class);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();
    }
}
