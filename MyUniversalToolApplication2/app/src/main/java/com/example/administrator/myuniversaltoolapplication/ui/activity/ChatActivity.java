package com.example.administrator.myuniversaltoolapplication.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.ui.adapter.ChatAdapter;
import com.example.administrator.myuniversaltoolapplication.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Map;

import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMTextMessage;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.exception.BmobException;

/**
 * Created by y8042 on 2016/5/29.
 */
public class ChatActivity extends BaseActivity{
    private EditText edit_msg;
    private Button btn_chat_send,btn_chat_keyboard,btn_chat_voice;
    private RecyclerView rc_view;
    BmobIMConversation c;

    ChatAdapter adapter;
    protected LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        c= BmobIMConversation.obtain(BmobIMClient.getInstance(), (BmobIMConversation) getIntent().getBundleExtra(getPackageName()).getSerializable("c"));
        initView();
    }

    private void initView() {
        edit_msg = (EditText) findViewById(R.id.edit_msg);
        btn_chat_send = (Button) findViewById(R.id.btn_chat_send);
        btn_chat_keyboard = (Button) findViewById(R.id.btn_chat_keyboard);
        btn_chat_voice = (Button) findViewById(R.id.btn_chat_voice);
        rc_view = (RecyclerView) findViewById(R.id.rc_view);
        layoutManager = new LinearLayoutManager(this);
        rc_view.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(this,c);
        rc_view.setAdapter(adapter);
        edit_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    btn_chat_send.setVisibility(View.VISIBLE);
                    btn_chat_keyboard.setVisibility(View.GONE);
                    btn_chat_voice.setVisibility(View.GONE);
                } else {
                    if (btn_chat_voice.getVisibility() != View.VISIBLE) {
                        btn_chat_voice.setVisibility(View.VISIBLE);
                        btn_chat_send.setVisibility(View.GONE);
                        btn_chat_keyboard.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }

        });
    }

    private void sendMessage() {
        String text=edit_msg.getText().toString();
        if(TextUtils.isEmpty(text.trim())){
            ToastUtils.show(getApplicationContext(),"请输入内容");
            return;
        }
        BmobIMTextMessage msg =new BmobIMTextMessage();
        msg.setContent(text);
        //可设置额外信息
        Map<String,Object> map =new HashMap<>();
        map.put("level", "1");//随意增加信息
        msg.setExtraMap(map);
        c.sendMessage(msg,listener);
    }
    /**
     * 消息发送监听器
     */
    public MessageSendListener listener =new MessageSendListener() {

        @Override
        public void onProgress(int value) {
            super.onProgress(value);
            //文件类型的消息才有进度值
            Logger.i("onProgress："+value);
        }

        @Override
        public void onStart(BmobIMMessage msg) {
            super.onStart(msg);
            adapter.addMessage(msg);
            edit_msg.setText("");
//            scrollToBottom();
        }

        @Override
        public void done(BmobIMMessage msg, BmobException e) {
            adapter.notifyDataSetChanged();
            edit_msg.setText("");
//            scrollToBottom();
            if (e != null) {
                ToastUtils.show(getApplicationContext(),e.getMessage());
            }
        }
    };
}
