package com.oleksandr.havryliuk.tvcontentcontroller.client.content;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.image_manager.ImageManager;

public class ContentView implements ContentContract.IContentView {

    private ContentContract.IContentPresenter presenter;
    private Fragment fragment;
    private View root;

    private ViewGroup adPostView, newsPostView, imagePostView, textPostView;
    private ImageView adPostImage, imagePostImage, newsPostImage;
    private TextView textPostText, adPostText, newsPostText;

    @Override
    public void init(Fragment fragment, View root) {
        this.fragment = fragment;
        this.root = root;

        initView();
        hideAll();
    }

    private void initView() {
        adPostView = root.findViewById(R.id.post_ad);
        newsPostView = root.findViewById(R.id.post_news);
        imagePostView = root.findViewById(R.id.post_image);
        textPostView = root.findViewById(R.id.post_text);

        adPostImage = root.findViewById(R.id.ad_post_image);
        newsPostImage = root.findViewById(R.id.news_post_image);
        imagePostImage = root.findViewById(R.id.image_post_image);

        adPostText = root.findViewById(R.id.ad_post_text);
        newsPostText = root.findViewById(R.id.news_post_text);
        textPostText = root.findViewById(R.id.text_post_text);
    }

    @Override
    public void setPresenter(ContentContract.IContentPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showADPost(Post post) {
        ImageManager.loadInto(root.getContext(), post.getImagePath(), adPostImage);

        if (post.getText().isEmpty()) {
            adPostText.setVisibility(View.GONE);
        } else {
            adPostText.setVisibility(View.VISIBLE);
            adPostText.setText(post.getText());
        }

        hideAll();
        adPostView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNewsPost(Post post) {
        ImageManager.loadInto(root.getContext(), post.getImagePath(), newsPostImage);
        newsPostText.setText(post.getText());

        hideAll();
        newsPostView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTextPost(Post post) {
        textPostText.setText(post.getText());

        hideAll();
        textPostView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showImagePost(Post post) {
        ImageManager.loadInto(root.getContext(), post.getImagePath(), imagePostImage);

        hideAll();
        imagePostView.setVisibility(View.VISIBLE);
    }

    private void hideAll() {
        adPostView.setVisibility(View.GONE);
        newsPostView.setVisibility(View.GONE);
        imagePostView.setVisibility(View.GONE);
        textPostView.setVisibility(View.GONE);
    }

    @Override
    public boolean isActive() {
        return fragment.isAdded();
    }

    @Override
    public void setLoadingIndicator(boolean value) {

    }

    @Override
    public void showLoadingTasksError() {

    }
}
