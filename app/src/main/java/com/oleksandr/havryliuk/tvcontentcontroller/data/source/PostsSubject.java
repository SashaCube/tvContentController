package com.oleksandr.havryliuk.tvcontentcontroller.data.source;

public interface PostsSubject {
    void registerObserver(PostsRepositoryObserver repositoryObserver);

    void removeObserver(PostsRepositoryObserver repositoryObserver);

    void notifyObserversPostsChanged();

    void notifyObserversConfChanged();
}