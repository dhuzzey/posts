package com.huzzey.mobile.posts.lists;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.huzzey.mobile.posts.AppApplication;
import com.huzzey.mobile.posts.R;
import com.huzzey.mobile.posts.datatype.Post;

/**
 * Created by darren.huzzey on 20/04/16.
 */
public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.Holder> {
    private Post[] list;
    private LayoutInflater inflater;
    private final String URL = AppApplication.getURL();
    private OnClickListener listener;
    private RequestManager manager;

    public MainListAdapter(Context context, Post[]list, OnClickListener listener1) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        listener = listener1;
        manager = Glide.with(context);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView image;

        public Holder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            image = (ImageView) itemView.findViewById(R.id.image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(getAdapterPosition());
        }
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public void updateData(Post[] data) {
        list = data;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(R.layout.activity_main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Post post = list[position];
        holder.title.setText(post.getTitle());
        manager.load(new StringBuilder(URL).append(post.getEmail().substring(0, post.getEmail().indexOf("@"))).append(".png").toString()).placeholder(R.drawable.photo).into(holder.image);
    }

    public interface OnClickListener {
        void onClick(int position);
    }
}
