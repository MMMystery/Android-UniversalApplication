package com.example.administrator.myuniversaltoolapplication.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import cn.bmob.v3.Bmob;
import io.rong.imkit.RongIM;


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

        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);
        }

    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

}

