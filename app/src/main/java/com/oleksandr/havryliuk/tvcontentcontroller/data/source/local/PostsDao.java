package com.oleksandr.havryliuk.tvcontentcontroller.data.source.local;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;

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