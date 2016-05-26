package com.example.administrator.myuniversaltoolapplication.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.app.AppConstants;
import com.example.administrator.myuniversaltoolapplication.entity.MyUser;
import com.example.administrator.myuniversaltoolapplication.utils.SPUtils;
import com.example.administrator.myuniversaltoolapplication.utils.ToastUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/1/16.
 */
public class LoginActivity extends BaseActivity {
    private Button bt_login;
    private Button bt_register;
    private EditText et_username;
    private EditText et_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    public void initView() {
        et_username = (EditText) findViewById(R.id.login_et_username);
        et_Password = (EditText) findViewById(R.id.login_et_password);
        bt_login = (Button) findViewById(R.id.login_bt_login);
        bt_register = (Button) findViewById(R.id.login_bt_register);

        //先检测用户是否登陆过
        final MyUser myUser = BmobUser.getCurrentUser(this, MyUser.class);
        if (myUser != null) {
            /**
             //         * 获取本地用户信息
             //         */

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

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

                            String current_objectId = String.valueOf(myUser.getObjectId());
                            String current_Username = String.valueOf(myUser.getUsername());

                            //清空之前的存储
                            SPUtils.clear(getApplicationContext());
                            //设置数据保存到本地SP中
                            SPUtils.put(getApplicationContext(), AppConstants.Key.CURRENT_OBJECTID, current_objectId);
                            SPUtils.put(getApplicationContext(), AppConstants.Key.CURRENT_USERNAME, current_Username);
                            //跳转到主界面
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            ToastUtils.show(getApplicationContext(),"登录成功");
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

  /*  OkHttpClient client = new OkHttpClient();

    String getToken(String url, String userId, String username, String img_avater) throws IOException {
        RequestBody body = new FormEncodingBuilder()
                .add("userId", userId)
                .add("name", username)
                .add("portraitUri", img_avater)//请求参数有错-------------------待修正----------------------------------------------
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


}
