package com.oleksandr.havryliuk.editor.auth.forgotpassword;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oleksandr.havryliuk.tvcontentcontroller.R;


public class ForgotPasswordFragment extends Fragment {

    private ForgotPasswordPresenter presenter;
    private ForgotPasswordView view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        initView(root);
        initPresenter();

        return root;
    }

    private void initView(View root) {
        view = new ForgotPasswordView();
        view.init(this, root);
    }

    private void initPresenter() {
        presenter = new ForgotPasswordPresenter(view);
        view.setPresenter(presenter);
    }
}
