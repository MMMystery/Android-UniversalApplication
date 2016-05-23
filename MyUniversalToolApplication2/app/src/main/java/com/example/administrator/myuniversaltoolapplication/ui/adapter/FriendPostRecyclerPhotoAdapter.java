package com.example.administrator.myuniversaltoolapplication.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.administrator.myuniversaltoolapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/2/21.
 */
public class FriendPostRecyclerPhotoAdapter extends RecyclerView.Adapter<FriendPostRecyclerPhotoAdapter.MyViewHolder> {
    private ArrayList<BmobFile> bmobFilesList;
    private Context context;

    public FriendPostRecyclerPhotoAdapter( Context context, ArrayList<BmobFile> bmobFilesList) {
        this.context = context;
        this.bmobFilesList = bmobFilesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_friendpost_photos, viewGroup, false);//后面这个false不能少
        MyViewHolder myViewHolder = new MyViewHolder(context, view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Picasso.with(context).load(bmobFilesList.get(position).getFileUrl(context)).into(holder.iv_photo);
    }

    @Override
    public int getItemCount() {
        return bmobFilesList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_photo;

        public MyViewHolder(Context context, View itemView) {
            super(itemView);
            iv_photo = (ImageView) itemView.findViewById(R.id.friendpostfragmentphoto_item_iv_photo);
        }
    }
}
