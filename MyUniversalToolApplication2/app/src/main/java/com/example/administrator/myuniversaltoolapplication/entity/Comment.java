package com.example.administrator.myuniversaltoolapplication.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/2/19.
 */
public class Comment extends BmobObject {
    private MyUser user;//评论者
    private String content;//评论内容
    private Post post; //所评论的帖子，一个评论只能属于一个微博

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }


}
