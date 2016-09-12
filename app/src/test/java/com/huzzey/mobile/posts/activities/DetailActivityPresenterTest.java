package com.huzzey.mobile.posts.activities;

import com.huzzey.mobile.posts.activities.detail.DetailActivityPresenter;
import com.huzzey.mobile.posts.activities.detail.DetailActivityView;
import com.huzzey.mobile.posts.database.DataSource;
import com.huzzey.mobile.posts.datatype.Comment;
import com.huzzey.mobile.posts.datatype.Post;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by darren.huzzey on 06/05/16.
 */
public class DetailActivityPresenterTest {
    @Mock private DetailActivityView.View view;
    @Mock private DataSource dataSource;

    private DetailActivityPresenter presenter;
    private Post post;
    private Comment[] list;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        post = new Post();
        list = new Comment[0];
    }

    @Test
    public void testValidPostDisplayNoEmail() throws Exception {
        post.setId(0);
        when(dataSource.getAllCommentsByPost(post.getId())).thenReturn(list);
        presenter = new DetailActivityPresenter(view, post, dataSource, null);
        presenter.populateScreen();
        verify(view).displayContent(post);
        verify(view).usernameClickable(dataSource.getAllCommentsByPost(post.getId()).length > 0);
        verify(view).displayList(dataSource.getAllCommentsByPost(post.getId()));
        verify(view).hideImage();
    }

    @Test
    public void testValidPostDisplayWithEmail() throws Exception {
        post.setId(0);
        post.setEmail("anothortest@test.com");
        when(dataSource.getAllCommentsByPost(post.getId())).thenReturn(list);
        presenter = new DetailActivityPresenter(view, post, dataSource, null);
        presenter.populateScreen();
        verify(view).displayContent(post);
        verify(view).usernameClickable(dataSource.getAllCommentsByPost(post.getId()).length > 0);
        verify(view).displayList(dataSource.getAllCommentsByPost(post.getId()));
        verify(view).displayImage("nullanothortest.png");
    }

    @Test
    public void testEmptyPost() throws Exception {
        when(dataSource.getAllCommentsByPost(post.getId())).thenReturn(list);
        presenter = new DetailActivityPresenter(view, null, dataSource, null);
        presenter.populateScreen();
        verify(view).displayDialog();
    }
}