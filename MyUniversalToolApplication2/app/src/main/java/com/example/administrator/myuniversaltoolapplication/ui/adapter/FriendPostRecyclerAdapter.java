package com.example.administrator.myuniversaltoolapplication.ui.adapter;


import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.entity.Post;
import com.example.administrator.myuniversaltoolapplication.ui.fragment.FriendPostFragment;
import com.example.administrator.myuniversaltoolapplication.utils.DateUtils;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/2/18.
 */
public class FriendPostRecyclerAdapter extends RecyclerView.Adapter<FriendPostRecyclerAdapter.MyViewHolder> implements View.OnClickListener {
    private List<Post> postDatasList;
    private Context context;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public FriendPostRecyclerAdapter(Context context, List<Post> postDatasList) {
        this.postDatasList = postDatasList;
        this.context = context;
    }

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    @Override
    public FriendPostRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_friendpost, viewGroup, false);//后面这个false不能少
        MyViewHolder myViewHolder = new MyViewHolder(context, view);

        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(FriendPostRecyclerAdapter.MyViewHolder holder, int position) {
        Post postDatas = postDatasList.get(position);
        Picasso.with(context).load(postDatas.getAuthor().getAvater().getFileUrl(context)).into(holder.iv_avater);

        if (null != postDatas.getImgfilestr()) {
            holder.gv_photo.setVisibility(View.VISIBLE);//viewhodler里做了判断，一定记得反过来做一遍。下面做了隐藏，这里就一定要做显示
            ArrayList<BmobFile> bmobFilesList = new ArrayList<BmobFile>();
            for (int i = 0; i < postDatas.getImgfilestr().length; i++) {
                bmobFilesList.add(postDatas.getImgfilestr()[i]);
            }
//            Logger.d("[[[[[[[[[[[[[[[[" + bmobFilesList.size() + "]]]]]]]]]]" + bmobFilesList.get(0) + "---" + bmobFilesList.get(1)
//                    + "---" + bmobFilesList.get(2) + "---" + bmobFilesList.get(3) + "---" + bmobFilesList.get(4));
            holder.friendPostRecyclerPhotoAdapter = new FriendPostRecyclerPhotoAdapter(context, bmobFilesList);
            holder.gv_photo.setAdapter(holder.friendPostRecyclerPhotoAdapter);
        } else {
            holder.gv_photo.setVisibility(View.GONE);//如果没有图片列表，就隐藏掉gridview
        }
        holder.tv_name.setText(postDatas.getAuthor().getUsername());//获取作者的用户名，作者是个对象
        holder.tv_content.setText(postDatas.getContent());
        holder.tv_commentNum.setText(postDatas.getCommentNum().toString());
        holder.tv_likeNum.setText(postDatas.getLikeNum().toString());
        holder.tv_creattime.setText(DateUtils.getTimestampString(postDatas.getCreatTime()));

        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(postDatasList.get(position));
    }

    @Override
    public int getItemCount() {
        return postDatasList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_name, tv_content, tv_commentNum, tv_likeNum, tv_creattime;
        private ImageView iv_avater;
        private GridView gv_photo;
        private FriendPostRecyclerPhotoAdapter friendPostRecyclerPhotoAdapter;

        public MyViewHolder(Context context, View itemView) {
            super(itemView);
            iv_avater = (ImageView) itemView.findViewById(R.id.friendpostfragment_item_iv_avater);
            gv_photo = (GridView) itemView.findViewById(R.id.friendpostfragment_item_gv_photo);
            tv_name = (TextView) itemView.findViewById(R.id.friendpostfragment_item_tv_name);
            tv_content = (TextView) itemView.findViewById(R.id.friendpostfragment_item_tv_content);
            tv_commentNum = (TextView) itemView.findViewById(R.id.friendpostfragment_item_tv_commentNum);
            tv_likeNum = (TextView) itemView.findViewById(R.id.friendpostfragment_item_tv_likeNum);
            tv_creattime = (TextView) itemView.findViewById(R.id.friendpostfragment_item_tv_creattime);
        }
    }

    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (String) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}
