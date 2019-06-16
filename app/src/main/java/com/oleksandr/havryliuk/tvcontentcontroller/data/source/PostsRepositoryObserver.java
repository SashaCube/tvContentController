package com.oleksandr.havryliuk.tvcontentcontroller.data.source;

import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;

import java.util.List;
import java.util.Map;

public interface PostsRepositoryObserver {
    void onPostDataChanged(List<Post> posts);

    void onConfDataChanged(Map<String, Object> conf);
}