package com.example.administrator.myuniversaltoolapplication.entity;

import cn.bmob.v3.BmobObject;

/**
 * 好友表
 * Created by Administrator on 2016/4/26.
 */
public class Friend extends BmobObject {

    private MyUser user;
    private MyUser friendUser;

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public MyUser getFriendUser() {
        return friendUser;
    }

    public void setFriendUser(MyUser friendUser) {
        this.friendUser = friendUser;
    }
}
