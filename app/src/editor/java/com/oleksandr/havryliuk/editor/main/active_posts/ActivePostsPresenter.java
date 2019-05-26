package com.oleksandr.havryliuk.editor.main.active_posts;

import com.oleksandr.havryliuk.editor.data.Post;
import com.oleksandr.havryliuk.editor.data.source.PostsDataSource;
import com.oleksandr.havryliuk.editor.data.source.PostsRepository;

import java.util.ArrayList;
import java.util.List;

public class ActivePostsPresenter implements ActivePostsContract.IActivePostPresenter {

    private ActivePostsContract.IActivePostsView view;
    private PostsRepository mRepository;

    public ActivePostsPresenter(ActivePostsContract.IActivePostsView view, PostsRepository postsRepository) {
        this.view = view;
        mRepository = postsRepository;
        loadPosts(true);
    }

    @Override
    public void loadPosts(final boolean showLoadingUI) {

        if (showLoadingUI) {
            view.setLoadingIndicator(true);
        }

        mRepository.getPosts(new PostsDataSource.LoadPostsCallback() {
            @Override
            public void onPostsLoaded(List<Post> posts) {
                processPosts(posts);

                if (!view.isActive()) {
                    return;
                }

                if (showLoadingUI) {
                    view.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!view.isActive()) {
                    return;
                }
                view.showLoadingTasksError();

                if (showLoadingUI) {
                    view.setLoadingIndicator(false);
                }
            }
        });
    }

    @Override
    public void clickDelete(final Post post) {
        mRepository.deletePost(post.getId());
        view.showPostDeleted();
        loadPosts(true);
    }

    @Override
    public void clickEdit(Post post) {
        view.showEditScreen(post);
    }

    @Override
    public void clickSetPost(final Post post) {
        mRepository.savePost(post);
        loadPosts(true);
    }

    private void processPosts(List<Post> posts) {
        if (getActivePosts(posts).isEmpty()) {
            view.showNoPosts();
        } else {
            view.setPosts(getActivePosts(posts));
        }
    }

    public List<Post> getActivePosts(List<Post> posts){
        ArrayList<Post> activePosts = new ArrayList<>();

        for(Post p: posts){
            if(p.isState()){
                activePosts.add(p);
            }
        }

        return activePosts;
    }
}
