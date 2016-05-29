package com.example.administrator.myuniversaltoolapplication.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.bmobutils.AddFriendMessage;
import com.example.administrator.myuniversaltoolapplication.entity.MyUser;
import com.example.administrator.myuniversaltoolapplication.ui.activity.ChatActivity;
import com.example.administrator.myuniversaltoolapplication.utils.ToastUtils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by y8042 on 2016/5/27.
 */
public class SeachUserAdapter extends RecyclerView.Adapter<SeachUserAdapter.MyViewHolder> {

    private List<MyUser> usersList;
    private Context context;
    BmobIMUserInfo info;
    public SeachUserAdapter(Context context, List<MyUser> userDatasList) {
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_addfriend, viewGroup, false);//后面这个false不能少
        MyViewHolder myViewHolder = new MyViewHolder(context, view);

        return myViewHolder;
    }

    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Picasso.with(context).load(usersList.get(position).getAvater().getFileUrl(context)).into(holder.iv_avater);
        holder.tv_username.setText(usersList.get(position).getUsername());
        //设置添加好友的信息
        info = new BmobIMUserInfo(usersList.get(position).getObjectId(),usersList.get(position).getUsername(),usersList.get(position).getAvater().getFileUrl(context));
        holder.bt_addfriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(context,usersList.get(position).getObjectId());
                sendAddFriendMessage();
            }
        });
        holder.bt_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动一个会话，设置isTransient设置为false,则会在本地数据库的会话列表中先创建（如果没有）与该用户的会话信息，且将用户信息存储到本地的用户表中
                Intent intent = new Intent(context, ChatActivity.class);

                BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info,false,null);
                Bundle bundle = new Bundle();
                bundle.putSerializable("c", c);
                intent.putExtra(context.getPackageName(), bundle);
                context.startActivity(intent);

            }
        });


    }

    private void sendAddFriendMessage() {
        //启动一个暂态会话，也就是isTransient为true,表明该会话仅执行发送消息的操作，不会保存会话和消息到本地数据库中，
        BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info, true,null);
        //这个obtain方法才是真正创建一个管理消息发送的会话
        final BmobIMConversation conversation = BmobIMConversation.obtain(BmobIMClient.getInstance(), c);
        //新建一个添加好友的自定义消息实体
        AddFriendMessage msg =new AddFriendMessage();
        MyUser currentUser = BmobUser.getCurrentUser(context,MyUser.class);
        msg.setContent("很高兴认识你，可以加个好友吗?");//给对方的一个留言信息
        Map<String,Object> map =new HashMap<>();
        map.put("name", currentUser.getUsername());//发送者姓名，这里只是举个例子，其实可以不需要传发送者的信息过去
        map.put("avatar",currentUser.getAvater().getFileUrl(context));//发送者的头像
        map.put("uid",currentUser.getObjectId());//发送者的uid
        msg.setExtraMap(map);
        conversation.sendMessage(msg, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage msg, BmobException e) {
                if (e == null) {//发送成功
                    ToastUtils.show(context,"好友请求发送成功，等待验证");
                } else {//发送失败
                    ToastUtils.show(context,"发送失败:" + e.getMessage());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_username;
        private ImageView iv_avater;
        private Button bt_addfriend,bt_chat;

        public MyViewHolder(Context context, View itemView) {
            super(itemView);
            iv_avater = (ImageView) itemView.findViewById(R.id.addfriendactivity_item_iv_avater);
            bt_addfriend = (Button) itemView.findViewById(R.id.addfriendactivity_item_bt_addfriend);
            bt_chat = (Button) itemView.findViewById(R.id.addfriendactivity_item_bt_chat);
            tv_username = (TextView) itemView.findViewById(R.id.addfriendactivity_item_tv_username);


        }
    }
}
