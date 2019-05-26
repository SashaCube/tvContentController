package com.oleksandr.havryliuk.editor.adapters;

import com.oleksandr.havryliuk.editor.data.Post;

public interface IPostAdapterPresenter {

    void clickDelete(Post post);

    void clickEdit(Post post);

    void clickSetPost(Post oldPost);
}
