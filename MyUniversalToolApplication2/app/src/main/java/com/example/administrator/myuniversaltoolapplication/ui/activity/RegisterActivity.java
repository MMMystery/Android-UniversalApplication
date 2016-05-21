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
import com.orhanobut.logger.Logger;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016/2/14.
 */
public class RegisterActivity extends BaseActivity {

    private EditText et_username;
    private EditText et_password;
    private EditText et_phonenumber;
    private EditText et_email;
    private Button bt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

    }

    private void initView() {
        et_username = (EditText) findViewById(R.id.register_et_username);
        et_password = (EditText) findViewById(R.id.register_et_password);
        et_phonenumber = (EditText) findViewById(R.id.register_et_phonenumber);
        et_email = (EditText) findViewById(R.id.register_et_email);
        bt_register = (Button) findViewById(R.id.register_bt_register);
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册用户
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String mobiliePhoneNumber = et_phonenumber.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                //注册账号
                final MyUser myUser = new MyUser();
                myUser.setUsername(username);
                myUser.setPassword(password);
                myUser.setMobilePhoneNumber(mobiliePhoneNumber);
                myUser.setEmail(email);
                myUser.signUp(getApplicationContext(), new SaveListener() {
                    @Override
                    public void onSuccess() {
                        ToastUtils.show(getApplicationContext(), "注册用户成功");

                        String objectId = String.valueOf(myUser.getObjectId());
                        String currentusername = String.valueOf(myUser.getUsername());

                        //清空之前的存储
                        SPUtils.clear(getApplicationContext());
                        //设置数据保存到本地SP中
                        SPUtils.put(getApplicationContext(), AppConstants.Key.CURRENT_USERNAME, currentusername);
                        SPUtils.put(getApplicationContext(), AppConstants.Key.CURRENT_OBJECTID, objectId);

                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        ToastUtils.show(getApplicationContext(), "注册用户失败" + s);
                        Logger.v("注册失败" + s);

                    }
                });

            }
        });
    }

}
