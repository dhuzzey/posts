package com.huzzey.mobile.posts.dagger;

import android.app.Application;

import com.huzzey.mobile.posts.AppApplication;
import com.huzzey.mobile.posts.api.VolleyHelper;
import com.huzzey.mobile.posts.database.DataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by darren.huzzey on 18/04/16.
 */
@Module(includes = {NetworkModule.class, DbModule.class,})
public class AppModule {
    private final Application application;

    public AppModule(AppApplication app){
        this.application = app;
    }

    @Provides
    @Singleton
    Application providesApplication(){
        return application;
    }

}
