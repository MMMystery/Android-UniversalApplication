package com.example.administrator.myuniversaltoolapplication.ui.adapter;



import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.administrator.myuniversaltoolapplication.R;

import java.util.List;

/**
 * Created by Administrator on 2016/2/18.
 */
public class HotPostRecyclerAdapter extends RecyclerView.Adapter<HotPostRecyclerAdapter.MyViewHolder> {
    private List<String> mDatas;
    public HotPostRecyclerAdapter(List<String> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public HotPostRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hotpost,viewGroup,false);//后面这个false不能少
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(HotPostRecyclerAdapter.MyViewHolder holder, int position) {
        holder.tv_name.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView tv_name;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.hotpostfragment_item_tv_name);
        }
    }
}
