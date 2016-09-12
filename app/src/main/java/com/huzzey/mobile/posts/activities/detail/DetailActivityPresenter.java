package com.huzzey.mobile.posts.activities.detail;

import com.huzzey.mobile.posts.R;
import com.huzzey.mobile.posts.database.DataSource;
import com.huzzey.mobile.posts.datatype.Comment;
import com.huzzey.mobile.posts.datatype.Post;

/**
 * Created by darren.huzzey on 06/05/16.
 */
public class DetailActivityPresenter implements DetailActivityView.Action {
    private DetailActivityView.View view;
    private Post post;
    private DataSource dataSource;
    private String url;

    public DetailActivityPresenter(DetailActivityView.View view, Post post, DataSource dataSource, String url) {
        this.view = view;
        this.dataSource = dataSource;
        this.url = url;
        if(post != null) {
            this.post = post;
        }
        view.setActionBarTitle(R.string.postDetailTitle);
    }

    @Override
    public void populateScreen() {
        if(post != null) {
            Comment[] list = dataSource.getAllCommentsByPost(post.getId());
            if(post.getEmail() != null && post.getEmail().length() > 0) {
                //new StringBuilder(url).append(post.getEmail().substring(0, post.getEmail().indexOf("@"))).append(".png").toString()
                view.displayImage(url + post.getEmail().substring(0, post.getEmail().indexOf("@"))+".png");
            } else {
                view.hideImage();
            }
            view.displayList(list);
            view.usernameClickable(list.length > 0);
            view.displayContent(post);
        } else {
            view.displayDialog();
        }
    }
}
