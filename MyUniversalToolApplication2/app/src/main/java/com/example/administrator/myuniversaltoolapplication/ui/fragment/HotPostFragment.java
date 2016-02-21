package com.example.administrator.myuniversaltoolapplication.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.ui.adapter.HotPostRecyclerAdapter;

import java.util.ArrayList;

public class HotPostFragment extends Fragment{
    private RecyclerView rv_RecyclerView;
    private ArrayList<String> mDatas;
    private HotPostRecyclerAdapter myRecyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hot_post, container, false);

        initView(view);

        return view;

    }

    private void initView(View view) {
        rv_RecyclerView = (RecyclerView) view.findViewById(R.id.friendhotfragment_rv_recyclerview);

        initDatas();

        GridLayoutManager layoutManger = new GridLayoutManager(getActivity(),2);
        rv_RecyclerView.setLayoutManager(layoutManger);

        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rv_RecyclerView.setHasFixedSize(true);
        //添加动画
        rv_RecyclerView.setItemAnimator(new DefaultItemAnimator());

        myRecyclerAdapter = new HotPostRecyclerAdapter(mDatas);
        rv_RecyclerView.setAdapter(myRecyclerAdapter);
    }


    private void initDatas() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDatas.add("item" + i);
        }
    }
}
