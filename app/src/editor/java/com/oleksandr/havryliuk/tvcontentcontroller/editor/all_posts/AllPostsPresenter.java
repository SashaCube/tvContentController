package com.oleksandr.havryliuk.tvcontentcontroller.editor.all_posts;

import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.data.source.PostsRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.oleksandr.havryliuk.tvcontentcontroller.data.Post.ALL;

public class AllPostsPresenter implements AllPostsContract.IAllPostsPresenter {

    public final static String TITLE = "Title";
    public final static String DATE = "Date";

    private AllPostsContract.IAllPostsView view;
    private static String sortedPostsBy = DATE;
    private PostsRepository mRepository;

    public AllPostsPresenter(AllPostsContract.IAllPostsView view, PostsRepository postsRepository) {
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

    public static void setSorting(String type) {
        sortedPostsBy = type;
    }

    private List<Post> sortPosts(List<Post> posts) {

        switch (sortedPostsBy) {
            case TITLE:
                Collections.sort(posts, (o1, o2) -> o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase()));
                break;
            case DATE:
                Collections.sort(posts, (o1, o2) -> o1.getCreateDate().compareTo(o2.getCreateDate()));
                break;
        }
        return posts;
    }

    private void processPosts(List<Post> posts) {
        posts = filterPosts(posts);

        if (posts.isEmpty()) {
            view.showNoPosts();
        } else {
            posts = sortPosts(posts);
            view.setPosts(posts);
        }
    }

    private List<Post> filterPosts(List<Post> posts) {
        if (view.getType().equals(ALL)) {
            return posts;
        } else {
            List<Post> filteredPosts = new ArrayList<>();
            for (Post p : posts) {
                if (p.getType().equals(view.getType())) {
                    filteredPosts.add(p);
                }
            }
            return filteredPosts;
        }
    }

    @Override
    public void onPostDataChanged(List<Post> posts) {
        processPosts(posts);
    }

    @Override
    public void onConfDataChanged(Map<String, Object> conf) {

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
}
