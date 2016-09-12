package com.huzzey.mobile.posts.activities.main;

import android.support.annotation.NonNull;

import com.huzzey.mobile.posts.R;
import com.huzzey.mobile.posts.database.DataSource;
import com.huzzey.mobile.posts.datatype.Post;

/**
 * Created by darren.huzzey on 11/04/16.
 */
public class MainActivityPresenter implements MainActivityView.ActionsListener{

    private MainActivityView.View view;
    private Post[] postArray;
    private DataSource datasource;

    public MainActivityPresenter(MainActivityView.View view, DataSource datasource) {
        this.view = view;
        this.datasource = datasource;
        view.setActionBarTitle(R.string.postListTitle);
    }

    @Override
    public void populateData() {
        postArray = datasource.getAllPosts();
        view.loadDataToList(postArray);
    }


    @Override
    public void openDetail(@NonNull int position) {
        Post post = postArray[position];
        view.moveToDetail(post);
    }

    @Override
    public void onDestroy() {
        postArray = null;
        datasource = null;
        view = null;
    }
}
