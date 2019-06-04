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
    private volatile static boolean state;
    private boolean startShowingPosts;
    private boolean showAD = false;

    private Handler handler;

    private volatile List<Post> posts;

    public ContentPresenter(ContentContract.IContentView view, PostsDataSource repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void loadPosts() {
        if (!state) {
            return;
        }

        repository.getPosts(new PostsDataSource.LoadPostsCallback() {
            @Override
            public void onPostsLoaded(final List<Post> posts) {
                processPosts(getFilteredPosts(posts));
                Log.i(TAG, "Loaded Posts " + posts.size());

                // refresh posts regularly
                if (handler != null) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadPosts();
                        }
                    }, 60000);
                }
            }

            @Override
            public void onDataNotAvailable() {
            }
        });

        repository.getConf(new PostsDataSource.LoadConfCallback() {
            @Override
            public void onConfigLoaded(Map<String, Boolean> configurations) {
                Log.i(TAG, "Loaded configuration " + posts.size());

                if (!configurations.isEmpty()) {
                    updateConf(configurations);
                }
            }

            @Override
            public void onDataNotAvailable() {
            }
        });
    }

    private void updateConf(Map<String, Boolean> configurations){
        showAD = configurations.get(Constants.SHOW_AD_CONF);
    }


    private synchronized void processPosts(List<Post> posts) {
        this.posts = posts;

        if (!this.posts.isEmpty() && startShowingPosts) {
            DelayedPosts(0);
            startShowingPosts = false;
        }
    }

    private void DelayedPosts(int index) {
        if (!state) {
            return;
        }

        if (index >= posts.size() || index < 0) {
            index = 0;
        }

        Post post = posts.get(index);
        showPost(post);
        Log.i(TAG, "show post(" + index + "/" + posts.size() + ") : "
                + post.getTitle() + "| duration: " + post.getDuration());

        final int finalIndex = index;
        if (handler != null) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DelayedPosts(finalIndex + 1);
                }
            }, post.getDuration() * 1000);
        }
    }

    private void showPost(Post post) {
        switch (post.getType()) {
            case NEWS:
                view.showNewsPost(post);
                break;
            case AD:
                view.showADPost(post);
                break;
            case IMAGE:
                view.showImagePost(post);
                break;
            case TEXT:
                view.showTextPost(post);
                break;
        }
    }

    private List<Post> getFilteredPosts(List<Post> allPosts) {
        ArrayList<Post> activePosts = new ArrayList<>();

        for (Post p : allPosts) {
            if (p.isState()) {
                if(!(p.getType().equals(AD) && showAD)) {
                    activePosts.add(p);
                }
            }
        }

        return activePosts;
    }


    @Override
    public void startShowingPosts() {
        Log.i(TAG, "Start showing Posts");

        state = true;

        handler = new Handler();
        startShowingPosts = true;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadPosts();
            }
        }, 100);
    }

    @Override
    public void stopShowingPosts() {
        Log.i(TAG, "Stop showing Posts");

        state = false;
        handler = null;
    }
}
