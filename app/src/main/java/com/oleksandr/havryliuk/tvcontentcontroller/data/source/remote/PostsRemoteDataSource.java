package com.oleksandr.havryliuk.tvcontentcontroller.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsDataSource;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsRepositoryObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PostsRemoteDataSource implements PostsDataSource {

    private final static String POSTS = "posts";
    private final static String CONF = "conf";
    private final static String USERS = "users";

    private DatabaseReference databaseReference;

    private static PostsRemoteDataSource INSTANCE;

    private static HashMap<String, Post> POSTS_SERVICE_DATA;
    private static HashMap<String, Object> CONF_SERVICE_DATA;
    private ArrayList<PostsRepositoryObserver> mObservers;

    private String USERID;

    static {
        POSTS_SERVICE_DATA = new HashMap<>();
        CONF_SERVICE_DATA = new HashMap<>();
    }

    public static PostsRemoteDataSource getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new PostsRemoteDataSource();
        }
        return INSTANCE;

    }

    public void destroyInstance() {
        POSTS_SERVICE_DATA.clear();
        INSTANCE = null;
    }

    // Prevent direct instantiation.
    private PostsRemoteDataSource() {
        mObservers = new ArrayList<>();
        USERID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        Log.i("Remote", "connected: user" + USERID);
        databaseReference = FirebaseDatabase.getInstance()
                .getReference()
                .child(USERS)
                .child(USERID);

        databaseReference.child(POSTS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    getPostsFromSnapShot(dataSnapshot);
                    notifyObserversPostsChanged();
                    Log.i("Remote DB", "update posts");
                } else {
                    saveNewUser(USERID);
                    Log.i("Remote DB", "create new posts List");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("Remote", "error - posts cancelled");
            }
        });

        databaseReference.child(CONF).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    getConfFromSnapShot(dataSnapshot);
                    notifyObserversConfChanged();
                    Log.i("Remote DB", "update conf");
                } else {
                    saveNewUser(USERID);
                    Log.i("Remote DB", "create new conf List");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("Remote", "error - conf cancelled");
            }
        });
    }

    private void getPostsFromSnapShot(DataSnapshot dataSnapshot) {
        POSTS_SERVICE_DATA.clear();

        HashMap<String, HashMap<String, Object>> map = (HashMap<String, HashMap<String, Object>>) dataSnapshot.getValue();
        for (Map.Entry<String, HashMap<String, Object>> entry : map.entrySet()) {

            String title = (String) entry.getValue().get("title");
            String about = (String) entry.getValue().get("about");
            String postId = (String) entry.getValue().get("id");
            boolean state = (Boolean) entry.getValue().get("state");
            String type = (String) entry.getValue().get("type");
            String imagePath = (String) entry.getValue().get("imagePath");
            String text = (String) entry.getValue().get("text");
            long duration = (Long) entry.getValue().get("duration");
            String createDate = (String) entry.getValue().get("createDate");


            Post post = new Post(postId, title, about, createDate, type, imagePath, text, state,
                    duration);
            POSTS_SERVICE_DATA.put(postId, post);
        }
    }


    private void getConfFromSnapShot(DataSnapshot dataSnapshot) {
        CONF_SERVICE_DATA.clear();

        HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();

        assert map != null;
        CONF_SERVICE_DATA.putAll(map);
    }

    private void saveNewUser(String userId) {
        databaseReference.child(userId).child(POSTS).setValue(POSTS_SERVICE_DATA);
        databaseReference.child(userId).child(CONF).setValue(CONF_SERVICE_DATA);
    }

    @Override
    public void getPosts(@NonNull final LoadPostsCallback callback) {
        List<Post> tasks = new ArrayList<>(POSTS_SERVICE_DATA.values());
        callback.onPostsLoaded(tasks);
    }

    @Override
    public void getPost(@NonNull String postId, @NonNull final GetPostCallback callback) {
        final Post post = POSTS_SERVICE_DATA.get(postId);
        callback.onPostLoaded(post);
    }

    @Override
    public void saveConf(@NonNull String key, Object value) {
        CONF_SERVICE_DATA.put(key, value);
        databaseReference.child(CONF).setValue(CONF_SERVICE_DATA);
    }

    @Override
    public void getConf(final LoadConfCallback callback) {
        callback.onConfigLoaded(CONF_SERVICE_DATA);
    }

    @Override
    public void savePost(@NonNull Post post) {
        POSTS_SERVICE_DATA.put(post.getId(), post);
        databaseReference.child(POSTS).setValue(POSTS_SERVICE_DATA);
    }

    @Override
    public void clearDisActivatedPosts() {
        Iterator<Map.Entry<String, Post>> it = POSTS_SERVICE_DATA.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String, Post> entry = it.next();
            if (!entry.getValue().isState()) {
                it.remove();
            }
        }
        databaseReference.setValue(POSTS_SERVICE_DATA);
    }

    @Override
    public void refreshPosts() {
    }

    @Override
    public void deleteAllPosts() {
        POSTS_SERVICE_DATA.clear();
        databaseReference.setValue(POSTS_SERVICE_DATA);
    }

    @Override
    public void deletePost(@NonNull String postId) {
        POSTS_SERVICE_DATA.remove(postId);
        databaseReference.setValue(POSTS_SERVICE_DATA);
    }


    @Override
    public void registerObserver(PostsRepositoryObserver repositoryObserver) {
        if (!mObservers.contains(repositoryObserver)) {
            mObservers.add(repositoryObserver);
        }
    }

    @Override
    public void removeObserver(PostsRepositoryObserver repositoryObserver) {
        if (mObservers.contains(repositoryObserver)) {
            mObservers.remove(repositoryObserver);
        }
    }

    @Override
    public void notifyObserversPostsChanged() {
        for (PostsRepositoryObserver observer : mObservers) {
            observer.onPostDataChanged(new ArrayList<>(POSTS_SERVICE_DATA.values()));
        }
    }

    @Override
    public void notifyObserversConfChanged() {
        for (PostsRepositoryObserver observer : mObservers) {
            observer.onConfDataChanged(CONF_SERVICE_DATA);
        }
    }
}