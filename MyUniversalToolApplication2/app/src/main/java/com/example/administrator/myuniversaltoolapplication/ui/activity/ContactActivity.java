package com.example.administrator.myuniversaltoolapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.entity.Friend;
import com.example.administrator.myuniversaltoolapplication.entity.MyUser;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class ContactActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView rv_contacts;
    private SwipeRefreshLayout sw_refresh;
    private Button bt_newFriend, bt_addFriend;
    private Button topBar_bt_left, topBar_bt_right;
    private TextView topBar_tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        initView();
    }

    private void initView() {
        queryFriends();
        topBar_bt_left = (Button) findViewById(R.id.topbar_btn_left);
        topBar_bt_right = (Button) findViewById(R.id.topbar_btn_right);
        topBar_tv_title = (TextView) findViewById(R.id.topbar_tv_title);
        bt_newFriend = (Button) findViewById(R.id.contactacitivity_bt_newfriend);
        bt_addFriend = (Button) findViewById(R.id.contactacitivity_bt_addfriend);
        rv_contacts = (RecyclerView) findViewById(R.id.contactactivity_rv_contacts);
        sw_refresh = (SwipeRefreshLayout) findViewById(R.id.contactacitivity_sw_refresh);
        bt_newFriend.setOnClickListener(this);
        bt_addFriend.setOnClickListener(this);
        topBar_bt_left.setBackgroundResource(R.mipmap.bar_back);
        topBar_bt_right.setVisibility(View.GONE);
        topBar_tv_title.setText("联系人");
        topBar_bt_left.setOnClickListener(this);
    }


    /**
     * 查询好友
     */
    public void queryFriends() {
        BmobQuery<Friend> query = new BmobQuery<>();
        MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
        query.addWhereEqualTo("user", user);
        query.include("friendUser");
        query.order("-updatedAt");
        query.findObjects(this, new FindListener<Friend>() {
            @Override
            public void onSuccess(List<Friend> list) {
                if (list != null && list.size() > 0) {
                } else {
                }
            }

            @Override
            public void onError(int i, String s) {
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topbar_btn_left:
                this.finish();
                break;
            case R.id.contactacitivity_bt_addfriend:
                Intent intent1 = new Intent(this, AddFriendActivity.class);
                startActivity(intent1);
                break;
            case R.id.contactacitivity_bt_newfriend:
                Intent intent = new Intent(this, NewFriendActivity.class);
                startActivity(intent);
                break;
        }
    }
}
