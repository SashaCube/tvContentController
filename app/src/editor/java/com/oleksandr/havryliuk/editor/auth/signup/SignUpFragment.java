package com.oleksandr.havryliuk.editor.auth.signup;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oleksandr.havryliuk.tvcontentcontroller.R;

public class SignUpFragment extends Fragment {

    private SignUpContract.ISignUpView view;
    private SignUpContract.ISignUpPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup, container, false);

        initView(root);
        initPresenter();

        return root;
    }

    private void initView(View root) {
        view = new SignUpView();
        view.init(this, root);
    }

    private void initPresenter() {
        presenter = new SignUpPresenter(view);
        view.setPresenter(presenter);
    }
}


