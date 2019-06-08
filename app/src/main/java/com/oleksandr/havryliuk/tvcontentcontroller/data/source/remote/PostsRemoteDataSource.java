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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.oleksandr.havryliuk.tvcontentcontroller.utils.Constants.SHOW_AD_CONF;

public class PostsRemoteDataSource implements PostsDataSource {

    private DatabaseReference databaseReference;

    private static PostsRemoteDataSource INSTANCE;

    private static HashMap<String, Post> POSTS_SERVICE_DATA;
    private static HashMap<String, Boolean> CONF_SERVICE_DATA;

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
        USERID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        Log.i("Remote", "connected: user");
        databaseReference = FirebaseDatabase.getInstance()
                .getReference()
                .child("users")
                .child(USERID);

        databaseReference.child("posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    getPostsFromSnapShot(dataSnapshot);
                    Log.i("Firebase DB", "update value for user " + USERID);
                } else {
                    saveNewUser(USERID);
                    Log.i("Firebase DB", "create new posts List for user " + USERID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("Firebase", "error");
            }
        });

        databaseReference.child("conf").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    getConfFromSnapShot(dataSnapshot);
                    Log.i("Firebase DB", "update value for user " + USERID);
                } else {
                    saveNewUser(USERID);
                    Log.i("Firebase DB", "create new conf List for user " + USERID);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("Firebase", "error");
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
            Log.i("DataSnapshot", "Post from remote repo " + post);
        }
    }

    private void getConfFromSnapShot(DataSnapshot dataSnapshot) {
        CONF_SERVICE_DATA.clear();

        HashMap<String, Boolean> map = (HashMap<String, Boolean>) dataSnapshot.getValue();

            CONF_SERVICE_DATA.put(SHOW_AD_CONF, map.get(SHOW_AD_CONF));
            Log.i("DataSnapshot", "Conf from remote repo " + SHOW_AD_CONF +
                    " " + map.get(SHOW_AD_CONF));
    }

    private void saveNewUser(String userId) {
        databaseReference.child(userId).child("posts").setValue(POSTS_SERVICE_DATA);
        databaseReference.child(userId).child("conf").setValue(CONF_SERVICE_DATA);
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
    public void saveConf(@NonNull String key, Boolean value) {
        CONF_SERVICE_DATA.put(key, value);
        databaseReference.child("conf").setValue(CONF_SERVICE_DATA);
    }

    @Override
    public void getConf(final LoadConfCallback callback) {
        callback.onConfigLoaded(CONF_SERVICE_DATA);
    }

    @Override
    public void savePost(@NonNull Post post) {
        POSTS_SERVICE_DATA.put(post.getId(), post);
        databaseReference.child("posts").setValue(POSTS_SERVICE_DATA);
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
        // Not required because the PostsRepository handles the logic of refreshing the
        // posts from all the available data sources.
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
}