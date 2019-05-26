package com.oleksandr.havryliuk.editor.data.source.local;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.oleksandr.havryliuk.editor.data.Post;

import java.util.List;

@Dao
public interface PostsDao {

    @Query("SELECT * FROM posts")
    List<Post> getPosts();

    @Query("SELECT * FROM Posts WHERE post_id = :postId")
    Post getPostById(String postId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPost(Post task);

    @Update
    int updatePost(Post task);

    @Query("UPDATE posts SET state = :state WHERE post_id = :postId")
    void updateState(String postId, boolean state);

    @Query("DELETE FROM posts WHERE post_id = :postId")
    int deletePostById(String postId);

    @Query("DELETE FROM posts")
    void deletePosts();

    @Query("DELETE FROM posts WHERE state = 1")
    int deleteDisActivetedPosts();
}