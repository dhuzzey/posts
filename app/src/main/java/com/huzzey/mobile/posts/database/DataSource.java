package com.huzzey.mobile.posts.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.huzzey.mobile.posts.datatype.Comment;
import com.huzzey.mobile.posts.datatype.Post;
import com.huzzey.mobile.posts.utils.DatabaseMethods;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by darren.huzzey on 11/04/16.
 */
public class DataSource {
    private final String TAG = getClass().getSimpleName();
    private DataSource mDatasource;
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    public DataSource(SQLiteHelper helper) {
        dbHelper = helper;
        //open();
    }


    private void open() throws SQLException {
        if(!database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }
    }

    public String getTables(){
        StringBuilder output = new StringBuilder();
        Cursor cursor = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            output.append(cursor.getString(0)).append(" ");
            cursor.moveToNext();
        }
        cursor.close();
        return output.toString();
    }

    public void close() {
        database.close();
    }

    private String getPostStatement() {
        String sql = new StringBuilder("INSERT INTO ").append(SQLiteHelper.TABLEPOSTS).append(" ( ")
                .append(SQLiteHelper.PO_USERID).append(" , ")
                .append(SQLiteHelper.PO_ID).append(" , ")
                .append(SQLiteHelper.PO_BODY).append(" , ")
                .append(SQLiteHelper.PO_TITLE).append(" ) VALUES (?,?,?,?); ")
                .toString();
        return sql;
    }

    public Post[] getAllPosts(){
        Post[] array = {};
        Cursor cursor = getAllPostsCursor();
        if(cursor.getCount() > 0){
            array = new Post[cursor.getCount()];
            Post post;
            int counter = 0;
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                post = new Post();
                post.setTitle(cursor.getString(0));
                post.setId(cursor.getInt(1));
                post.setUserID(cursor.getInt(2));
                post.setBody(cursor.getString(3));
                post.setEmail(cursor.getString(4));
                post.setUsername(cursor.getString(5));
                array[counter++] = post;
                cursor.moveToNext();
            }
        }
        cursor.close();
        return array;
    }

    public void deletePosts(){
        dbHelper.getWritableDatabase().execSQL("DELETE FROM " + SQLiteHelper.TABLEPOSTS);
    }

    public void deleteComments(){
        dbHelper.getWritableDatabase().execSQL("DELETE FROM " + SQLiteHelper.TABLECOMMENT);
    }

    public void deleteUsers(){
        dbHelper.getWritableDatabase().execSQL("DELETE FROM " + SQLiteHelper.TABLEUSERS);
    }

    public Comment[] getAllCommentsByPost(int post){
        Comment[] list = {};
        Cursor cursor = getComments(post);
        if(cursor.getCount() > 0){
            int counter = 0;
            Comment comment;
            list = new Comment[cursor.getCount()];
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                comment = new Comment();
                comment.setBody(cursor.getString(0));
                comment.setEmail(cursor.getString(1));
                comment.setId(cursor.getInt(2));
                comment.setName(cursor.getString(3));
                comment.setPostID(cursor.getInt(4));
                list[counter++] = comment;
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }

    private Cursor getComments(int post){
        String[] columns = {SQLiteHelper.CM_BODY, SQLiteHelper.CM_EMAIL, SQLiteHelper.CM_ID, SQLiteHelper.CM_NAME, SQLiteHelper.CM_POSTID};
        return dbHelper.getReadableDatabase().query(true, SQLiteHelper.TABLECOMMENT, columns, SQLiteHelper.CM_POSTID + "=?", new String[]{""+post}, null, null, null, null);
    }
    private Cursor getAllPostsCursor(){
        String sql = new StringBuilder("SELECT p.").append(SQLiteHelper.PO_TITLE).append(", ")
                .append(" p.").append(SQLiteHelper.PO_ID).append(", ")
                .append(" p.").append(SQLiteHelper.PO_USERID).append(", ")
                .append(" p.").append(SQLiteHelper.PO_BODY).append(", ")
                .append(" u.").append(SQLiteHelper.US_EMAIL).append(", ")
                .append(" u.").append(SQLiteHelper.US_USERNAME)
                .append(" FROM ").append(SQLiteHelper.TABLEPOSTS).append(" p LEFT JOIN ")
                .append(SQLiteHelper.TABLEUSERS).append(" u ON p.").append(SQLiteHelper.PO_USERID).append(" = u.").append(SQLiteHelper.US_ID).toString();
        return dbHelper.getReadableDatabase().rawQuery(sql, null);
    }
    private String getUserStatement() {
        String sql = new StringBuilder("INSERT INTO ").append(SQLiteHelper.TABLEUSERS).append(" ( ")
                .append(SQLiteHelper.US_ID).append(" , ")
                .append(SQLiteHelper.US_LAT).append(" , ")
                .append(SQLiteHelper.US_LNG).append(" , ")
                .append(SQLiteHelper.US_ZIP).append(" , ")
                .append(SQLiteHelper.US_CITY).append(" , ")
                .append(SQLiteHelper.US_EMAIL).append(" , ")
                .append(SQLiteHelper.US_NAME).append(" , ")
                .append(SQLiteHelper.US_SUITE).append(" , ")
                .append(SQLiteHelper.US_USERNAME).append(", ")
                .append(SQLiteHelper.US_STREET).append(" ) VALUES (?,?,?,?,?,?,?,?,?,?); ")
                .toString();
        return sql;
    }

    private String getCommentStatement() {
        String sql = new StringBuilder("INSERT INTO ").append(SQLiteHelper.TABLECOMMENT).append(" ( ")
                .append(SQLiteHelper.CM_ID).append(" , ")
                .append(SQLiteHelper.CM_POSTID).append(" , ")
                .append(SQLiteHelper.CM_EMAIL).append(" , ")
                .append(SQLiteHelper.CM_BODY).append(" , ")
                .append(SQLiteHelper.CM_NAME).append(" ) VALUES (?,?,?,?,?); ")
                .toString();
        return sql;
    }

    public void addComments(JsonArray list){
        Log.w(TAG, "addComments ");
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();
        SQLiteStatement commentStatement = database.compileStatement(getCommentStatement());
        DatabaseMethods utils = new DatabaseMethods();
        JsonObject comment;
        for(int i = 0; i < list.size(); i++){
            comment = list.get(i).getAsJsonObject();
            commentStatement.clearBindings();
            commentStatement.bindLong(1, utils.checkJsonInteger(comment, "id"));
            commentStatement.bindLong(2, utils.checkJsonInteger(comment, "postId"));
            commentStatement.bindString(3, utils.checkJsonString(comment, "email"));
            commentStatement.bindString(4, utils.checkJsonString(comment, "body"));
            commentStatement.bindString(5, utils.checkJsonString(comment, "name"));
            commentStatement.execute();
        }

        commentStatement.close();
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void addPosts(JsonArray list) {
        Log.w(TAG, "addPosts ");
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();
        SQLiteStatement postStatement = database.compileStatement(getPostStatement());
        DatabaseMethods utils = new DatabaseMethods();
        JsonObject post;
        for(int i = 0; i < list.size(); i++){
            post = list.get(i).getAsJsonObject();
            postStatement.clearBindings();
            postStatement.bindLong(1, utils.checkJsonInteger(post, "userId"));
            postStatement.bindLong(2, utils.checkJsonInteger(post, "id"));
            postStatement.bindString(3, utils.checkJsonString(post, "body"));
            postStatement.bindString(4, utils.checkJsonString(post, "title"));
            postStatement.execute();
        }

        postStatement.close();
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public String[] addUsers(JsonArray list){
        Log.w(TAG, "addUsers ");
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.beginTransaction();
        SQLiteStatement userStatement = database.compileStatement(getUserStatement());
        DatabaseMethods utils = new DatabaseMethods();
        JsonObject user;
        JsonObject address;
        JsonObject geo;
        List<String> emailList = new ArrayList<>();
        String email;
        for(int i = 0; i < list.size(); i++){
            user = list.get(i).getAsJsonObject();
            address = utils.checkJsonObject(user, "address");
            geo = utils.checkJsonObject(address, "geo");
            email = utils.checkJsonString(user, "email");
            emailList.add(email);
            userStatement.clearBindings();
            userStatement.bindLong(1, utils.checkJsonInteger(user, "id"));
            userStatement.bindString(2, utils.checkJsonString(geo, "lat"));
            userStatement.bindString(3, utils.checkJsonString(geo, "lng"));
            userStatement.bindString(4, utils.checkJsonString(address, "zip"));
            userStatement.bindString(5, utils.checkJsonString(address, "city"));
            userStatement.bindString(6, email);
            userStatement.bindString(7, utils.checkJsonString(user, "name"));
            userStatement.bindString(8, utils.checkJsonString(address, "suite"));
            userStatement.bindString(9, utils.checkJsonString(user, "username"));
            userStatement.bindString(10, utils.checkJsonString(address, "street"));
            userStatement.execute();
        }
        userStatement.close();
        database.setTransactionSuccessful();
        database.endTransaction();
        emailList = new ArrayList<>(new LinkedHashSet<>(emailList));
        return emailList.toArray(new String[emailList.size()]);
    }
}
