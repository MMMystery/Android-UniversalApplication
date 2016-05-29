package com.example.administrator.myuniversaltoolapplication.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myuniversaltoolapplication.R;

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

    public void bindDatas(Context context, List<BmobIMConversation> list) {
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
        MyViewHolder myViewHolder = new MyViewHolder(context, view, onRecyclerViewListener);

        return myViewHolder;
    }

    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        List<BmobIMMessage> msgs = conversations.get(position).getMessages();
        if (msgs != null && msgs.size() > 0) {
            BmobIMMessage lastMsg = msgs.get(0);
            String content = lastMsg.getContent();
            holder.tv_recent_msg.setText(content);

        }
        holder.tv_recent_name.setText(conversations.get(position).getConversationTitle());

    }

    public BmobIMConversation getItem(int position) {
        return conversations.get(position);
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView tv_recent_name, tv_recent_msg, tv_recent_time;
        private ImageView iv_recent_avatar;
        OnRecyclerViewListener onRecyclerViewListener;

        public MyViewHolder(Context context, View itemView, OnRecyclerViewListener onRecyclerViewListener) {
            super(itemView);
            iv_recent_avatar = (ImageView) itemView.findViewById(R.id.iv_recent_avatar);
            tv_recent_name = (TextView) itemView.findViewById(R.id.tv_recent_name);
            tv_recent_msg = (TextView) itemView.findViewById(R.id.tv_recent_msg);
            tv_recent_time = (TextView) itemView.findViewById(R.id.tv_recent_time);

            this.onRecyclerViewListener = onRecyclerViewListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (onRecyclerViewListener != null) {
                onRecyclerViewListener.onItemClick(getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (onRecyclerViewListener != null) {
                onRecyclerViewListener.onItemLongClick(getAdapterPosition());
            }
            return true;
        }

    }
}
