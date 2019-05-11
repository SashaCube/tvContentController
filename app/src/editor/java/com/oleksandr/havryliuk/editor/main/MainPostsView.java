package com.oleksandr.havryliuk.editor.main;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.oleksandr.havryliuk.editor.auth.Auth;
import com.oleksandr.havryliuk.editor.auth.AuthenticationActivity;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

import java.util.Objects;

public class MainPostsView implements ContractMainPosts.IMainFragmentView, View.OnClickListener {

    private Fragment fragment;
    private View root;
    private ContractMainPosts.IMainFragmentPresenter presenter;

    @Override
    public void init(Fragment fragment, View root) {
        this.fragment = fragment;
        this.root = root;


        ImageView settings = root.findViewById(R.id.settings_icon);
        settings.setOnClickListener(this);
    }

    @Override
    public void setPresenter(ContractMainPosts.IMainFragmentPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settings_icon:
                signOut(); //TODO: open settings instead of singOut
                break;
        }
    }

    public void signOut() {
        Auth.signOut();
        Intent intent = new Intent(fragment.getContext(), AuthenticationActivity.class);
        fragment.startActivity(intent);
        Objects.requireNonNull(fragment.getActivity()).finish();
    }
}
