package com.huzzey.mobile.posts.activities;

import com.huzzey.mobile.posts.activities.main.MainActivityPresenter;
import com.huzzey.mobile.posts.activities.main.MainActivityView;
import com.huzzey.mobile.posts.database.DataSource;
import com.huzzey.mobile.posts.datatype.Post;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by darren.huzzey on 04/05/16.
 */
public class MainActivityPresenterTest {
    @Mock MainActivityView.View view;
    @Mock DataSource datasource;

    MainActivityPresenter presenter;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new MainActivityPresenter(view, datasource);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testLoadListToScreen() throws Exception {
        Post[] list = new Post[0];
        when(datasource.getAllPosts()).thenReturn(list);
        presenter.populateData();

        verify(view).loadDataToList(list);
    }

    @Test
    public void testClickItemInList() throws Exception {
        Post[] list = new Post[1];
        list[0] = new Post();
        when(datasource.getAllPosts()).thenReturn(list);
        presenter.populateData();
        verify(view).loadDataToList(list);
        presenter.openDetail(0);
        verify(view).moveToDetail(list[0]);
    }
}