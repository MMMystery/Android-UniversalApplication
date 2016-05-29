package com.example.administrator.myuniversaltoolapplication.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.entity.NewFriend;

import java.util.List;

import cn.bmob.newim.bean.BmobIMUserInfo;

/**
 * Created by y8042 on 2016/5/28.
 */
public class NewFriendAdapter  extends  RecyclerView.Adapter<NewFriendAdapter.MyViewHolder> {

private List<NewFriend> usersList;
private Context context;
        BmobIMUserInfo info;
public NewFriendAdapter(Context context, List<NewFriend> userDatasList) {
        this.context = context;
        this.usersList = userDatasList;
        }

public interface OnRecyclerViewListener {
    void onItemClick(int position);

    boolean onItemLongClick(int position);
}

    private OnRecyclerViewListener onRecyclerViewListener;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_newfriend, viewGroup, false);//后面这个false不能少
        MyViewHolder myViewHolder = new MyViewHolder(context, view);

        return myViewHolder;
    }

    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        Picasso.with(context).load(usersList.get(position).getAvater().getFileUrl(context)).into(holder.iv_avater);
//        holder.tv_username.setText(usersList.get(position).getUsername());
//        //设置添加好友的信息
//        info = new BmobIMUserInfo(usersList.get(position).getObjectId(),usersList.get(position).getUsername(),usersList.get(position).getAvater().getFileUrl(context));
//        holder.bt_agree.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtils.show(context,usersList.get(position).getObjectId());
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

public static class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_username;
    private ImageView iv_avater;
    private Button bt_agree;

    public MyViewHolder(Context context, View itemView) {
        super(itemView);
        iv_avater = (ImageView) itemView.findViewById(R.id.newfriendactivity_item_iv_avater);
        bt_agree = (Button) itemView.findViewById(R.id.newfriendactivity_item_bt_agree);
        tv_username = (TextView) itemView.findViewById(R.id.newfriendactivity_item_tv_username);


    }
}
}
