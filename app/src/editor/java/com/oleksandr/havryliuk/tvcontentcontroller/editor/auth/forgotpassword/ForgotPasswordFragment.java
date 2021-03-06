package com.oleksandr.havryliuk.tvcontentcontroller.editor.auth.forgotpassword;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
