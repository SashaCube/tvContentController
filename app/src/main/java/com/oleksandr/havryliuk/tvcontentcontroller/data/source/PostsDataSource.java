package com.oleksandr.havryliuk.tvcontentcontroller.data.source;

import androidx.annotation.NonNull;

import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;

import java.util.List;
import java.util.Map;

public interface PostsDataSource extends PostsSubject {

    interface LoadPostsCallback {

        void onPostsLoaded(List<Post> posts);

        void onDataNotAvailable();
    }

    interface LoadConfCallback {

        void onConfigLoaded(Map<String, Object> configurations);

        void onDataNotAvailable();
    }

    interface GetPostCallback {

        void onPostLoaded(Post post);

        void onDataNotAvailable();
    }

    void destroyInstance();

    void getPosts(@NonNull LoadPostsCallback callback);

    void getPost(@NonNull String postId, @NonNull GetPostCallback callback);

    void savePost(@NonNull Post post);

    void clearDisActivatedPosts();

    void getConf( @NonNull LoadConfCallback callback);

    void saveConf(@NonNull String key, Object value);

    void refreshPosts();

    void deleteAllPosts();

    void deletePost(@NonNull String postId);
}

