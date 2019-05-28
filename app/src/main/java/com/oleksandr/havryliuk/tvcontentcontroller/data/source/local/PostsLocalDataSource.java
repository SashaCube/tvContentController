package com.oleksandr.havryliuk.tvcontentcontroller.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsDataSource;
import com.oleksandr.havryliuk.tvcontentcontroller.utils.AppExecutors;

import java.util.List;

public class PostsLocalDataSource implements PostsDataSource {

    private static volatile PostsLocalDataSource INSTANCE;

    private PostsDao mPostsDao;

    private AppExecutors mAppExecutors;

    // Prevent direct instantiation.
    private PostsLocalDataSource(@NonNull AppExecutors appExecutors,
                                 @NonNull PostsDao tasksDao) {
        mAppExecutors = appExecutors;
        mPostsDao = tasksDao;
    }

    public static PostsLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            synchronized (PostsLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PostsLocalDataSource(new AppExecutors(),
                            PostsDatabase.getInstance(context).postDao());
                }
            }
        }
        return INSTANCE;
    }

    public void destroyInstance() {
        deleteAllPosts();
        INSTANCE = null;
    }

    @Override
    public void getPosts(@NonNull final LoadPostsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Post> tasks = mPostsDao.getPosts();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (tasks.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onPostsLoaded(tasks);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getPost(@NonNull final String taskId, @NonNull final GetPostCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Post post = mPostsDao.getPostById(taskId);

                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (post != null) {
                            callback.onPostLoaded(post);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    @Override
    public void savePost(@NonNull final Post post) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mPostsDao.insertPost(post);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void clearDisActivatedPosts() {
        Runnable clearPostsRunnable = new Runnable() {
            @Override
            public void run() {
                mPostsDao.deleteDisActivetedPosts();

            }
        };

        mAppExecutors.diskIO().execute(clearPostsRunnable);
    }

    @Override
    public void getConf(@NonNull LoadConfCallback callback) {

    }

    @Override
    public void saveConf(@NonNull String key, Boolean value) {

    }

    @Override
    public void refreshPosts() {
    }

    @Override
    public void deleteAllPosts() {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mPostsDao.deletePosts();
            }
        };
        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void deletePost(@NonNull final String postId) {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mPostsDao.deletePostById(postId);
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    static void clearInstance() {
        INSTANCE = null;
    }
}