package com.huzzey.mobile.posts.activities.detail;

import com.huzzey.mobile.posts.datatype.Comment;
import com.huzzey.mobile.posts.datatype.Post;

/**
 * Created by darren.huzzey on 06/05/16.
 */
public interface DetailActivityView {
    interface View {
        void displayContent(Post post);
        void displayImage(String image);
        void displayList(Comment[] list);
        void hideImage();

        void usernameClickable(boolean clickable);
        void displayDialog();
        void setActionBarTitle(int title);
    }

    interface Action {
        void populateScreen();
    }
}
