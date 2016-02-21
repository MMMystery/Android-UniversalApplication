package com.example.administrator.myuniversaltoolapplication.entity;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Administrator on 2016/2/19.
 */
public class Post extends BmobObject {

    private String content;// 帖子内容
    private List<BmobFile> fileList; //一系列的图片
    private MyUser author;//帖子的发布者，这里体现的是一对一的关系，该帖子属于某个用户
    private BmobRelation likes;//多对多关系：用于存储喜欢该帖子的所有用户
    private Integer likeNum;//点赞数
    private Integer commentNum;//评论数

    public List<BmobFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<BmobFile> fileList) {
        this.fileList = fileList;
    }


    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MyUser getAuthor() {
        return author;
    }

    public void setAuthor(MyUser author) {
        this.author = author;
    }

    public BmobRelation getLikes() {
        return likes;
    }

    public void setLikes(BmobRelation likes) {
        this.likes = likes;
    }


}
