package com.example.administrator.myuniversaltoolapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.entity.Post;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailPostActivity extends AppCompatActivity {
    private List<Post> postDatasList;
    private ImageView iv_avater;
    private TextView tv_name,tv_content,tv_creattime,tv_commentNum,tv_likeNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);
        initView();
    }

    private void initView() {
        iv_avater = (ImageView) findViewById(R.id.detailpost_iv_avater);
        tv_name = (TextView) findViewById(R.id.detailpost_tv_name);
        tv_content = (TextView) findViewById(R.id.detailpost_tv_content);
        tv_creattime = (TextView) findViewById(R.id.detailpost_tv_creattime);
        tv_commentNum = (TextView) findViewById(R.id.detailpost_tv_commentNum);
        tv_likeNum = (TextView) findViewById(R.id.detailpost_tv_likeNum);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        String avater = bundle.getString("avater");
        String author = bundle.getString("author");
        String content = bundle.getString("content");
        int commentNum = bundle.getInt("commentNum");
        int likeNum = bundle.getInt("likeNum");
        String creattime = bundle.getString("creattime");
        Logger.d(avater + "=" + author + "=" + content + "=" + commentNum + "=" + likeNum + "=" + creattime);


        Picasso.with(this).load(avater).into(iv_avater);
        tv_name.setText(author);
        tv_content.setText(content);
        tv_creattime.setText(creattime);
        tv_commentNum.setText(String.valueOf(commentNum));
        tv_likeNum.setText(String.valueOf(likeNum));


//        bundle.putString("avater",postDatasList.get(position-1).getAuthor().getAvater().getFileUrl(getActivity()));
//        bundle.putString("author",postDatasList.get(position-1).getAuthor().getUsername());
//        bundle.putString("content",postDatasList.get(position-1).getContent());
//        bundle.putInt("commentNum",postDatasList.get(position-1).getCommentNum());
//        bundle.putInt("likeNum",postDatasList.get(position-1).getLikeNum());
//        bundle.putString("creattime", DateUtils.getTimestampString(postDatasList.get(position-1).getCreatTime()));
    }
}
