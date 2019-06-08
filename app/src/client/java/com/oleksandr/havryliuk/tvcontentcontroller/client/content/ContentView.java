package com.oleksandr.havryliuk.tvcontentcontroller.client.content;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oleksandr.havryliuk.tvcontentcontroller.R;
import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.image_manager.ImageManager;

import java.util.List;
import java.util.Objects;

import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.AD;
import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.IMAGE;
import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.NEWS;
import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.TEXT;

public class ContentView implements ContentContract.IContentView {

    private final static String TAG = ContentPresenter.class.getName();
    private final static int LOAD = -1;

    private ContentContract.IContentPresenter presenter;
    private Fragment fragment;
    private View root;

    private ViewGroup adPostView, newsPostView, imagePostView, textPostView;
    private ImageView adPostImage, imagePostImage, newsPostImage;
    private TextView textPostText, adPostText, newsPostText;

    private Thread displayPostThread;
    private int postIndex = LOAD;
    private volatile static List<Post> posts;

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

    private void showADPost(Post post) {
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

    private void showNewsPost(Post post) {
        ImageManager.loadInto(root.getContext(), post.getImagePath(), newsPostImage);
        newsPostText.setText(post.getText());

        hideAll();
        newsPostView.setVisibility(View.VISIBLE);
    }


    private void showTextPost(Post post) {
        textPostText.setText(post.getText());

        hideAll();
        textPostView.setVisibility(View.VISIBLE);
    }


    private void showImagePost(Post post) {
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

    @Override
    public void setPosts(List<Post> posts) {
        this.posts = posts;

        if (postIndex == LOAD || postIndex >= posts.size()) {
            postIndex = 0;
        }
    }

    private void showPost(Post post) {
        switch (post.getType()) {
            case NEWS:
                showNewsPost(post);
                break;
            case AD:
                showADPost(post);
                break;
            case IMAGE:
                showImagePost(post);
                break;
            case TEXT:
                showTextPost(post);
                break;
        }
    }

    @Override
    public void startDisplayPosts() {
        Log.i(TAG, "start display posts");
        initDisplayPostThread();
        displayPostThread.start();
    }

    @Override
    public void stopDisplayPosts() {
        Log.i(TAG, "syop display posts");
        if (isActive()) {
            displayPostThread.interrupt();
        }
    }

    private void initDisplayPostThread() {
        displayPostThread = new Thread() {
            @Override
            public void run() {
                try {
                    final long[] duration = {2000};
                    while (!isInterrupted()) {
                        if (fragment.getActivity() == null) {
                            return;
                        }

                        Objects.requireNonNull(fragment.getActivity()).runOnUiThread(() -> {
                            duration[0] = displayPost();
                        });

                        Thread.sleep(duration[0]);
                    }
                } catch (InterruptedException e) {

                }
            }
        };
    }

    private long displayPost() {
        if ((posts != null && !posts.isEmpty()) && postIndex != LOAD) {
            Post post;
            if (postIndex < posts.size()) {
                post = posts.get(postIndex);
                postIndex++;
            } else {
                post = posts.get(0);
                postIndex = 0;
            }

            showPost(post);
            Log.i(TAG, "display post : " + post);
            return post.getDuration() * 1000;
        } else {
            showEmptyScreen();
            Log.i(TAG, "show empty screen");
            return 10 * 1000;
        }
    }

    private void showEmptyScreen() {
        hideAll();
    }
}
