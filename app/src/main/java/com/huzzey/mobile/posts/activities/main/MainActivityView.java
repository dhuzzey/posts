package com.huzzey.mobile.posts.activities.main;

import android.support.annotation.NonNull;

import com.huzzey.mobile.posts.datatype.Post;

/**
 * Created by darren.huzzey on 11/04/16.
 */
public interface MainActivityView {
    interface View {
        void loadDataToList(Post[] list);
        void setActionBarTitle(int title);
        void moveToDetail(Post post);
    }

    interface ActionsListener {
        void populateData();
        void openDetail(@NonNull int position);
        void onDestroy();
    }
}
