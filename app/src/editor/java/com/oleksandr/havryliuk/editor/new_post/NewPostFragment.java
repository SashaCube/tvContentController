package com.oleksandr.havryliuk.editor.new_post;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oleksandr.havryliuk.tvcontentcontroller.R;

public class NewPostFragment extends Fragment {

    private final static int GALLERY_REQUEST_CODE = 1;
    private NewPostContract.INewPostPresenter presenter;
    private NewPostContract.INewPostView view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_new_post, container, false);

        initView(root);
        initPresenter();

        return root;
    }

    public void initView(View root) {
        view = new NewPostView();
        view.init(root);
    }

    public void initPresenter() {
        presenter = new NewPostPresenter(view, this);
        view.setPresenter(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImage;

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    selectedImage = data.getData();
                    presenter.setUri(selectedImage);
                    break;
            }
        }
    }

    public void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }
}
