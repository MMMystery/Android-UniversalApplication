package com.example.administrator.myuniversaltoolapplication.app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import cn.bmob.v3.Bmob;


/**
 * Created by Administrator on 2015/12/21.
 */
public class MyApplication extends Application {

    private RefWatcher mRefWatcher;//LeakCanary性能检测
    @Override
    public void onCreate() {
        super.onCreate();
        //LeakCanary注册
        mRefWatcher = LeakCanary.install(this);
        //Bmob注册
        Bmob.initialize(this,"4ae7abfd4c36023e0cdc426d39a5ee42");
        //volley注册
//        requestQueue = Volley.newRequestQueue(getApplicationContext());


    }

}

