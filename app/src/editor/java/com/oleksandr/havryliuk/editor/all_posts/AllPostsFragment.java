package com.oleksandr.havryliuk.editor.all_posts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.oleksandr.havryliuk.tvcontentcontroller.R;

public class AllPostsFragment extends Fragment {

    public final static String TITLE = "Title";
    public final static String DATE = "Date";

    private AllPostsContract.IAllPostsView view;
    private AllPostsContract.IAllPostsPresenter presenter;

    private ImageView sortButton;
    private PopupMenu popupMenu;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_all_posts, container, false);

        initView(root);
        initPresenter();

        return root;
    }


    private void initView(final View root) {
        view = new AllPostsView();
        view.init(root);

        sortButton = root.findViewById(R.id.sort_icon);

        popupMenu = new PopupMenu(getContext(), sortButton);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.sort_by_date:
                        presenter.setSorting(DATE);
                        break;
                    case R.id.sort_by_title:
                        presenter.setSorting(TITLE);
                        break;
                }
                return true;
            }
        });
        popupMenu.inflate(R.menu.sorter_posts);

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });
    }

    private void initPresenter() {
        presenter = new AllPostsPresenter(view, this);
        view.setPresenter(presenter, getFragmentManager());
    }
}
