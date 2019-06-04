package com.oleksandr.havryliuk.tvcontentcontroller.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;

@Database(entities = {Post.class}, version = 2, exportSchema = false)
public abstract class PostsDatabase extends RoomDatabase {

    private static PostsDatabase INSTANCE;

    public abstract PostsDao postDao();

    private static final Object sLock = new Object();

    public static PostsDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        PostsDatabase.class, "Posts.db")
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return INSTANCE;
        }
    }

}