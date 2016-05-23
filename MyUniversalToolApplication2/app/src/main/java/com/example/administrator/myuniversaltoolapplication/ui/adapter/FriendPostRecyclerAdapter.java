package com.example.administrator.myuniversaltoolapplication.ui.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.entity.Post;
import com.example.administrator.myuniversaltoolapplication.ui.view.MyGridLayoutManager;
import com.example.administrator.myuniversaltoolapplication.utils.DateUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/2/18.
 */
public class FriendPostRecyclerAdapter extends RecyclerView.Adapter<FriendPostRecyclerAdapter.MyViewHolder> {
    private List<Post> postDatasList;
    private Context context;
    private FriendPostRecyclerPhotoAdapter friendPostRecyclerPhotoAdapter;

    private OnRecyclerViewItemClickListener mOnItemClickListener;
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public FriendPostRecyclerAdapter(Context context, List<Post> postDatasList) {
        this.postDatasList = postDatasList;
        this.context = context;
    }

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public FriendPostRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_friendpost, viewGroup, false);//后面这个false不能少
        MyViewHolder myViewHolder = new MyViewHolder(context, view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final FriendPostRecyclerAdapter.MyViewHolder holder, int position) {
        Post postDatas = postDatasList.get(position);
        Picasso.with(context).load(postDatas.getAuthor().getAvater().getFileUrl(context)).into(holder.iv_avater);

        if (null != postDatas.getImgfilestr()) {
            holder.rv_photo.setVisibility(View.VISIBLE);//viewhodler里做了判断，一定记得反过来做一遍。下面做了隐藏，这里就一定要做显示
            ArrayList<BmobFile> bmobFilesList = new ArrayList<BmobFile>();
            for (int i = 0; i < postDatas.getImgfilestr().length; i++) {
                bmobFilesList.add(postDatas.getImgfilestr()[i]);
            }
            // 创建一个Grid布局管理器
            MyGridLayoutManager myGridLayoutManager =new MyGridLayoutManager(context,3);
            // 设置布局管理器
            holder.rv_photo.setLayoutManager(myGridLayoutManager);
            friendPostRecyclerPhotoAdapter = new FriendPostRecyclerPhotoAdapter(context, bmobFilesList);
            holder.rv_photo.setAdapter(friendPostRecyclerPhotoAdapter);
        } else {
            holder.rv_photo.setVisibility(View.GONE);//如果没有图片列表，就隐藏掉gridview
        }
        holder.tv_name.setText(postDatas.getAuthor().getUsername());//获取作者的用户名，作者是个对象
        holder.tv_content.setText(postDatas.getContent());
        holder.tv_commentNum.setText(postDatas.getCommentNum().toString());
        holder.tv_likeNum.setText(postDatas.getLikeNum().toString());
        holder.tv_creattime.setText(DateUtils.getTimestampString(postDatas.getCreatTime()));


        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    int position = holder.getPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return postDatasList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_name, tv_content, tv_commentNum, tv_likeNum, tv_creattime;
        private ImageView iv_avater;
        private RecyclerView rv_photo;


        public MyViewHolder(Context context, View itemView) {
            super(itemView);
            iv_avater = (ImageView) itemView.findViewById(R.id.friendpostfragment_item_iv_avater);
            rv_photo = (RecyclerView) itemView.findViewById(R.id.friendpostfragment_item_rv_photo);
            tv_name = (TextView) itemView.findViewById(R.id.friendpostfragment_item_tv_name);
            tv_content = (TextView) itemView.findViewById(R.id.friendpostfragment_item_tv_content);
            tv_commentNum = (TextView) itemView.findViewById(R.id.friendpostfragment_item_tv_commentNum);
            tv_likeNum = (TextView) itemView.findViewById(R.id.friendpostfragment_item_tv_likeNum);
            tv_creattime = (TextView) itemView.findViewById(R.id.friendpostfragment_item_tv_creattime);
        }
    }

}
