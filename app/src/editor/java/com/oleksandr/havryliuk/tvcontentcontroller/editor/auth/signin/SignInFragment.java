package com.oleksandr.havryliuk.tvcontentcontroller.editor.auth.signin;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oleksandr.havryliuk.tvcontentcontroller.R;

public class SignInFragment extends Fragment {

    private View root;

    private SignInContract.ISignInView view;
    private SignInContract.ISignInPresenter presenter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_signin, container, false);

        initView(root);
        initPresenter();

        return root;
    }

    private void initView(View root) {
        view = new SignInView();
        view.init(this, root);
    }

    private void initPresenter() {
        presenter = new SignInPresenter(view);
        view.setPresenter(presenter);
    }

}