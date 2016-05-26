package com.example.administrator.myuniversaltoolapplication.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadBatchListener;
import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.entity.MyUser;
import com.example.administrator.myuniversaltoolapplication.entity.Post;
import com.example.administrator.myuniversaltoolapplication.utils.ToastUtils;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class NewPostActivity extends BaseActivity implements View.OnClickListener {
    public final static int REQUEST_CODE = 100;
    private Button topBar_btn_left, topBar_btn_right;
    private Button bt_addphoto;
    private LinearLayout ll_photos;
    private EditText et_content;

    private String[] fileurlStr;
    private List<String> fileurlList = new ArrayList<String>();
    private List<String> imgList = new ArrayList<String>();
    private int allowPhotosNum = 9;
    private int index;//第几张图片了

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        initView();

    }

    private void initView() {
        topBar_btn_left = (Button) findViewById(R.id.topbar_btn_left);
        topBar_btn_right = (Button) findViewById(R.id.topbar_btn_right);
        bt_addphoto = (Button) findViewById(R.id.newpostActivity_bt_addphoto);
        et_content = (EditText) findViewById(R.id.newpostActivity_et_content);
        ll_photos = (LinearLayout) findViewById(R.id.newpostActivity_ll_photos);
        topBar_btn_left.setOnClickListener(this);
        topBar_btn_right.setOnClickListener(this);
        bt_addphoto.setOnClickListener(this);
        topBar_btn_left.setBackgroundResource(R.mipmap.bar_back);
        topBar_btn_right.setBackgroundResource(R.mipmap.bar_send);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topbar_btn_left:
                this.finish();
                break;
            case R.id.topbar_btn_right:
                fileurlStr = new String[fileurlList.size()];
                for (int i = 0; i < fileurlList.size(); i++) {
                    fileurlStr[i] = fileurlList.get(i);
                }
                BmobProFile.getInstance(this).uploadBatch(fileurlStr, new UploadBatchListener() {
                    @Override
                    //批量上传，每次上传成功都执行了下面这个方法，所以说上传3照片就执行了3次下面这些方法。
                    public void onSuccess(boolean isFinish, String[] fileNames, String[] urls, BmobFile[] files) {

                        if (isFinish) {
                            upLoadPost(et_content.getText().toString(), files);
                        }
                    }

                    @Override
                    public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {

                        Log.i("bmob", "onProgress :" + curIndex + "---" + curPercent + "---" + total + "----" + totalPercent);

                    }

                    @Override
                    public void onError(int statuscode, String errormsg) {
                        // TODO Auto-generated method stub
                        Log.i("bmob", "批量上传出错：" + statuscode + "--" + errormsg);
                    }
                });

                break;
            case R.id.newpostActivity_bt_addphoto:
                PhotoPickerIntent intent = new PhotoPickerIntent(NewPostActivity.this);
                allowPhotosNum = 9 - index;
                Logger.d("///////////////////////" + allowPhotosNum + "////////////////////////");
                intent.setPhotoCount(allowPhotosNum);//每次点，限制的图片数量都是不定的
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);

                break;

            default:
                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                imgList = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);

                Logger.d("-=-=-=-=-=" + imgList.size() + "-=-=-=-=-=-=-=");
                for (int i = 0; i < imgList.size(); i++) {
                    fileurlList.add(imgList.get(i));
                    //设置图片显示
                    ImageView imageView = new ImageView(this);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    lp.height = 200;
                    lp.width = 200;
                    imageView.setLayoutParams(lp);
                    Picasso.with(this).load(Uri.fromFile(new File(imgList.get(i)))).into(imageView);
                    ll_photos.addView(imageView);
                }
                Logger.d("================" + fileurlList.size() + "================");
                index = fileurlList.size();
                //图片多余九张时隐藏加图片的按钮
                if (fileurlList.size() >= 9) {
                    bt_addphoto.setVisibility(View.GONE);
                }
            }
        }
    }

    private void upLoadPost(String content, BmobFile[] filestr) {
        MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
        // 创建帖子信息
        Post post = new Post();

        //添加一对一关联,帖子信息
        post.setAuthor(user);
        post.setContent(content);
        post.setImgfilestr(filestr);//上传一系列图片
        post.setCommentNum(0);
        post.setLikeNum(0);
        post.save(this, new SaveListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub

                ToastUtils.show(getApplicationContext(), "帖子发表成功");
                finish();
            }

            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ll_photos.removeAllViews();//每次一开始要先清空所有视图
        //将获取下来的list数据分别一个一个赋值给数组中去
    }
}
