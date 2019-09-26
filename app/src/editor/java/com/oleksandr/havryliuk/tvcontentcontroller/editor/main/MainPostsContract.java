package com.oleksandr.havryliuk.tvcontentcontroller.editor.main;

import androidx.fragment.app.Fragment;
import android.view.View;

public interface MainPostsContract {

    interface IMainFragmentView{

        void init(Fragment fragment, View root);

        void setPresenter(IMainFragmentPresenter presenter);
    }

    interface IMainFragmentPresenter{

    }
}
