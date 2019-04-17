package com.oleksandr.havryliuk.editor.repository;

import com.oleksandr.havryliuk.editor.model.Post;

import java.util.ArrayList;

public class Repository { //fake repository

    private static Repository INSTANCE;

    public static Repository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Repository();
        }
        return INSTANCE;
    }

    private ArrayList<Post> posts;

    public Repository() {
        posts = new ArrayList<>();
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }
}
