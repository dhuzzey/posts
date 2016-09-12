package com.huzzey.mobile.posts.datatype;

/**
 * Created by darren.huzzey on 04/05/16.
 */
public class Comment {
    private int postID;
    private int id;
    private String name;
    private String email;
    private String body;

    public Comment() {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "body='" + body + '\'' +
                ", postID=" + postID +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
