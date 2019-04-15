package com.oleksandr.havryliuk.editor.new_post;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oleksandr.havryliuk.tvcontentcontroller.R;

public class NewPostFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_post, container, false);

        initView(root);
        initPresenter();

        return root;
    }

    public void initView(View root){

    }

    public void initPresenter(){

    }
}
