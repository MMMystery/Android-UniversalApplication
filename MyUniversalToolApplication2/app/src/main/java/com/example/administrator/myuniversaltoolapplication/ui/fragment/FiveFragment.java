package com.example.administrator.myuniversaltoolapplication.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.app.AppConstants;
import com.example.administrator.myuniversaltoolapplication.entity.MyUser;
import com.example.administrator.myuniversaltoolapplication.ui.activity.LoginActivity;
import com.example.administrator.myuniversaltoolapplication.utils.ActivityCollectorUtil;
import com.example.administrator.myuniversaltoolapplication.utils.PhotoUtil;
import com.example.administrator.myuniversaltoolapplication.utils.SPUtils;
import com.example.administrator.myuniversaltoolapplication.utils.ToastUtils;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;

public class FiveFragment extends Fragment implements View.OnClickListener {
    private ImageView iv_avater;
    private TextView tv_currentUserName;
    private Button topBar_bt_right;
    private LinearLayout ll_logout, ll_exit;
    private static final String IMAGE_FILE_NAME = "avatarImage.jpg";//拍完照的照片都給它定為這個名字
    private String filePath;
    private static int CAMERA_REQUEST_CODE = 1;
    private static int GALLERY_REQUEST_CODE = 2;
    private static int CROP_REQUEST_CODE = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_five, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        iv_avater = (ImageView) view.findViewById(R.id.fivefragment_iv_avater);
        tv_currentUserName = (TextView) view.findViewById(R.id.fivefragment_tv_currentUserName);
        topBar_bt_right = (Button) view.findViewById(R.id.topbar_btn_right);
        ll_logout = (LinearLayout) view.findViewById(R.id.fivefragment_ll_logout);
        ll_exit = (LinearLayout) view.findViewById(R.id.fivefragment_ll_exit);
        iv_avater.setOnClickListener(this);
        topBar_bt_right.setOnClickListener(this);
        ll_logout.setOnClickListener(this);
        ll_exit.setOnClickListener(this);
        if (!SPUtils.get(getActivity(), AppConstants.Key.CURRENT_AVATER, "").equals("")) {
            String fileUrl = (String) SPUtils.get(getActivity(), AppConstants.Key.CURRENT_AVATER, "");
            Picasso.with(getActivity()).load(fileUrl).into(iv_avater);
        } else {
            //设置默认头像
            Picasso.with(getActivity()).load(R.mipmap.img_wechat).into(iv_avater);
        }

        String currentUserName = (String) SPUtils.get(getActivity(), AppConstants.Key.CURRENT_USERNAME, "");


        tv_currentUserName.setText(currentUserName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topbar_btn_right:

                break;
            case R.id.fivefragment_iv_avater:
                final String[] stringItems = {"拍照", "相册"};
                final ActionSheetDialog dialog = new ActionSheetDialog(getActivity(), stringItems, null);
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
            case R.id.fivefragment_ll_logout:
                BmobUser.logOut(getActivity());//注销
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.fivefragment_ll_exit:
                ActivityCollectorUtil.finishAll();
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
                        Toast.makeText(getActivity(), "SD不可用", Toast.LENGTH_SHORT).show();
                        return;
                    }
//                    Logger.d("/////////////////111111111111111111"+data.toString());
                    File file = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                    startImageCrop(Uri.fromFile(file), 300, 300, true);
                }
                break;
            case 2:// 本地相册修改头像
                if (resultCode == Activity.RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        Toast.makeText(getActivity(), "SD不可用", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Logger.d("/////////////////22222222222222222222222"+data.toString());
                    startImageCrop(data.getData(), 300, 300, true);
                } else {
//                    Toast.makeText(getActivity(), "照片获取失败", Toast.LENGTH_SHORT).show();
                }

                break;
            case 3:// 裁剪头像返回
                if (data != null) {
                    Logger.d("0000000000000000"+data.toString());
                    saveCropAvator(data);
                }
                break;
            default:
                break;
        }
    }

    private void startImageCrop(Uri uri, int outputX, int outputY, boolean isCrop) {
        Intent intent = null;
        if (isCrop) {
            intent = new Intent("com.android.camera.action.CROP");
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    private void saveCropAvator(Intent data) {
        Logger.d("11111111111111111111111"+data.toString());
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            if (bitmap != null) {
                //离线，无网络模拟上传
               /* bitmap = PhotoUtil.toRoundCorner(bitmap, 30);//图片圆角化
                //設置頭像
                iv_avater.setImageBitmap(bitmap);*/

                // 设置头像路徑，上傳頭像要用。
                String dateFolder = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)
                        .format(new Date());
                filePath = PhotoUtil.saveBitmap(Environment.getExternalStorageDirectory() + "/hayzro/" + dateFolder + "/", "temphead.jpg",
                        bitmap, true);
                Logger.i("图片路径", filePath);//----------------/storage/sdcard0/hayzro/20160216/temphead.jpg
                // 上传头像文件到服务端，获取url下来
                sendAvatarFile(filePath);
            }
        }
    }

    private void sendAvatarFile(String filePath) {
        BTPFileResponse response = BmobProFile.getInstance(getContext()).upload(filePath, new UploadListener() {

            @Override
            public void onSuccess(String fileName, String url, BmobFile file) {
                Logger.d("bmob", "文件上传成功：" + fileName + ",可访问的文件地址：" + file.getUrl());//后面这个就是访问图片的url不过没有在bmob进行url签名暂时访问时无效的

                ToastUtils.show(getActivity(), "新方法---上传文件成功：" + file.getUrl());

               /* //获取可以网页真正直接访问的图片的url
                final String URL1 = BmobProFile.getInstance(getActivity()).signURL(fileName, file.getUrl(), "7af54fcbd534962f74d6f18d30441aea", 0, null);
                Logger.d(URL1);
                //url2没用上，搞清两者区别
                String URL2 = BmobProFile.getInstance(getActivity()).signURL(fileName, file.getUrl(), "7af54fcbd534962f74d6f18d30441aea", 50, "abc");
                Logger.d(URL2);*/
                sendAvatar(file);

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
                ToastUtils.show(getActivity(), "上传失败：" + errormsg);
            }
        });
    }

    private void sendAvatar(final BmobFile file) {
        Logger.i("444444444444444444");
        //获取url后更新用户的头像信息
        final MyUser myUser = BmobUser.getCurrentUser(getActivity(), MyUser.class);
        if (myUser != null) {
            MyUser newUser = new MyUser();
            newUser.setAvater(file);
            newUser.update(getActivity(), myUser.getObjectId(), new UpdateListener() {

                @Override
                public void onSuccess() {
                    // TODO Auto-generated method stub
                    Picasso.with(getActivity())
                            .load(file.getFileUrl(getActivity()))
                            .error(R.mipmap.ic_launcher)
                            .into(iv_avater);
                    SPUtils.put(getActivity(), AppConstants.Key.CURRENT_AVATER, file.getFileUrl(getActivity()));
                }

                @Override
                public void onFailure(int code, String msg) {
                    // TODO Auto-generated method stub
                    ToastUtils.show(getActivity(), "更新用户信息失败:" + msg);
                }
            });
        } else {
            ToastUtils.show(getActivity(), "本地用户为null,请登录。");
        }
    }
}
