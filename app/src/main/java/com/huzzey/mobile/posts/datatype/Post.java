package com.huzzey.mobile.posts.datatype;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by darren.huzzey on 21/04/16.
 */
public class Post implements Parcelable{
    private String title;
    private String body;
    private String email;
    private String username;
    private int userID;
    private int id;

    public Post() {
    }

    public Post(Parcel in){
        title = in.readString();
        body = in.readString();
        email = in.readString();
        username = in.readString();
        userID = in.readInt();
        id = in.readInt();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(body);
        dest.writeString(email);
        dest.writeString(username);
        dest.writeInt(userID);
        dest.writeInt(id);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        public Post[] newArray(int size){
            return new Post[size];
        }
    };

    @Override
    public String toString() {
        return "Post{" +
                "body='" + body + '\'' +
                ", title='" + title + '\'' +
                ", email='" + email + '\'' +
                ", userID=" + userID +
                ", id=" + id +
                '}';
    }
}
