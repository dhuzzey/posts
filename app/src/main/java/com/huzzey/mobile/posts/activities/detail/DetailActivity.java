package com.huzzey.mobile.posts.activities.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huzzey.mobile.posts.AppApplication;
import com.huzzey.mobile.posts.R;
import com.huzzey.mobile.posts.database.DataSource;
import com.huzzey.mobile.posts.datatype.Comment;
import com.huzzey.mobile.posts.datatype.Post;
import com.huzzey.mobile.posts.lists.DetailListAdapter;

import javax.inject.Inject;

/**
 * Created by darren.huzzey on 22/04/16.
 */
public class DetailActivity extends AppCompatActivity implements DetailActivityView.View {
    private final String LOG = getClass().getSimpleName();
    private BottomSheetBehavior behavior;
    private DetailActivityView.Action presenter;

    private TextView title;
    private TextView body;
    private TextView username;
    private ImageView image;
    private DetailListAdapter adapter;

    @Inject
    DataSource dataSource;

    public DetailActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        AppApplication.getComponent().inject(this);

        Post post = getIntent().getParcelableExtra("post");
        presenter = new DetailActivityPresenter(this, post , dataSource, AppApplication.getURL());
        title = (TextView)findViewById(R.id.title);
        body = (TextView)findViewById(R.id.body);
        username = (TextView)findViewById(R.id.username);
        image = (ImageView)findViewById(R.id.image);

        behavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheet));
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.sheetList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DetailListAdapter(this, new Comment[0]);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setActionBarTitle(int title) {
        setTitle(getString(title));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.populateScreen();
    }

    @Override
    public void displayContent(Post post) {
        title.setText(post.getTitle());
        body.setText(post.getBody());
        username.setText(post.getUsername());
    }

    @Override
    public void displayImage(String image) {
        this.image.setVisibility(View.VISIBLE);
        Glide.with(this).load(image).into(this.image);
    }

    @Override
    public void hideImage() {
        image.setVisibility(View.GONE);
    }

    @Override
    public void displayList(Comment[] list) {
        adapter.updateData(list);
    }

    @Override
    public void usernameClickable(boolean clickable) {
        if(clickable) {
            username.setOnClickListener(clickListener());
        }
    }

    @Override
    public void displayDialog() {

    }

    private View.OnClickListener clickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        };
    }
}
