package com.oleksandr.havryliuk.tvcontentcontroller.data.source;

import com.oleksandr.havryliuk.tvcontentcontroller.data.source.RepositoryObserver;

public interface Subject {
    void registerObserver(RepositoryObserver repositoryObserver);
    void removeObserver(RepositoryObserver repositoryObserver);
    void notifyObserversPostsChanged();
    void notifyObserversConfChanged();
}