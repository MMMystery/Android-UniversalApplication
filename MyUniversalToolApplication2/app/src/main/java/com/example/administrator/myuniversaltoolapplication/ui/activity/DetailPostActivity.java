package com.example.administrator.myuniversaltoolapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.entity.Comment;
import com.example.administrator.myuniversaltoolapplication.entity.MyUser;
import com.example.administrator.myuniversaltoolapplication.entity.Post;
import com.example.administrator.myuniversaltoolapplication.ui.adapter.DetailPostRecyclerAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class DetailPostActivity extends BaseActivity implements View.OnClickListener, XRecyclerView.LoadingListener {
    private Button topBar_btn_left, topBar_btn_right;
    private TextView tv_topBar_tv_title;
    private List<Comment> commentDatasList;
    private Comment commentDatas;
    private ImageView iv_avater;
    private TextView tv_name, tv_content, tv_creattime, tv_commentNum, tv_likeNum;
    private XRecyclerView rv_comment;
    private EditText et_commentinput;
    private Button bt_commentsend;
    private DetailPostRecyclerAdapter detailPostRecyclerAdapter;
    private static final int ITEM_NUM_PAGE = 15;
    private int pageNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        initView();
    }

    private void initView() {
        topBar_btn_left = (Button) findViewById(R.id.topbar_btn_left);
        topBar_btn_right = (Button) findViewById(R.id.topbar_btn_right);
        tv_topBar_tv_title = (TextView) findViewById(R.id.topbar_tv_title);
        iv_avater = (ImageView) findViewById(R.id.detailpost_iv_avater);
        tv_name = (TextView) findViewById(R.id.detailpost_tv_name);
        tv_content = (TextView) findViewById(R.id.detailpost_tv_content);
        tv_creattime = (TextView) findViewById(R.id.detailpost_tv_creattime);
        tv_commentNum = (TextView) findViewById(R.id.detailpost_tv_commentNum);
        tv_likeNum = (TextView) findViewById(R.id.detailpost_tv_likeNum);
        rv_comment = (XRecyclerView) findViewById(R.id.detailpost_rv_commment);
        et_commentinput = (EditText) findViewById(R.id.detailpost_et_commentinput);
        bt_commentsend = (Button) findViewById(R.id.detailpost_bt_commentsend);
        bt_commentsend.setOnClickListener(this);
        tv_topBar_tv_title.setText("评论");
        topBar_btn_left.setBackgroundResource(R.mipmap.bar_back);
        topBar_btn_right.setVisibility(View.GONE);

        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        // 设置布局管理器
        rv_comment.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rv_comment.setHasFixedSize(true);
        //添加动画
        rv_comment.setItemAnimator(new DefaultItemAnimator());

//        rv_RecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rv_comment.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        rv_comment.setArrowImageView(R.mipmap.arrow_refresh);
        rv_comment.setLoadingListener(this);

        commentDatasList = new ArrayList<Comment>();
        detailPostRecyclerAdapter = new DetailPostRecyclerAdapter(this, commentDatasList);
        rv_comment.setAdapter(detailPostRecyclerAdapter);


        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        String postid = bundle.getString("postid");
        String avater = bundle.getString("avater");
        String author = bundle.getString("author");
        String content = bundle.getString("content");
        int commentNum = bundle.getInt("commentNum");
        int likeNum = bundle.getInt("likeNum");
        String creattime = bundle.getString("creattime");
        Logger.d(postid + "=" + avater + "=" + author + "=" + content + "=" + commentNum + "=" + likeNum + "=" + creattime);


        Picasso.with(this).load(avater).into(iv_avater);
        tv_name.setText(author);
        tv_content.setText(content);
        tv_creattime.setText(creattime);
        tv_commentNum.setText(String.valueOf(commentNum));
        tv_likeNum.setText(String.valueOf(likeNum));
        if (postid != null) {
            initDatas();
        }


    }

    private void initDatas() {
        BmobQuery<Comment> query = new BmobQuery<Comment>();
//用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        Post post = new Post();
        post.setObjectId(getIntent().getExtras().getString("postid"));
        query.addWhereEqualTo("post", new BmobPointer(post));
//希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        query.include("user,post.author");
        query.setLimit(ITEM_NUM_PAGE); // 限制最多几条数据结果作为一页
        query.findObjects(this, new FindListener<Comment>() {

            @Override
            public void onSuccess(List<Comment> object) {
                // TODO Auto-generated method stub
                //把获取下来的List数据传递给postDatas;用于去adapter那显示出来。
                for (Comment datasList : object) {
                    commentDatas = new Comment();//几组数据就得new几次
                    commentDatas.setUser(datasList.getUser());
                    commentDatas.setContent(datasList.getContent());
                    commentDatasList.add(commentDatas);//每一组数据也都得加到List当中去
                }

                //在这网络请求完成之前就setAdapter过了，不过最初是空的，然后获取数据下来后，再更新一遍数据就有显示了。
                //这样的话就不需要网络请求完后才能做setAdapter这些操作了

                detailPostRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detailpost_bt_commentsend:
                MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
                Post post = new Post();
                post.setObjectId(getIntent().getExtras().getString("postid"));
                commentDatas = new Comment();
                commentDatas.setPost(post);
                commentDatas.setContent(et_commentinput.getText().toString());
                commentDatas.setUser(user);
                commentDatasList.add(commentDatas);//这里是我自己加的
                commentDatas.save(this, new SaveListener() {

                    @Override
                    public void onSuccess() {
                        // TODO Auto-generated method stub
                        Log.i("life", "评论发表成功");
                        detailPostRecyclerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        // TODO Auto-generated method stub
                        Log.i("life", "评论失败");
                    }
                });
                break;
            case R.id.topbar_btn_left:
                this.finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        BmobQuery<Comment> query = new BmobQuery<Comment>();
//用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        Post post = new Post();
        post.setObjectId(getIntent().getExtras().getString("postid"));
        query.addWhereEqualTo("post", new BmobPointer(post));
//希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        query.include("user,post.author");
        query.setLimit(ITEM_NUM_PAGE); // 限制最多几条数据结果作为一页
        query.findObjects(this, new FindListener<Comment>() {

            @Override
            public void onSuccess(List<Comment> object) {
                // TODO Auto-generated method stub
                //把获取下来的List数据传递给postDatas;用于去adapter那显示出来。
                for (Comment datasList : object) {
                    commentDatas = new Comment();//几组数据就得new几次
                    commentDatas.setUser(datasList.getUser());
                    commentDatas.setContent(datasList.getContent());

                    commentDatasList.add(commentDatas);//每一组数据也都得加到List当中去
                }

                //在这网络请求完成之前就setAdapter过了，不过最初是空的，然后获取数据下来后，再更新一遍数据就有显示了。
                //这样的话就不需要网络请求完后才能做setAdapter这些操作了

                detailPostRecyclerAdapter.notifyDataSetChanged();
                rv_comment.refreshComplete();//刷新完成，刷新动画去掉
                pageNumber = 1;//刷新后，记住页码数要归为1
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public void onLoadMore() {
        BmobQuery<Comment> query = new BmobQuery<Comment>();
//用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        Post post = new Post();
        post.setObjectId(getIntent().getExtras().getString("postid"));
        query.addWhereEqualTo("post", new BmobPointer(post));
//希望同时查询该评论的发布者的信息，以及该帖子的作者的信息，这里用到上面`include`的并列对象查询和内嵌对象的查询
        query.include("user,post.author");
        query.setLimit(ITEM_NUM_PAGE); // 限制最多几条数据结果作为一页
        query.setSkip(ITEM_NUM_PAGE * pageNumber);//跳过前面的，把后面的加载进来
        query.findObjects(this, new FindListener<Comment>() {

            @Override
            public void onSuccess(List<Comment> object) {
                // TODO Auto-generated method stub
                //把获取下来的List数据传递给postDatas;用于去adapter那显示出来。
                for (Comment datasList : object) {
                    commentDatas = new Comment();//几组数据就得new几次
                    commentDatas.setUser(datasList.getUser());
                    commentDatas.setContent(datasList.getContent());

                    commentDatasList.add(commentDatas);//每一组数据也都得加到List当中去
                }

                //在这网络请求完成之前就setAdapter过了，不过最初是空的，然后获取数据下来后，再更新一遍数据就有显示了。
                //这样的话就不需要网络请求完后才能做setAdapter这些操作了

                detailPostRecyclerAdapter.notifyDataSetChanged();
                rv_comment.loadMoreComplete();//加载完成，加载动画去掉
                pageNumber++;//页码数+1
            }

            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
            }
        });
    }
}
