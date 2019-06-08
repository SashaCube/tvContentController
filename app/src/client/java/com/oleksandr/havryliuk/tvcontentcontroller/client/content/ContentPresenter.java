package com.oleksandr.havryliuk.tvcontentcontroller.client.content;

import android.os.Handler;
import android.util.Log;

import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsDataSource;
import com.oleksandr.havryliuk.tvcontentcontroller.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.AD;
import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.IMAGE;
import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.NEWS;
import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.TEXT;

public class ContentPresenter implements ContentContract.IContentPresenter {

    private final static String TAG = ContentPresenter.class.getName();

    private ContentContract.IContentView view;
    private PostsDataSource repository;
    private boolean showAD = false;
    private volatile List<Post> posts;
    private Handler updateHandler;


    public ContentPresenter(ContentContract.IContentView view, PostsDataSource repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void loadPosts() {

        repository.getPosts(new PostsDataSource.LoadPostsCallback() {
            @Override
            public void onPostsLoaded(final List<Post> posts) {
                processPosts(getFilteredPosts(posts));
            }

            @Override
            public void onDataNotAvailable() {
            }
        });

        repository.getConf(new PostsDataSource.LoadConfCallback() {
            @Override
            public void onConfigLoaded(Map<String, Boolean> configurations) {
                // TODO: 08.06.19 save all configuration in Preference
                if (!configurations.isEmpty()) {
                    updateConf(configurations);
                }
            }

            @Override
            public void onDataNotAvailable() {
            }
        });
    }

    private void updateConf(Map<String, Boolean> configurations) {
        Log.i(TAG, "Update configuration " + configurations.size());
        showAD = configurations.get(Constants.SHOW_AD_CONF);
    }


    private void processPosts(List<Post> posts) {
        Log.i(TAG, "Update posts " + posts.size());

        this.posts = posts;
        view.setPosts(posts);
    }

    private List<Post> getFilteredPosts(List<Post> allPosts) {
        ArrayList<Post> activePosts = new ArrayList<>();

        for (Post p : allPosts) {
            if (p.isState()) {
                if (!(p.getType().equals(AD) && !showAD)) {
                    activePosts.add(p);
                }
            }
        }

        return activePosts;
    }


    @Override
    public void startShowingPosts() {
        Log.i(TAG, "Start showing Posts");

        updateHandler = new Handler();

        Runnable r = new Runnable() {
            public void run() {
                loadPosts();
                if(updateHandler != null) {
                    updateHandler.postDelayed(this, 60 * 1000);
                }
            }
        };
        updateHandler.postDelayed(r, 4 * 1000);

        view.startDisplayPosts();
    }

    @Override
    public void stopShowingPosts() {
        Log.i(TAG, "Stop showing Posts");
        view.stopDisplayPosts();

        updateHandler = null;
    }
}
