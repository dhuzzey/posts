package com.huzzey.mobile.posts.lists;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huzzey.mobile.posts.R;
import com.huzzey.mobile.posts.datatype.Comment;

/**
 * Created by darren.huzzey on 04/05/16.
 */
public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.Holder> {
    private Comment[] list;
    private LayoutInflater inflater;

    public DetailListAdapter(Context context, Comment[] allCommentsByPost) {
        list = allCommentsByPost;
        inflater = LayoutInflater.from(context);
    }

    public static class Holder extends RecyclerView.ViewHolder {
        TextView name;
        TextView email;
        TextView body;

        public Holder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            email = (TextView) itemView.findViewById(R.id.email);
            body = (TextView) itemView.findViewById(R.id.body);
        }
    }

    public void updateData(Comment[] posts) {
        list = posts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(R.layout.activity_detail_sheet_item, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Comment comment = list[position];
        holder.name.setText(comment.getName());
        holder.email.setText(comment.getEmail());
        holder.body.setText(comment.getBody());
    }
}
