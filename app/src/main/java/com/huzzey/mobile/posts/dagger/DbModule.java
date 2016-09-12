package com.huzzey.mobile.posts.dagger;

import android.app.Application;

import com.huzzey.mobile.posts.AppApplication;
import com.huzzey.mobile.posts.database.DataSource;
import com.huzzey.mobile.posts.database.SQLiteHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by darren.huzzey on 12/09/2016.
 */
@Module
public class DbModule {

    @Provides
    @Singleton
    SQLiteHelper providesSQLiteHelper(Application application) {
        return new SQLiteHelper(application);
    }

    @Provides
    @Singleton
    DataSource providesDataSource(SQLiteHelper helper){
        return new DataSource(helper);
    }
}
