package com.example.administrator.myuniversaltoolapplication.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.entity.MyUser;
import com.example.administrator.myuniversaltoolapplication.entity.Post;
import com.example.administrator.myuniversaltoolapplication.ui.adapter.FriendPostRecyclerAdapter;
import com.example.administrator.myuniversaltoolapplication.utils.DateUtils;
import com.example.administrator.myuniversaltoolapplication.utils.ToastUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class FriendPostFragment extends Fragment implements FriendPostRecyclerAdapter.OnRecyclerViewItemClickListener, XRecyclerView.LoadingListener {

    private int pageNumber = 1;
    private static final int ITEM_NUM_PAGE = 15;

    private XRecyclerView rv_RecyclerView;
    private List<Post> postDatasList;
    private Post postDatas;
    private FriendPostRecyclerAdapter myRecyclerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_post, container, false);

        initView(view);


        return view;

    }

    private void initView(View view) {

        rv_RecyclerView = (XRecyclerView) view.findViewById(R.id.friendpostfragment_rv_recyclerview);

        initDatas();
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        // 设置布局管理器
        rv_RecyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rv_RecyclerView.setHasFixedSize(true);
        //添加动画
        rv_RecyclerView.setItemAnimator(new DefaultItemAnimator());

        rv_RecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rv_RecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        rv_RecyclerView.setArrowImageView(R.mipmap.arrow_refresh);
        rv_RecyclerView.setLoadingListener(this);

        postDatasList = new ArrayList<Post>();
        myRecyclerAdapter = new FriendPostRecyclerAdapter(getActivity(), postDatasList);
        rv_RecyclerView.setAdapter(myRecyclerAdapter);
        myRecyclerAdapter.setOnItemClickListener(this);


    }

    private void initDatas() {

        MyUser user = BmobUser.getCurrentUser(getActivity(), MyUser.class);
        BmobQuery<Post> query = new BmobQuery<Post>();
        query.addWhereEqualTo("author", user);    // 查询当前用户的所有帖子
        query.order("-updatedAt");
        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.setLimit(ITEM_NUM_PAGE); // 限制最多几条数据结果作为一页
        query.findObjects(getActivity(), new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> object) {
                // TODO Auto-generated method stub

                //把获取下来的List数据传递给postDatas;用于去adapter那显示出来。
                for (Post datasList : object) {
                    postDatas = new Post();//几组数据就得new几次
                    postDatas.setAuthor(datasList.getAuthor());
                    postDatas.setContent(datasList.getContent());
                    postDatas.setImgfilestr(datasList.getImgfilestr());
                    postDatas.setCommentNum(datasList.getCommentNum());
                    postDatas.setLikeNum(datasList.getLikeNum());
                    postDatas.setCreatTime(DateUtils.string2Date(datasList.getCreatedAt(),"yyyy-MM-dd HH:mm:ss"));
                    postDatasList.add(postDatas);//每一组数据也都得加到List当中去
                }

                //在这网络请求完成之前就setAdapter过了，不过最初是空的，然后获取数据下来后，再更新一遍数据就有显示了。
                //这样的话就不需要网络请求完后才能做setAdapter这些操作了

                myRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                ToastUtils.show(getActivity(), "查询失败:" + msg);
            }
        });

    }

    @Override
    public void onItemClick(View view, String data) {
        ToastUtils.show(getActivity(), data);
    }


    //刷新加载事件
    @Override
    public void onRefresh() {
        MyUser user = BmobUser.getCurrentUser(getActivity(), MyUser.class);
        BmobQuery<Post> query = new BmobQuery<Post>();
        query.addWhereEqualTo("author", user);    // 查询当前用户的所有帖子
        query.order("-updatedAt");
        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.setLimit(ITEM_NUM_PAGE); // 限制最多几条数据结果作为一页
        query.findObjects(getActivity(), new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> object) {
                // TODO Auto-generated method stub

                postDatasList.clear();//刷新的话，要先把之间的数据都清空，再去拉取。
                //把获取下来的List数据传递给postDatas;用于去adapter那显示出来。
                for (Post datasList : object) {
                    postDatas = new Post();//几组数据就得new几次
                    postDatas.setAuthor(datasList.getAuthor());
                    postDatas.setContent(datasList.getContent());
                    postDatas.setImgfilestr(datasList.getImgfilestr());
                    postDatas.setCommentNum(datasList.getCommentNum());
                    postDatas.setLikeNum(datasList.getLikeNum());
                    postDatas.setCreatTime(DateUtils.string2Date(datasList.getCreatedAt(),"yyyy-MM-dd HH:mm:ss"));
                    postDatasList.add(postDatas);//每一组数据也都得加到List当中去
                }

                //在这网络请求完成之前就setAdapter过了，不过最初是空的，然后获取数据下来后，再更新一遍数据就有显示了。
                //这样的话就不需要网络请求完后才能做setAdapter这些操作了
                myRecyclerAdapter.notifyDataSetChanged();
                rv_RecyclerView.refreshComplete();//刷新完成，刷新动画去掉
                pageNumber = 1;//刷新后，记住页码数要归为1
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                ToastUtils.show(getActivity(), "查询失败:" + msg);
            }
        });
    }

    @Override
    public void onLoadMore() {
        MyUser user = BmobUser.getCurrentUser(getActivity(), MyUser.class);
        BmobQuery<Post> query = new BmobQuery<Post>();
        query.addWhereEqualTo("author", user);    // 查询当前用户的所有帖子
        query.order("-updatedAt");
        query.include("author");// 希望在查询帖子信息的同时也把发布人的信息查询出来
        query.setLimit(ITEM_NUM_PAGE); // 限制最多几条数据结果作为一页
        query.setSkip(ITEM_NUM_PAGE * pageNumber);//跳过前面的，把后面的加载进来
        query.findObjects(getActivity(), new FindListener<Post>() {
            @Override
            public void onSuccess(List<Post> object) {
                // TODO Auto-generated method stub


                //把获取下来的List数据传递给postDatas;用于去adapter那显示出来。
                for (Post datasList : object) {
                    postDatas = new Post();//几组数据就得new几次
                    postDatas.setAuthor(datasList.getAuthor());
                    postDatas.setContent(datasList.getContent());
                    postDatas.setImgfilestr(datasList.getImgfilestr());
                    postDatas.setCommentNum(datasList.getCommentNum());
                    postDatas.setLikeNum(datasList.getLikeNum());
                    postDatas.setCreatTime(DateUtils.string2Date(datasList.getCreatedAt(),"yyyy-MM-dd HH:mm:ss"));
                    postDatasList.add(postDatas);//每一组数据也都得加到List当中去
                }

                //在这网络请求完成之前就setAdapter过了，不过最初是空的，然后获取数据下来后，再更新一遍数据就有显示了。
                //这样的话就不需要网络请求完后才能做setAdapter这些操作了
                myRecyclerAdapter.notifyDataSetChanged();
                rv_RecyclerView.loadMoreComplete();//加载完成，加载动画去掉
                pageNumber++;//页码数+1
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                ToastUtils.show(getActivity(), "查询失败:" + msg);
            }
        });
    }
}
