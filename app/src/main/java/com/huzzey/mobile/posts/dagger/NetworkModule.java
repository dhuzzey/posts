package com.huzzey.mobile.posts.dagger;

import android.app.Application;

import com.huzzey.mobile.posts.AppApplication;
import com.huzzey.mobile.posts.api.VolleyHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by darren.huzzey on 12/09/2016.
 */
@Module
public class NetworkModule {
    private final VolleyHelper helper;

    public NetworkModule(Application app) {
        helper = new VolleyHelper(app);
    }

    @Provides
    @Singleton
    VolleyHelper providesVolleyHelper(){
        return helper;
    }
}
