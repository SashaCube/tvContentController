package com.oleksandr.havryliuk.editor.edit_post;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oleksandr.havryliuk.editor.model.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

import static com.oleksandr.havryliuk.editor.edit_post.EditPostPresenter.CANCEL;

public class EditPostFragment extends Fragment {

    private EditPostContract.IEditPostPresenter presenter;
    private EditPostContract.IEditPostView view;
    private Post post;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_post, container, false);

        initView(root);
        initPresenter();

        return root;
    }

    public void init(Post post) {
        this.post = post;
    }

    private void initView(View root) {
        view = new EditPostView();
        view.init(root);
    }

    private void initPresenter() {
        presenter = new EditPostPresenter(view, this);
        view.setPresenter(presenter);
        presenter.initEditPost(post);
    }

    @Override
    public void onDestroy() {
        if (presenter.getResult() == CANCEL) {
            Toast.makeText(getContext(), "changes have not been saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "changes saved successfully", Toast.LENGTH_SHORT).show();
        }
        super.onDestroy();
    }
}

