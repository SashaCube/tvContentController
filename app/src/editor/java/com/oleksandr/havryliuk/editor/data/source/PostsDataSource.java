package com.oleksandr.havryliuk.editor.data.source;

import android.support.annotation.NonNull;

import com.oleksandr.havryliuk.editor.data.Post;

import java.util.List;

public interface PostsDataSource {

    interface LoadPostsCallback {

        void onPostsLoaded(List<Post> posts);

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

    void disActivatePost(@NonNull Post post);

    void disActivatePost(@NonNull String postId);

    void activatePost(@NonNull Post post);

    void activatePost(@NonNull String postId);

    void clearDisActivatedPosts();

    void refreshPosts();

    void deleteAllPosts();

    void deletePost(@NonNull String postId);
}

