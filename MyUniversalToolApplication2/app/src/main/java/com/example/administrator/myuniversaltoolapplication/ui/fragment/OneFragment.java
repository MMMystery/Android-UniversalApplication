package com.example.administrator.myuniversaltoolapplication.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.ui.activity.ConversationActivity;
import com.example.administrator.myuniversaltoolapplication.utils.ToastUtils;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import cn.bmob.v3.datatype.BmobFile;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;


public class OneFragment extends Fragment implements View.OnClickListener{
    private Button topBar_btn_left, topBar_btn_right;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        initView(view);
        //引入会话列表
        enterConversationListFragment();


      /*  //上传文件
        String filePath = "sdcard/aa.jpg";
        BTPFileResponse response = BmobProFile.getInstance(getContext()).upload(filePath, new UploadListener() {

            @Override
            public void onSuccess(String fileName,String url,BmobFile file) {
                Logger.d("bmob","文件上传成功："+fileName+",可访问的文件地址："+file.getUrl());//后面这个就是访问图片的url不过没有在bmob进行url签名暂时访问时无效的

                ToastUtils.show(getActivity(),"新方法---上传文件成功：" + file.getUrl());

                //获取可以网页真正直接访问的图片的url

                String URL1 =BmobProFile.getInstance(getActivity()).signURL(fileName,file.getUrl(),"7af54fcbd534962f74d6f18d30441aea",0,null);
                Logger.d(URL1);
                String URL2 = BmobProFile.getInstance(getActivity()).signURL(fileName,file.getUrl(),"7af54fcbd534962f74d6f18d30441aea",50,"abc");
                Logger.d(URL2);
                Picasso.with(getActivity())
                        .load(URL1)
                        .error(R.mipmap.ic_launcher)
                        .into(fragment_one_iv_three);

                //下载文件
                BmobProFile.getInstance(getApplicationContext()).download(fileName, new DownloadListener() {

                    @Override
                    public void onSuccess(String fullPath) {
                        // TODO Auto-generated method stub
                        //这里是真的把文件下载下来了。
                        Logger.d("bmob下载成功："+fullPath);//fullPath打印出来是：/data/data/com.example.administrator.myuniversaltoolapplication/cache/BmobCache/Download/5089eded673e46a8aa4bddeacfffb91d.jpg
                    }

                    @Override
                    public void onProgress(String localPath, int percent) {
                        // TODO Auto-generated method stub
                        Logger.d("bmob","download-->onProgress :"+percent);
//                        dialog.setProgress(percent);
                    }

                    @Override
                    public void onError(int statuscode, String errormsg) {
                        // TODO Auto-generated method stub
//                        dialog.dismiss();
                        Logger.d("bmob","下载出错："+statuscode +"--"+errormsg);
                    }
                });*//*
                // TODO Auto-generated method stub
                // fileName ：文件名（带后缀），这个文件名是唯一的，开发者需要记录下该文件名，方便后续下载或者进行缩略图的处理
                // url        ：文件地址
                // file        :BmobFile文件类型，`V3.4.1版本`开始提供，用于兼容新旧文件服务。
                //注：若上传的是图片，url地址并不能直接在浏览器查看（会出现404错误），需要经过`URL签名`得到真正的可访问的URL地址,当然，`V3.4.1`的版本可直接从'file.getUrl()'中获得可访问的文件地址。
            }



            @Override
            public void onProgress(int progress) {
                // TODO Auto-generated method stub
                Log.i("bmob","onProgress :"+progress);
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                // TODO Auto-generated method stub
                Log.i("bmob","文件上传失败："+errormsg);
            }
        });*/

        return view;

    }

    private void enterConversationListFragment() {
        //ConversationListFragment是融云自带的fragment,嵌入进来了
        ConversationListFragment fragment = (ConversationListFragment) getChildFragmentManager().findFragmentById(
                R.id.conversationlist);

        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon().appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") // 设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")// 设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")// 设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")// 设置系统会话非聚合显示
                .build();
        fragment.setUri(uri);
    }



    private void initView(View view) {
        topBar_btn_left = (Button) view.findViewById(R.id.topbar_btn_left);
        topBar_btn_right = (Button) view.findViewById(R.id.topbar_btn_right);
        topBar_btn_left.setOnClickListener(this);
        topBar_btn_right.setOnClickListener(this);
        topBar_btn_left.setVisibility(View.GONE);
        topBar_btn_right.setBackgroundResource(R.mipmap.bar_contacts);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topbar_btn_left:
                Toast.makeText(getActivity(), "left", Toast.LENGTH_LONG).show();
                break;
            case R.id.topbar_btn_right:
                //启动会话界面
                if (RongIM.getInstance() != null)
                    RongIM.getInstance().startPrivateChat(getActivity(), "f1195ce0a6", "title");//这里控制了和谁对话，启动ConversationActivity。

                break;
        }

    }
}
