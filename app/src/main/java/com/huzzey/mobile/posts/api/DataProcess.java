package com.huzzey.mobile.posts.api;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonArray;
import com.huzzey.mobile.posts.AppApplication;
import com.huzzey.mobile.posts.BuildConfig;
import com.huzzey.mobile.posts.database.DataSource;
import com.huzzey.mobile.posts.utils.Misc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.inject.Inject;

/**
 * Created by darren.huzzey on 12/04/16.
 */
public class DataProcess {
    private final String LOG = getClass().getSimpleName();
    public String ROOT_PATH;
    private boolean imagesDownloaded;
    private boolean dataDownloaded;
    private DataProcessResponse response;

    @Inject DataSource dataSource;
    @Inject VolleyHelper helper;
    @Inject Application context;


    public DataProcess(){
        AppApplication.getComponent().inject(this);
        ROOT_PATH = AppApplication.getURL();
    }

    public void startProcess(DataProcessResponse response){
        this.response = response;
        if(new Misc().isDeviceOnline(context)) {
            imagesDownloaded = false;
            imagesDownloaded = false;
            getUserData();
        } else {
            completeProcess();
        }
    }

    private void getUserData(){
        String url = BuildConfig.Base_Url + "users";
        GsonRequest<JsonArray> request = new GsonRequest<>(url, JsonArray.class, null, new Response.Listener<JsonArray>() {
            @Override
            public void onResponse(JsonArray response) {
                Log.w(LOG, "response " + response);
                dataSource.deleteUsers();
                Log.w(LOG, "deleteUsers ");
                String[] list = dataSource.addUsers(response);
                Log.w(LOG, "addUsers ");
                if(ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    new DownloadImages().execute(list);
                }
                getCommentsData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getCommentsData();
            }
        });
        helper.addToRequestQueue(request);
    }

    private void getCommentsData(){
        String url = BuildConfig.Base_Url + "comments";
        GsonRequest<JsonArray> request = new GsonRequest<>(url, JsonArray.class, null, new Response.Listener<JsonArray>() {
            @Override
            public void onResponse(JsonArray response) {
                dataSource.deleteComments();
                dataSource.addComments(response);
                getPostsData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getPostsData();
            }
        });
        helper.addToRequestQueue(request);
    }

    private void getPostsData(){
        String url = BuildConfig.Base_Url + "posts";
        GsonRequest<JsonArray> request = new GsonRequest<>(url, JsonArray.class, null, new Response.Listener<JsonArray>() {
            @Override
            public void onResponse(JsonArray response) {
                dataSource.deletePosts();
                dataSource.addPosts(response);
                dataDownloaded = true;
                completeProcess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dataDownloaded = true;
                completeProcess();
            }
        });
        helper.addToRequestQueue(request);
    }

    private void completeProcess(){
        Log.w(LOG, "imagesDownloaded " + imagesDownloaded + " dataDownloaded " + dataDownloaded);
        if(imagesDownloaded && dataDownloaded) {
            response.downloadSuccessful();
        }
    }

    private class DownloadImages extends AsyncTask<String[], String, String>{
        @Override
        protected String doInBackground(String[]... params) {
            if (params.length > 0) {
                if (params[0].length > 0) {
                    for (String email : params[0]) {
                        getImage(new StringBuilder(BuildConfig.Image_Url).append(email).append("@adorable.png").toString(),
                                email.substring(0, email.indexOf("@")) + ".png");
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            imagesDownloaded = true;
            completeProcess();
        }

        private boolean getImage(String fileURL, String fileName){
            int aReasonableSize = 1024;
            boolean fileDownloaded = false;

            new File(ROOT_PATH).mkdirs();
            if(new File(ROOT_PATH + fileName).exists()) {
                return fileDownloaded;
            } else {
                InputStream input = null;
                URL url = null;
                try {
                    url = new URL(fileURL);
                    input = new BufferedInputStream(url.openStream());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(url != null && input != null){
                    BufferedOutputStream outputStream = null;
                    try {
                        File currentFile = new File(ROOT_PATH, fileName);
                        outputStream = new BufferedOutputStream(new FileOutputStream(currentFile));
                        byte[] buffer = new byte[aReasonableSize];
                        // number of bytes downloaded
                        int bytesRead;
                        // loop through the file file until there's nothing left to read, writing the bytes to the file
                        while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        fileDownloaded = true;
                    } catch (Exception e){
                        e.printStackTrace();
                    } finally {
                        try {
                            outputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    try{
                        input.close();
                    } catch (Exception e){ e.printStackTrace();}
                }
            }

            return fileDownloaded;
        }
    }

    public interface DataProcessResponse {
        void downloadSuccessful();
    }
}
