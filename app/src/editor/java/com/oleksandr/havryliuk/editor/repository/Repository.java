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
        posts.add(new Post(1, "Simple Post", "Inactivity, disconnecting from the service",
                new Date(), Post.ALL, null, "10", true, 10));
        posts.add(new Post(2, "Big News", " Compiler allocated 6MB to compile void android.view.ViewRootImpl.performTraversals()",
                new Date(), Post.NEWS, null, "10", true, 10));
        posts.add(new Post(3, "Big AD", " Compiler allocated 6MB to compile void android.view.ViewRootImpl.performTraversals()",
                new Date(), Post.AD, null, "kajsdghka", false, 6));

    }

    public void addPost(Post post) {
        posts.add(post);
    }
    public void deletePost(Post post){
        posts.remove(post);
    }
    public void setPost(Post post){
        for(Post p: posts){
            if(p.getId() == post.getId()){
                posts.set(posts.indexOf(p), post);
            }
        }
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
