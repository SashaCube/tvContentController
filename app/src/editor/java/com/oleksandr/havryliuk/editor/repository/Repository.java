package com.oleksandr.havryliuk.editor.repository;

import com.oleksandr.havryliuk.editor.model.Post;

import java.util.ArrayList;
import java.util.Date;

public class Repository { //fake repository

    private volatile static Repository INSTANCE;

    public static Repository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Repository();
        }
        return INSTANCE;
    }

    private ArrayList<Post> posts;

    public Repository() {
        posts = new ArrayList<>();
        posts.add(new Post("title", "", new Date(), Post.ALL, null, "10", true, 10));
        posts.add(new Post("title", "", new Date(), Post.NEWS, null, "10", true, 10));

    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public ArrayList<Post> getAllPosts() {
        return posts;
    }

    public ArrayList<Post> getPostsByType(String type){
        ArrayList<Post> postList = new ArrayList<>();

        for(Post p : posts){
            if(p.getType().equals(type)){
                postList.add(p);
            }
        }
        return postList;
    }
}
