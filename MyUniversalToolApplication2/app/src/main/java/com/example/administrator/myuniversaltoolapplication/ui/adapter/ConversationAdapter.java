package com.example.administrator.myuniversaltoolapplication.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.ui.activity.ChatActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;

/**
 * Created by y8042 on 2016/5/29.
 */
public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.MyViewHolder> {
    private List<BmobIMConversation> conversations = new ArrayList<>();
    private Context context;
    public void bindDatas(Context context,List<BmobIMConversation> list) {
        this.context = context;
        conversations.clear();
        if (null != list) {
            conversations.addAll(list);
        }
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_conversation, viewGroup, false);//后面这个false不能少
        MyViewHolder myViewHolder = new MyViewHolder(context, view);

        return myViewHolder;
    }

    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        List<BmobIMMessage> msgs = conversations.get(position).getMessages();
        if(msgs!=null&& msgs.size()>0){
            BmobIMMessage lastMsg =msgs.get(0);
            String content =lastMsg.getContent();
            holder.tv_recent_msg.setText(content);

        }
        holder.tv_recent_name.setText(conversations.get(position).getConversationTitle());
        holder.iv_recent_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);

                BmobIMConversation c = conversations.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("c", c);
                intent.putExtra(context.getPackageName(), bundle);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return conversations.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_recent_name,tv_recent_msg,tv_recent_time;
        private ImageView iv_recent_avatar;

        public MyViewHolder(Context context, View itemView) {
            super(itemView);
            iv_recent_avatar = (ImageView) itemView.findViewById(R.id.iv_recent_avatar);
            tv_recent_name = (TextView) itemView.findViewById(R.id.tv_recent_name);
            tv_recent_msg = (TextView) itemView.findViewById(R.id.tv_recent_msg);
            tv_recent_time = (TextView) itemView.findViewById(R.id.tv_recent_time);


        }
    }
}
