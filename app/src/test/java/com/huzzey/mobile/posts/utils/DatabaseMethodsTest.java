package com.huzzey.mobile.posts.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by darren.huzzey on 13/04/16.
 */
public class DatabaseMethodsTest {
    private JsonArray array;
    private JsonObject object;
    DatabaseMethods methods;

    @Before
    public void setUp() throws Exception {
        methods = new DatabaseMethods();
        object = new JsonObject();
        array = new JsonArray();
    }

    @Test
    public void testString_Correct() throws Exception {
        object.addProperty("test","test");
        assertTrue(methods.checkJsonString(object, "test").equals("test"));
    }

    @Test
    public void testString_InCorrect() throws Exception {
        object.addProperty("test1","test");
        assertFalse(methods.checkJsonString(object, "test").equals("test"));
    }

    @Test
    public void testString_isEmpty() throws Exception {
        assertFalse(methods.checkJsonString(object, "test").equals("test"));
    }

    @Test
    public void testJsonObject_Correct() throws Exception {
        JsonObject wrapper = new JsonObject();
        object.addProperty("test", "test");
        wrapper.add("object", object);
        assertEquals(methods.checkJsonObject(wrapper, "object").get("test"), object.get("test"));
    }

    @Test
    public void testJsonObject_IsEmpty() throws Exception {
        JsonObject wrapper = new JsonObject();
        assertTrue(methods.checkJsonObject(wrapper, "object").entrySet().isEmpty());
    }

    @Test
    public void testInt_IsEmpty() throws Exception {
        assertEquals(0, methods.checkJsonInteger(object, "test"));
    }

    @Test
    public void testInt_IsCorrect() throws Exception {
        object.addProperty("test", 1);
        assertTrue(methods.checkJsonInteger(object, "test") == 1);
    }

    @Test
    public void testJsonArray_IsEmpty() throws Exception {
        assertEquals(0, methods.checkJsonArray(object, "test").size());
    }

    @Test
    public void testJsonArray_IsCorrect() throws Exception {
        object.add("test", array);
        assertEquals(array, methods.checkJsonArray(object, "test"));
    }

    @Test
    public void testBoolean_IsCorrect() throws Exception {
        object.addProperty("test", true);
        assertEquals(array, methods.checkJsonArray(object, "test"));
    }




}