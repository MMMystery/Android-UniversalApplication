package com.example.administrator.myuniversaltoolapplication.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myuniversaltoolapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.v3.BmobUser;

/**
 * Created by y8042 on 2016/5/29.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<BmobIMMessage> msgs = new ArrayList<>();
    private String currentUid = "";
    private Context context;
    BmobIMConversation c;

    public ChatAdapter(Context context, BmobIMConversation c) {
        this.context = context;
        try {
            currentUid = BmobUser.getCurrentUser(context).getObjectId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.c = c;
    }

    public void addMessage(BmobIMMessage message) {
        msgs.addAll(Arrays.asList(message));
        notifyDataSetChanged();
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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chat_received_message, viewGroup, false);//后面这个false不能少
        MyViewHolder myViewHolder = new MyViewHolder(context, view);

        return myViewHolder;
    }

    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        Picasso.with(context).load(msgs.get(position).getBmobIMUserInfo().getAvatar()).into(holder.iv_avatar);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = dateFormat.format(msgs.get(position).getCreateTime());
        holder.tv_time.setText(time);
        holder.tv_message.setText(msgs.get(position).getContent());

    }


    @Override
    public int getItemCount() {
        return msgs.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_time, tv_message;
        private ImageView iv_avatar;


        public MyViewHolder(Context context, View itemView) {
            super(itemView);

            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            iv_avatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tv_message = (TextView) itemView.findViewById(R.id.tv_message);


        }
    }
}
