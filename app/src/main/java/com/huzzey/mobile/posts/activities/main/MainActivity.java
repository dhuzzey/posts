package com.huzzey.mobile.posts.activities.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.huzzey.mobile.posts.AppApplication;
import com.huzzey.mobile.posts.R;
import com.huzzey.mobile.posts.activities.detail.DetailActivity;
import com.huzzey.mobile.posts.database.DataSource;
import com.huzzey.mobile.posts.datatype.Post;
import com.huzzey.mobile.posts.lists.MainListAdapter;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainActivityView.View {
    private MainActivityView.ActionsListener presenter;
    private Context context;
    private MainListAdapter adapter;

    @Inject
    DataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppApplication.getComponent().inject(this);
        context = this;
        setContentView(R.layout.activity_main);

        RecyclerView recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
        recyclerList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new MainListAdapter(context, new Post[0], clickListener());
        recyclerList.setAdapter(adapter);
        presenter = new MainActivityPresenter(this, dataSource);
    }

    @Override
    public void setActionBarTitle(int title) {
        setTitle(getString(title));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.populateData();
    }

    private MainListAdapter.OnClickListener clickListener(){
        return new MainListAdapter.OnClickListener() {
            @Override
            public void onClick(int position) {
                presenter.openDetail(position);
            }
        };
    }

    @Override
    public void loadDataToList(Post[] list) {
        adapter.updateData(list);
    }

    @Override
    public void moveToDetail(Post post) {
        context.startActivity(new Intent(context, DetailActivity.class).putExtra("post", post));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
