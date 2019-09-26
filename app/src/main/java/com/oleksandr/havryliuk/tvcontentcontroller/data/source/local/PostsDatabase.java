package com.oleksandr.havryliuk.tvcontentcontroller.data.source.local;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
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