package com.oleksandr.havryliuk.editor.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.oleksandr.havryliuk.editor.all_posts.AllPostsContract;
import com.oleksandr.havryliuk.editor.model.Post;
import com.oleksandr.havryliuk.tvcontentcontroller.R;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.WordViewHolder> {

    private AllPostsContract.IAllPostsPresenter presenter;
    private String type;
    private final static int NONE = -1;
    private int openPost = NONE;

    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleTextView, aboutTextView, typeTextView, dateTextView, editButton, deleteButton;
        Switch stateSwitch;
        LinearLayout dateLayout, editLayout, stateLayout;

        private WordViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            aboutTextView = itemView.findViewById(R.id.about_text_view);
            typeTextView = itemView.findViewById(R.id.type_text_view);
            dateTextView = itemView.findViewById(R.id.date_text_view);
            stateSwitch = itemView.findViewById(R.id.state_switch);
            dateLayout = itemView.findViewById(R.id.date_layout);
            editLayout = itemView.findViewById(R.id.edit_layout);
            stateLayout = itemView.findViewById(R.id.state_layout);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setPostView(getAdapterPosition());
                }
            });

            stateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                    Post post = mPosts.get(getAdapterPosition());
                    post.setState(isChecked);

                    presenter.clickSetPost(post);
                }
            });
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            switch (v.getId()){
                case R.id.edit_button:
                    presenter.clickEdit(mPosts.get(position));
                    break;
                case R.id.delete_button:
                    setPostView(position);
                    presenter.clickDelete(mPosts.get(position));
                    break;
            }
        }
    }

    private void setPostView(int position) {
        if (openPost == position) {
            openPost = NONE;
        } else {
            openPost = position;
        }
        notifyDataSetChanged();
    }

    private final LayoutInflater mInflater;
    private List<Post> mPosts;

    public PostsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void setPresenter(AllPostsContract.IAllPostsPresenter presenter, String type) {
        this.presenter = presenter;
        this.type = type;
        update();
        notifyDataSetChanged();
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
            holder.aboutTextView.setText(currentPost.getAbout());
            holder.typeTextView.setText(currentPost.getType());
            holder.dateTextView.setText(currentPost.getCreateDate().toString());
            holder.stateSwitch.setChecked(currentPost.isState());

            if (position == openPost) {
                holder.dateLayout.setVisibility(View.VISIBLE);
                holder.stateLayout.setVisibility(View.VISIBLE);
                holder.editLayout.setVisibility(View.VISIBLE);
            } else {
                holder.dateLayout.setVisibility(View.GONE);
                holder.stateLayout.setVisibility(View.GONE);
                holder.editLayout.setVisibility(View.GONE);
            }
        }
    }

    public void update() {
        mPosts = presenter.getPosts(type);
    }

    public int getItemCount() {
        if (mPosts != null)
            return mPosts.size();
        else return 0;
    }
}
