package com.huzzey.mobile.posts.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * Created by darren.huzzey on 22/04/16.
 */
public class Misc {
    private final String TAG = getClass().getSimpleName();

    public boolean isDeviceOnline(Context context) {
        // connectivity manager, and the network info to see if the device is online
        try {
            if (((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null) {
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "isDeviceOnline error=" + e.getMessage());
        }
        return false;
    }
}
