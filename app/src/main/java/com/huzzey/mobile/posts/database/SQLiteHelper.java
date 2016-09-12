package com.huzzey.mobile.posts.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by darren.huzzey on 11/04/16.
 */
public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Posts.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLEUSERS = "users";
    public static final String US_ID = "id";
    public static final String US_NAME = "name";
    public static final String US_USERNAME = "username";
    public static final String US_EMAIL = "email";
    public static final String US_STREET = "street";
    public static final String US_SUITE = "suite";
    public static final String US_CITY = "city";
    public static final String US_ZIP = "zipCode";
    public static final String US_LAT = "lit";
    public static final String US_LNG = "lng";

    public static final String TABLECOMMENT = "comments";
    public static final String CM_POSTID = "postID";
    public static final String CM_ID = "id";
    public static final String CM_NAME = "name";
    public static final String CM_EMAIL = "email";
    public static final String CM_BODY = "body";

    public static final String TABLEPOSTS = "posts";
    public static final String PO_USERID = "userId";
    public static final String PO_ID = "id";
    public static final String PO_TITLE = "title";
    public static final String PO_BODY = "body";

    private final String CREATE_TABLE       = "CREATE TABLE IF NOT EXISTS ";
    private final String CREATE_INDEX       = " CREATE INDEX ";
    private final String FIELDTYPEINTEGER   = " INTEGER DEFAULT 0 ";
    private final String FIELDTYPETEXT      = " TEXT DEFAULT NULL ";

    private final String CREATE_USERS = new StringBuilder(CREATE_TABLE)
            .append(TABLEUSERS) .append(" ( ")
            .append(US_ID)      .append(FIELDTYPEINTEGER).append(", ")
            .append(US_NAME)    .append(FIELDTYPETEXT).append(", ")
            .append(US_USERNAME).append(FIELDTYPETEXT).append(", ")
            .append(US_EMAIL)   .append(FIELDTYPETEXT).append(", ")
            .append(US_STREET)  .append(FIELDTYPETEXT).append(", ")
            .append(US_SUITE)   .append(FIELDTYPETEXT).append(", ")
            .append(US_CITY)    .append(FIELDTYPETEXT).append(", ")
            .append(US_ZIP)     .append(FIELDTYPETEXT).append(", ")
            .append(US_LAT)     .append(FIELDTYPETEXT).append(", ")
            .append(US_LNG)     .append(FIELDTYPETEXT).append("); ")
            .append(CREATE_INDEX).append(TABLEUSERS).append("_idx ON ").append(TABLEUSERS).append(" ( ").append(US_ID).append(" );").toString();

    private final String CREATE_COMMENT = new StringBuilder(CREATE_TABLE)
            .append(TABLECOMMENT).append(" ( ")
            .append(CM_POSTID)  .append(FIELDTYPEINTEGER).append(" , ")
            .append(CM_ID)      .append(FIELDTYPEINTEGER).append(" , ")
            .append(CM_NAME)    .append(FIELDTYPETEXT).append(" , ")
            .append(CM_EMAIL)   .append(FIELDTYPETEXT).append(" , ")
            .append(CM_BODY)    .append(FIELDTYPETEXT).append(" ); ")
            .append(CREATE_INDEX).append(TABLECOMMENT).append("_idx ON ").append(TABLECOMMENT).append(" ( ").append(CM_ID).append("); ")
            .append(CREATE_INDEX).append(TABLECOMMENT).append("_postIdx ON ").append(TABLECOMMENT).append(" ( ").append(CM_POSTID).append("); ").toString();

    private final String CREATE_POSTS = new StringBuilder(CREATE_TABLE)
            .append(TABLEPOSTS).append(" ( ")
            .append(PO_ID).append(FIELDTYPEINTEGER).append(" , ")
            .append(PO_USERID).append(FIELDTYPEINTEGER).append(" , ")
            .append(PO_TITLE).append(FIELDTYPETEXT).append(" , ")
            .append(PO_BODY).append(FIELDTYPETEXT).append(" ); ")
            .append(CREATE_INDEX).append(TABLEPOSTS).append("_idx ON ").append(TABLEPOSTS).append(" ( ").append(PO_ID).append(" ); ")
            .append(CREATE_INDEX).append(TABLEPOSTS).append("_userIdx ON ").append(TABLEPOSTS).append(" ( ").append(PO_USERID).append(" ); ").toString();

    public SQLiteHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createTables(SQLiteDatabase db){
        String[] tableList = {  CREATE_POSTS,
                                CREATE_COMMENT,
                                CREATE_USERS};

        for (String table: tableList){
            try {
                db.execSQL(table);
            } catch (Exception e){;}
        }
    }
}
