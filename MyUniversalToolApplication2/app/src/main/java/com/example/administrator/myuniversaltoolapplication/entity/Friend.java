package com.example.administrator.myuniversaltoolapplication.entity;

/**
 * Created by Administrator on 2016/2/17.
 */
public class Friend {
    private String friend_id;
    private String friend_name;

    public String getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(String friend_id) {
        this.friend_id = friend_id;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public void setFriend_name(String friend_name) {
        this.friend_name = friend_name;
    }

    public String getFriend_avater() {
        return friend_avater;
    }

    public void setFriend_avater(String friend_avater) {
        this.friend_avater = friend_avater;
    }

    private String friend_avater;
    public Friend(String friend_id,String friend_name,String friend_avater) {
        this.friend_id = friend_id;
        this.friend_name = friend_name;
        this.friend_avater = friend_avater;
    }
}
