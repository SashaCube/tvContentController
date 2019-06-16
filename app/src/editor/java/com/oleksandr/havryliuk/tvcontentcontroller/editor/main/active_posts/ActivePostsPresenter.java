package com.oleksandr.havryliuk.tvcontentcontroller.editor.main.active_posts;

import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsDataSource;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivePostsPresenter implements ActivePostsContract.IActivePostPresenter {

    private ActivePostsContract.IActivePostsView view;
    private PostsRepository mRepository;

    public ActivePostsPresenter(ActivePostsContract.IActivePostsView view, PostsRepository postsRepository) {
        this.view = view;
        mRepository = postsRepository;
    }

    @Override
    public void clickDelete(final Post post) {
        mRepository.deletePost(post.getId());
        view.showPostDeleted();
    }

    @Override
    public void clickEdit(Post post) {
        view.showEditScreen(post);
    }

    @Override
    public void clickSetPost(final Post post) {
        mRepository.savePost(post);
    }

    private void processPosts(List<Post> posts) {
        if (getActivePosts(posts).isEmpty()) {
            view.showNoPosts();
        } else {
            view.setPosts(getActivePosts(posts));
        }
    }

    public List<Post> getActivePosts(List<Post> posts) {
        ArrayList<Post> activePosts = new ArrayList<>();

        for (Post p : posts) {
            if (p.isState()) {
                activePosts.add(p);
            }
        }

        return activePosts;
    }

    @Override
    public void start() {
        mRepository.registerObserver(this);
        mRepository.notifyObserversPostsChanged();
    }

    @Override
    public void stop() {
        mRepository.removeObserver(this);
    }

    @Override
    public void onPostDataChanged(List<Post> posts) {
        processPosts(posts);
    }

    @Override
    public void onConfDataChanged(Map<String, Object> conf) {

    }
}
