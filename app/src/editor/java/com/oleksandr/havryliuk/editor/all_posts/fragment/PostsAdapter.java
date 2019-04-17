package com.oleksandr.havryliuk.editor.all_posts.fragment;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.oleksandr.havryliuk.editor.all_posts.AllPostsContract;
import com.oleksandr.havryliuk.editor.model.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.WordViewHolder> {

    private AllPostsContract.IAllPostsPresenter presenter;

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView;

        private WordViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_text_view);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Post post = mPosts.get(getAdapterPosition());
            presenter.clickEditButton(post);
        }
    }

    private final LayoutInflater mInflater;
    private List<Post> mPosts;

    public PostsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setPresenter(AllPostsContract.IAllPostsPresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_post, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if (mPosts != null) {
            Post currentPost = mPosts.get(position);
            holder.titleTextView.setText(currentPost.getTitle());
        } else {

        }
    }

    public void setPosts(List<Post> posts) {
        mPosts = posts;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        if (mPosts != null)
            return mPosts.size();
        else return 0;
    }
}
