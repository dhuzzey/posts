package com.huzzey.mobile.posts.activities.splash;

/**
 * Created by darren.huzzey on 17/05/16.
 */
public interface SplashContract {

    interface View {
        void pushToMain();
    }

    interface Action {
        void networkCall();
    }
}
