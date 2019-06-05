package com.oleksandr.havryliuk.tvcontentcontroller.editor.main.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.utils.ActivityUtils;

public class SettingsFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        PreferenceFragmentCompat fragment = new SettingsPreferenceFragment();

        ActivityUtils.addFragmentToActivity(getChildFragmentManager(), fragment, R.id.fragment,
                SettingsPreferenceFragment.class.getName());

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }
}