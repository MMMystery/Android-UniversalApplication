package com.example.administrator.myuniversaltoolapplication.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.app.AppConstants;
import com.example.administrator.myuniversaltoolapplication.app.MyApplication;
import com.example.administrator.myuniversaltoolapplication.entity.Friend;
import com.example.administrator.myuniversaltoolapplication.entity.MyUser;
import com.example.administrator.myuniversaltoolapplication.utils.RegUtils;
import com.example.administrator.myuniversaltoolapplication.utils.SPUtils;
import com.example.administrator.myuniversaltoolapplication.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

/**
 * Created by Administrator on 2016/1/16.
 */
public class LoginActivity extends BaseActivity {
    private Button bt_login;
    private Button bt_register;
    private EditText et_username;
    private EditText et_Password;
    private List<Friend> userIdList;
    private static final String TOKEN = "L5q9BDBJrdSZL+bW2dQ3Q8YzH3VW73IW5fAgvgegI2eMEedG8biriWNuf21xiyb1xDBjDJoBgFYRhg+ASkpmJzNE3AyckuX6";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userIdList = new ArrayList<Friend>();
        userIdList.add(new Friend("f1195ce0a6", "a", "http://img.blog.csdn.net/20160219093419247"));
        userIdList.add(new Friend("b248a15ad2", "b", "http://s1.jikexueyuan.com/my/images/empty/paizi-he-not-index-course.png"));


        initView();
    }

    public void initView() {
        et_username = (EditText) findViewById(R.id.login_et_username);
        et_Password = (EditText) findViewById(R.id.login_et_password);
        bt_login = (Button) findViewById(R.id.login_bt_login);
        bt_register = (Button) findViewById(R.id.login_bt_register);

        //先检测用户是否登陆过
        MyUser myUser = BmobUser.getCurrentUser(this, MyUser.class);
        if (myUser != null) {
            /**
             //         * 获取本地用户信息
             //         */
            //V3.4.5版本新增加getObjectByKey方法获取本地用户对象中某一列的值
//            final String RM_Token_URL = "https://api.cn.ronghub.com/user/getToken.json";

            //方法从本地缓存中获取当前登陆用户某一列的值。其中key为用户表的指定列名
           /* final String current_objectId = (String) BmobUser.getObjectByKey(getApplicationContext(), AppConstants.Key.BOMBUSERTABLE_OBJECTID);
            final String current_Username = (String) BmobUser.getObjectByKey(getApplicationContext(), AppConstants.Key.BOMBUSERTABLE_USERNAME);
            if (null != BmobUser.getObjectByKey(getApplicationContext(), AppConstants.Key.BOMBUSERTABLE_AVATER)) {
                BmobFile current_Avater = (BmobFile) BmobUser.getObjectByKey(getApplicationContext(), AppConstants.Key.CURRENT_AVATER);
                SPUtils.put(getApplicationContext(), AppConstants.Key.CURRENT_AVATER, current_Avater.getFileUrl(this));//把file转换成Url

            }
            //从本地bmob缓存中获取下来的数据保存本地SP中
            SPUtils.put(getApplicationContext(), AppConstants.Key.CURRENT_OBJECTID, current_objectId);
            SPUtils.put(getApplicationContext(), AppConstants.Key.CURRENT_USERNAME, current_Username);
            Logger.i("bmob", current_objectId + ",\n" + current_Username);*/

            //连接融云服务器
            connect(TOKEN);
           /* //去获取融云Token
            try {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {


                            getToken(RM_Token_URL, objectId, currentusername,img_avater);
                            Logger.i("正在获取token");

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            } catch (Exception e) {
                e.printStackTrace();
            }*/

        } else {
            //第一次登陆，需要输入用户和密码
            bt_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final MyUser myUser = new MyUser();
                    myUser.setUsername(et_username.getText().toString().trim());
                    myUser.setPassword(et_Password.getText().toString().trim());
                    myUser.login(getApplicationContext(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            ToastUtils.show(getApplicationContext(), "登录成功");

//                            final String RM_Token_URL = "https://api.cn.ronghub.com/user/getToken.json";
                            String current_objectId = String.valueOf(myUser.getObjectId());
                            String current_Username = String.valueOf(myUser.getUsername());
                            String current_UserAvater = String.valueOf(myUser.getAvaterUrl());
                            Logger.d(current_UserAvater+"----");
                            //设置数据保存到本地SP中
                            SPUtils.put(getApplicationContext(), AppConstants.Key.CURRENT_OBJECTID, current_objectId);
                            SPUtils.put(getApplicationContext(), AppConstants.Key.CURRENT_USERNAME, current_Username);
                            SPUtils.put(getApplicationContext(), AppConstants.Key.CURRENT_AVATER, current_UserAvater);


                            //连接融云服务器
                            connect(TOKEN);

                           /* //去获取Token
                            try {
                                new Thread() {
                                    @Override
                                    public void run() {
                                        super.run();
                                        try {

//                                            getToken(RM_Token_URL, objectId, currentusername,img_avater);
                                            Logger.i("正在获取token");


                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }*/

                        }


                        @Override
                        public void onFailure(int code, String msg) {
                            // TODO Auto-generated method stub
                            ToastUtils.show(getApplicationContext(), "登录失败");
                        }
                    });

                }
            });
            //点击了注册按钮
            bt_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

   /* OkHttpClient client = new OkHttpClient();

    String getToken(String url, String userId, String username,String img_avater) throws IOException {

        RequestBody body = new FormEncodingBuilder()
                .add("userId", userId)
                .add("name", username)
                .add("portraitUri", "")//请求参数有错-------------------待修正----------------------------------------------
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            Logger.i("获取token成功" + response.body().string());
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                            startActivity(intent);
            return response.body().string();
        } else {
            Logger.i("获取token失败" + response.body().string());
            throw new IOException("Unexpected code " + response);
        }
    }*/

    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(MyApplication.getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {

                    Logger.d("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {

                  /*  final String objectId = (String) SPUtils.get(getApplicationContext(), AppConstants.Key.OBJECTID, "////");
                    final String currentusername = (String) SPUtils.get(getApplicationContext(), AppConstants.Key.CURRENT_USERNAME_KEY, "///");
                    final String img_avater = (String) SPUtils.get(getApplicationContext(), AppConstants.Key.IMG_AVATER, "////");

                    Logger.d("========",userid + "---" + objectId + "---" + currentusername + "---" + img_avater);*/
                    //如果连接上服务，就提供当前用户的聊天信息（有哪些聊天）给融云
                    RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

                        //其实本来要从融云那获取的userid和我从自己服务器端获取的objectId相等才可以执行下面，这才算是真正的关联上融云可以连接了。
                        @Override
                        public UserInfo getUserInfo(String userId) {
                            Logger.d("userIdList---------" + userIdList.size());
                            for (Friend i : userIdList) {
                                if (i.getFriend_id().equals(userId)) {

                                    Logger.d(i.getFriend_name().toString()+"/////"+i.getFriend_avater().toString());
                                    return new UserInfo(i.getFriend_id(), i.getFriend_name(), Uri.parse(i.getFriend_avater()));
                                }
                            }
                            return null;
//                            return new UserInfo(objectId, currentusername, Uri.parse(img_avater));//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
                        }

                    }, true);

                    Logger.d("LoginActivity", "--onSuccess" + userid);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    Logger.d("LoginActivity", "--onError" + errorCode);
                }
            });
        }
    }


}
