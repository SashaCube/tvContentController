package com.oleksandr.havryliuk.tvcontentcontroller.data.source;

import android.content.Context;
import android.support.annotation.NonNull;

import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.local.PostsLocalDataSource;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.remote.PostsRemoteDataSource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PostsRepository implements PostsDataSource {

    private static PostsRepository INSTANCE = null;

    private PostsDataSource mPostsRemoteDataSource;

    private PostsDataSource mPostsLocalDataSource;

    Map<String, Post> mCachedPosts;

    // Prevent direct instantiation.
    private PostsRepository(@NonNull Context context) {
        mPostsRemoteDataSource = PostsRemoteDataSource.getInstance();
        mPostsLocalDataSource = PostsLocalDataSource.getInstance(context);
    }

    public static PostsRepository getInstance(@NonNull final Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PostsRepository(context);
        }
        return INSTANCE;
    }

    public void destroyInstance() {
        INSTANCE.mPostsLocalDataSource.destroyInstance();
        INSTANCE.mPostsRemoteDataSource.destroyInstance();
        INSTANCE = null;
    }

    @Override
    public void getPosts(@NonNull final LoadPostsCallback callback) {

        // Query the local storage if available.
        mPostsLocalDataSource.getPosts(new LoadPostsCallback() {
            @Override
            public void onPostsLoaded(List<Post> posts) {
                refreshCache(posts);
                callback.onPostsLoaded(new ArrayList<>(mCachedPosts.values()));
            }

            @Override
            public void onDataNotAvailable() {
                getPostsFromRemoteDataSource(callback);
            }
        });


        // Fetch new data from the network.
        getPostsFromRemoteDataSource(callback);
    }

    @Override
    public void getPost(@NonNull final String postId, @NonNull final GetPostCallback callback) {
        Post cachedPost = getPostWithId(postId);

        // Respond immediately with cache if available
        if (cachedPost != null) {
            callback.onPostLoaded(cachedPost);
            return;
        }

        // Is the post in the local data source? If not, query the network.
        mPostsLocalDataSource.getPost(postId, new GetPostCallback() {
            @Override
            public void onPostLoaded(Post post) {
                // Do in memory cache update to keep the app UI up to date
                if (mCachedPosts == null) {
                    mCachedPosts = new LinkedHashMap<>();
                }
                mCachedPosts.put(post.getId(), post);
                callback.onPostLoaded(post);
            }

            @Override
            public void onDataNotAvailable() {
                mPostsRemoteDataSource.getPost(postId, new GetPostCallback() {
                    @Override
                    public void onPostLoaded(Post post) {
                        // Do in memory cache update to keep the app UI up to date
                        if (mCachedPosts == null) {
                            mCachedPosts = new LinkedHashMap<>();
                        }
                        mCachedPosts.put(post.getId(), post);
                        callback.onPostLoaded(post);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void savePost(@NonNull Post post) {
        mPostsRemoteDataSource.savePost(post);
        mPostsLocalDataSource.savePost(post);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedPosts == null) {
            mCachedPosts = new LinkedHashMap<>();
        }
        mCachedPosts.put(post.getId(), post);
    }

    @Override
    public void clearDisActivatedPosts() {
        mPostsRemoteDataSource.clearDisActivatedPosts();
        mPostsLocalDataSource.clearDisActivatedPosts();

        // Do in memory cache update to keep the app UI up to date
        if (mCachedPosts == null) {
            mCachedPosts = new LinkedHashMap<>();
        }
        Iterator<Map.Entry<String, Post>> it = mCachedPosts.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Post> entry = it.next();
            if (entry.getValue().isState()) {
                it.remove();
            }
        }
    }

    @Override
    public void getConf(@NonNull LoadConfCallback callback) {
        mPostsRemoteDataSource.getConf(callback);
    }

    @Override
    public void saveConf(@NonNull String key, Object value) {
        mPostsRemoteDataSource.saveConf(key, value);
    }

    @Override
    public void refreshPosts() {
    }

    @Override
    public void deleteAllPosts() {
        mPostsRemoteDataSource.deleteAllPosts();
        mPostsLocalDataSource.deleteAllPosts();

        if (mCachedPosts == null) {
            mCachedPosts = new LinkedHashMap<>();
        }
        mCachedPosts.clear();
    }

    @Override
    public void deletePost(@NonNull String postId) {
        mPostsRemoteDataSource.deletePost(postId);
        mPostsLocalDataSource.deletePost(postId);

        mCachedPosts.remove(postId);
    }

    private void getPostsFromRemoteDataSource(@NonNull final LoadPostsCallback callback) {
        mPostsRemoteDataSource.getPosts(new LoadPostsCallback() {
            @Override
            public void onPostsLoaded(List<Post> posts) {
                refreshCache(posts);
                refreshLocalDataSource(posts);
                callback.onPostsLoaded(new ArrayList<>(mCachedPosts.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }


    private void refreshCache(List<Post> posts) {
        if (mCachedPosts == null) {
            mCachedPosts = new LinkedHashMap<>();
        }
        mCachedPosts.clear();

        for (Post t : posts) {
            mCachedPosts.put(t.getId(), t);
        }
    }

    private void refreshLocalDataSource(List<Post> posts) {
        mPostsLocalDataSource.deleteAllPosts();
        for (Post post : posts) {
            mPostsLocalDataSource.savePost(post);
        }
    }

    private Post getPostWithId(@NonNull String id) {
        if (mCachedPosts == null || mCachedPosts.isEmpty()) {
            return null;
        } else {
            return mCachedPosts.get(id);
        }
    }

    @Override
    public void registerObserver(PostsRepositoryObserver repositoryObserver) {
        mPostsRemoteDataSource.registerObserver(repositoryObserver);
    }

    @Override
    public void removeObserver(PostsRepositoryObserver repositoryObserver) {
        mPostsRemoteDataSource.removeObserver((repositoryObserver));
    }

    @Override
    public void notifyObserversPostsChanged() {
        mPostsRemoteDataSource.notifyObserversPostsChanged();
    }

    @Override
    public void notifyObserversConfChanged() {
        mPostsRemoteDataSource.notifyObserversConfChanged();
    }
}
