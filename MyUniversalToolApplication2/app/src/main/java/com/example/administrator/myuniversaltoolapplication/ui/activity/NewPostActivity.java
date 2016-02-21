package com.example.administrator.myuniversaltoolapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadBatchListener;
import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.entity.MyUser;
import com.example.administrator.myuniversaltoolapplication.entity.Post;
import com.example.administrator.myuniversaltoolapplication.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class NewPostActivity extends AppCompatActivity implements View.OnClickListener {
    public final static int REQUEST_CODE = 100;
    private Button topBar_btn_left, topBar_btn_right;
    private Button bt_addphoto;
    private ImageView iv_photo;
    private EditText et_content;
    private String filePath;
    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLERY_REQUEST_CODE = 2;
    private static int CROP_REQUEST_CODE = 3;
    private static final String IMAGE_FILE_NAME = "avatarImage.jpg";//拍完照的照片都給它定為這個名字
    String[] fileurlStr;
    private List<String> imgList = new ArrayList<String>();
    private List<BmobFile[]> imgFileList = new ArrayList<BmobFile[]>();

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
                BmobProFile.getInstance(this).uploadBatch(fileurlStr, new UploadBatchListener() {

                    @Override
                    public void onSuccess(boolean isFinish, String[] fileNames, String[] urls, BmobFile[] files) {
                        // isFinish ：批量上传是否完成
                        // fileNames：文件名数组
                        // urls        : url：文件地址数组
                        // files     : BmobFile文件数组，`V3.4.1版本`开始提供，用于兼容新旧文件服务。
//                        注：若上传的是图片，url(s)并不能直接在浏览器查看（会出现404错误），需要经过`URL签名`得到真正的可访问的URL地址,当然，`V3.4.1`版本可直接从BmobFile中获得可访问的文件地址。
                        imgFileList.add(files);
                        upLoadPost(et_content.getText().toString(), imgFileList);
                    }

                    @Override
                    public void onProgress(int curIndex, int curPercent, int total, int totalPercent) {
                        // curIndex    :表示当前第几个文件正在上传
                        // curPercent  :表示当前上传文件的进度值（百分比）
                        // total       :表示总的上传文件数
                        // totalPercent:表示总的上传进度（百分比）

                        Log.i("bmob", "onProgress :" + curIndex + "---" + curPercent + "---" + total + "----" + totalPercent);
                    }

                    @Override
                    public void onError(int statuscode, String errormsg) {
                        // TODO Auto-generated method stub
                        Log.i("bmob", "批量上传出错：" + statuscode + "--" + errormsg);
                    }
                });


               /* BTPFileResponse response = BmobProFile.getInstance(this).upload(filePath, new UploadListener() {

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
                });*/
                break;
            case R.id.newpostActivity_bt_addphoto:
                PhotoPickerIntent intent = new PhotoPickerIntent(NewPostActivity.this);
                intent.setPhotoCount(9);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);


               /* final String[] stringItems = {"拍照", "相册"};
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
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image*//*");//引号里面是image*//*
                                startActivityForResult(intent, GALLERY_REQUEST_CODE);
                                dialog.dismiss();
                                break;

                        }
                        dialog.dismiss();
                    }
                });*/
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                imgList = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                Logger.d(imgList.toString());
                fileurlStr = new String[imgList.size()];
                for (int i = 0; i < imgList.size(); i++) {
                    fileurlStr[i] = imgList.get(i);
                }
            }
        }
    }

    /* @Override
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

                        *//*
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
                    }*//*
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
*/
    private void upLoadPost(String content, List<BmobFile[]> fileList) {
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
