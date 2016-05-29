package com.example.administrator.myuniversaltoolapplication.app;

import android.app.Application;

import com.example.administrator.myuniversaltoolapplication.bmobutils.DemoMessageHandler;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.newim.BmobIM;
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
        Bmob.initialize(this, "4ae7abfd4c36023e0cdc426d39a5ee42");
        //只有主进程运行的时候才需要初始化
        if (getApplicationInfo().packageName.equals(getMyProcessName())) {
            //im初始化
            BmobIM.init(this);
            //注册消息接收器
            BmobIM.registerDefaultMessageHandler(new DemoMessageHandler(this));
        }
    }

        //volley注册
//        requestQueue = Volley.newRequestQueue(getApplicationContext());

        /**
         * 获取当前运行的进程名
         * @return
         */

    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

