package com.example.administrator.myuniversaltoolapplication.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.entity.Comment;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by y8042 on 2016/5/23.
 */
public class DetailPostRecyclerAdapter extends RecyclerView.Adapter<DetailPostRecyclerAdapter.MyViewHolder> {
    private List<Comment> commentDatasList;
    private Context context;

    public DetailPostRecyclerAdapter(Context context, List<Comment> commentDatasList) {
        this.commentDatasList = commentDatasList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_detailpost, viewGroup, false);//后面这个false不能少
        MyViewHolder myViewHolder = new MyViewHolder(context, view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Comment commentDatas = commentDatasList.get(position);
        Picasso.with(context).load(commentDatas.getUser().getAvater().getFileUrl(context)).into(holder.iv_avater);
        holder.tv_comment.setText(commentDatas.getContent());

    }

    @Override
    public int getItemCount() {
        return commentDatasList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_comment;
        private ImageView iv_avater;

        public MyViewHolder(Context context, View itemView) {
            super(itemView);

            iv_avater = (ImageView) itemView.findViewById(R.id.detailpost_item_iv_avater);
            tv_comment = (TextView) itemView.findViewById(R.id.detailpost_item_tv_comment);
        }
    }

}
