package com.huzzey.mobile.posts.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by darren.huzzey on 11/04/16.
 */
public class DatabaseMethods {

    public boolean checkJsonBoolean(JsonObject object, String name){
        return object.has(name) && object.get(name).getAsBoolean();
    }

    public int checkJsonInteger(JsonObject object, String name){
        return object.has(name) ? object.get(name).getAsInt() : 0;
    }

    public String checkJsonString(JsonObject object, String name) {
        return object.has(name) && !object.get(name).isJsonNull() ? object.get(name).getAsString() : "";
    }

    public JsonObject checkJsonObject(JsonObject object, String name){
        return object.has(name) ? object.get(name).getAsJsonObject() : new JsonObject();
    }

    public JsonArray checkJsonArray(JsonObject object, String name){
        return object.has(name) ? object.get(name).getAsJsonArray() : new JsonArray();
    }


}
