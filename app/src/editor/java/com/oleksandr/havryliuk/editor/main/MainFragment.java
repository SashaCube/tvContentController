package com.oleksandr.havryliuk.editor.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oleksandr.havryliuk.editor.auth.Auth;
import com.oleksandr.havryliuk.editor.auth.AuthenticationActivity;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

public class MainFragment extends Fragment implements View.OnClickListener {

    private View root;
    private ImageView settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main, container, false);

        initView();

        return root;
    }

    public void initView(){
        settings = root.findViewById(R.id.settings_icon);
        settings.setOnClickListener(this);
    }

    public void signOut() {
        Auth.signOut();
        Intent intent = new Intent(getContext(), AuthenticationActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settings_icon:
                signOut(); //TODO: open settings instead of singOut
                break;
        }
    }
}
