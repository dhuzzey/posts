package com.huzzey.mobile.posts.dagger;

import com.huzzey.mobile.posts.activities.detail.DetailActivity;
import com.huzzey.mobile.posts.activities.main.MainActivity;
import com.huzzey.mobile.posts.api.DataProcess;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by darren.huzzey on 20/04/16.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(DetailActivity activity);
    void inject(DataProcess process);
}
