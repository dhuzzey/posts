package com.huzzey.mobile.posts.activities.splash;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.huzzey.mobile.posts.api.DataProcess;

/**
 * Created by darren.huzzey on 17/05/16.
 */
public class SplashPresenter implements SplashContract.Action {
    private SplashContract.View view;
    private DataProcess process;

    public SplashPresenter(SplashContract.View view, DataProcess dataProcess) {
        this.view = view;
        process = dataProcess;
    }

    @Override
    public void networkCall() {
        callAPI();
    }


    private void callAPI(){
        process.startProcess(new DataProcess.DataProcessResponse() {
            @Override
            public void downloadSuccessful() {
                view.pushToMain();
            }
        });
    }
}
