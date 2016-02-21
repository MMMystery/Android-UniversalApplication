package com.example.administrator.myuniversaltoolapplication.ui.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.app.AppConstants;
import com.example.administrator.myuniversaltoolapplication.entity.MyUser;
import com.example.administrator.myuniversaltoolapplication.entity.Post;
import com.example.administrator.myuniversaltoolapplication.utils.PhotoUtil;
import com.example.administrator.myuniversaltoolapplication.utils.SPUtils;
import com.example.administrator.myuniversaltoolapplication.utils.ToastUtils;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class NewPostActivity extends AppCompatActivity implements View.OnClickListener {
    private Button topBar_btn_left, topBar_btn_right;
    private Button bt_addphoto;
    private ImageView iv_photo;
    private EditText et_content;
    private String filePath;
    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLERY_REQUEST_CODE = 2;
    private static int CROP_REQUEST_CODE = 3;
    private static final String IMAGE_FILE_NAME = "avatarImage.jpg";//拍完照的照片都給它定為這個名字

    private List<BmobFile> imgFileList = new ArrayList<BmobFile>();

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
        iv_photo = (ImageView) findViewById(R.id.newpostActivity_iv_photo);
        topBar_btn_left.setOnClickListener(this);
        topBar_btn_right.setOnClickListener(this);
        bt_addphoto.setOnClickListener(this);
        topBar_btn_left.setBackgroundResource(R.mipmap.bar_back);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topbar_btn_left:
                this.finish();
                break;
            case R.id.topbar_btn_right:

                BTPFileResponse response = BmobProFile.getInstance(this).upload(filePath, new UploadListener() {

                    @Override
                    public void onSuccess(String fileName, String url, BmobFile file) {
                        Logger.d("bmob", "文件上传成功：" + fileName + ",可访问的文件地址：" + file.getUrl());//后面这个就是访问图片的url不过没有在bmob进行url签名暂时访问时无效的
                        imgFileList.add(file);
                        upLoadPost(et_content.getText().toString(), imgFileList);
                    }


                    @Override
                    public void onProgress(int progress) {
                        // TODO Auto-generated method stub
                        Log.i("bmob", "onProgress :" + progress);
                    }

                    @Override
                    public void onError(int statuscode, String errormsg) {
                        // TODO Auto-generated method stub
                        Log.i("bmob", "文件上传失败：" + errormsg);
                        ToastUtils.show(getApplicationContext(), "上传失败：" + errormsg);
                    }
                });
                break;
            case R.id.newpostActivity_bt_addphoto:

                final String[] stringItems = {"拍照", "相册"};
                final ActionSheetDialog dialog = new ActionSheetDialog(this, stringItems, null);
                dialog.isTitleShow(false)
                        .show();
                dialog.setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                //从相机
                                Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                                startActivityForResult(takeIntent, CAMERA_REQUEST_CODE);
                                dialog.dismiss();
                                break;
                            case 1:
                                //从相册
                                Intent intent = new Intent(Intent.ACTION_PICK, null);
                                intent.setDataAndType(
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//引号里面是image/*
                                startActivityForResult(intent, GALLERY_REQUEST_CODE);
                                dialog.dismiss();
                                break;

                        }
                        dialog.dismiss();
                    }
                });
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:// 拍照修改头像
                if (resultCode == Activity.RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        Toast.makeText(this, "SD不可用", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    File file = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                    showPhoto(bitmap);
                }
                break;
            case 2:// 本地相册修改头像
                if (resultCode == Activity.RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        Toast.makeText(this, "SD不可用", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Uri uri = data.getData();
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    showPhoto(bitmap);

                    /*
                    //另外一种方式
                    ContentResolver resolver = getContentResolver();
                    try {
                        //使用ContentProvider通过URI获取原始图片
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, uri);
                        if (bitmap != null) {
                            iv_photo.setImageBitmap(bitmap);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }

                break;

            default:
                break;
        }
    }

    private void showPhoto(Bitmap bitmap) {
        Logger.d(bitmap.toString() + "------------------------");
        //图片没做压缩，如果做压缩的话，之前的bitmap要recycle
        iv_photo.setImageBitmap(bitmap);
        // 设置头像路徑，上傳頭像要用。
        String dateFolder = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)
                .format(new Date());
        filePath = PhotoUtil.saveBitmap(Environment.getExternalStorageDirectory() + "/hayzro/" + dateFolder + "/", "temphead.jpg",
                bitmap, true);
    }

    private void upLoadPost(String content, List<BmobFile> fileList) {
        MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
        // 创建帖子信息
        Post post = new Post();

        //添加一对一关联,帖子信息
        post.setAuthor(user);
        post.setContent(content);
        post.setFileList(fileList);//上传一系列图片
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
}
