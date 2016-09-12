package com.huzzey.mobile.posts.activities.splash;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.huzzey.mobile.posts.R;
import com.huzzey.mobile.posts.activities.main.MainActivity;
import com.huzzey.mobile.posts.api.DataProcess;

/**
 * Created by darren.huzzey on 20/04/16.
 */
public class SplashActivity extends AppCompatActivity implements SplashContract.View {
    private final String TAG = getClass().getSimpleName();
    private SplashPresenter presenter;

    public SplashActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        presenter = new SplashPresenter(this, new DataProcess());
        checkStoragePermissions(this);
    }

    @Override
    public void pushToMain() {
        Log.w(TAG, "pushToMain()");
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 0 ){
            presenter.networkCall();
        }
    }

    public void checkStoragePermissions(Context context){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                //display popup
                ActivityCompat.requestPermissions((Activity)context, new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            } else {
                ActivityCompat.requestPermissions((Activity)context, new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
        } else {
            presenter.networkCall();
        }
    }
}
