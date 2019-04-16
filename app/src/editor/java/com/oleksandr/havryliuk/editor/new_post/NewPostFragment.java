package com.oleksandr.havryliuk.editor.new_post;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.oleksandr.havryliuk.tvcontentcontroller.R;

public class NewPostFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_post, container, false);

        Spinner spinner = root.findViewById(R.id.spinner);
        spinner.setAdapter(ArrayAdapter.createFromResource(getContext(),
                R.array.type_list, R.layout.item_type ));

        initView(root);
        initPresenter();

        return root;
    }

    public void initView(View root){

    }

    public void initPresenter(){

    }
}
