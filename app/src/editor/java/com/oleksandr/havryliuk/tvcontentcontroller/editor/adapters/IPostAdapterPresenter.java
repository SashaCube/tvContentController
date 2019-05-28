package com.oleksandr.havryliuk.tvcontentcontroller.editor.adapters;

import com.oleksandr.havryliuk.tvcontentcontroller.data.Post;

public interface IPostAdapterPresenter {

    void clickDelete(Post post);

    void clickEdit(Post post);

    void clickSetPost(Post oldPost);
}
