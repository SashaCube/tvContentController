package com.oleksandr.havryliuk.editor.new_edit_post.edit_post;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.oleksandr.havryliuk.editor.data.Post;
import com.oleksandr.havryliuk.editor.data.source.PostsRepository;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

import java.util.Objects;

import static com.oleksandr.havryliuk.editor.new_edit_post.edit_post.EditPostPresenter.CANCEL;

public class EditPostFragment extends Fragment {

    private EditPostContract.IEditPostPresenter presenter;
    private EditPostContract.IEditPostView view;
    private Post post;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        view.init(this, root);
    }

    private void initPresenter() {
        presenter = new EditPostPresenter(view,
                PostsRepository.getInstance(Objects.requireNonNull(getContext())));
        view.setPresenter(presenter);
        presenter.initEditPost(post);
    }

    @Override
    public void onDestroy() {
        if (presenter.getResult().equals(CANCEL)) {
            Toast.makeText(getContext(), R.string.edit_canceled, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), R.string.edit_saved, Toast.LENGTH_SHORT).show();
        }
        super.onDestroy();
    }
}

