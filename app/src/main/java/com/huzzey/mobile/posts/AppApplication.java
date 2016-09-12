package com.huzzey.mobile.posts;

import android.app.Application;
import android.os.Environment;

import com.huzzey.mobile.posts.dagger.AppComponent;
import com.huzzey.mobile.posts.dagger.AppModule;
import com.huzzey.mobile.posts.dagger.DaggerAppComponent;
import com.huzzey.mobile.posts.dagger.NetworkModule;

/**
 * Created by darren.huzzey on 20/04/16.
 */
public class AppApplication extends Application {
    private static AppComponent component;
    private static String URL;

    @Override
    public void onCreate() {
        super.onCreate();
        URL = new StringBuilder().append("").append(Environment.getExternalStorageDirectory()).append("/data/").append(getApplicationContext().getPackageName()).append("/images/").toString();
        component = DaggerAppComponent.builder().appModule(new AppModule(this))
                .networkModule(new NetworkModule(this)).build();
    }

    public static AppComponent getComponent() {
        return component;
    }

    public static String getURL() {
        return URL;
    }
}
