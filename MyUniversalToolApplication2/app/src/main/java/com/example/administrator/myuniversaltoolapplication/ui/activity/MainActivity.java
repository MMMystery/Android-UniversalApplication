package com.example.administrator.myuniversaltoolapplication.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.bmobutils.IMMLeaks;
import com.example.administrator.myuniversaltoolapplication.entity.MyUser;
import com.example.administrator.myuniversaltoolapplication.ui.adapter.MyFragPagerAdapter;
import com.example.administrator.myuniversaltoolapplication.ui.fragment.FiveFragment;
import com.example.administrator.myuniversaltoolapplication.ui.fragment.OneFragment;
import com.example.administrator.myuniversaltoolapplication.ui.fragment.ThreeFragment;
import com.example.administrator.myuniversaltoolapplication.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.newim.listener.ObseverListener;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;


public class MainActivity extends BaseActivity implements ObseverListener,View.OnClickListener, ViewPager.OnPageChangeListener {


    private RadioButton btn1, btn2, btn3, btn4, btn5;
    private RadioGroup radioGroup;

    private ViewPager viewPager;
    private MyFragPagerAdapter myFragPagerAdapter;

    private ArrayList<Fragment> fragmentList;
    private OneFragment oneFragment;
//    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;
//    private FourFragment fourFragment;
    private FiveFragment fiveFragment;

    private String all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        MyUser user = BmobUser.getCurrentUser(this,MyUser.class);
        BmobIM.connect(user.getObjectId(), new ConnectListener() {
            @Override
            public void done(String uid, BmobException e) {
                if (e == null) {
                    Logger.i("connect success");
                } else {
                    Logger.e(e.getErrorCode() + "/" + e.getMessage());
                }
            }
        });
        //监听连接状态，也可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
        BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
            @Override
            public void onChange(ConnectionStatus status) {
                ToastUtils.show(getApplicationContext(),"" + status.getMsg());
            }
        });
        //解决leancanary提示InputMethodManager内存泄露的问题
        IMMLeaks.fixFocusedViewLeak(getApplication());
    }
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}

    public void initView() {
        btn1 = (RadioButton) findViewById(R.id.mainactivity_btn_btn1);
//        btn2 = (RadioButton) findViewById(R.id.mainactivity_btn_btn2);
        btn3 = (RadioButton) findViewById(R.id.mainactivity_btn_btn3);
//        btn4 = (RadioButton) findViewById(R.id.mainactivity_btn_btn4);
        btn5 = (RadioButton) findViewById(R.id.mainactivity_btn_btn5);
        radioGroup = (RadioGroup) findViewById(R.id.mainactivity_rg_tabs);
        viewPager = (ViewPager) findViewById(R.id.mainactivity_vp_viewpager);
        btn1.setOnClickListener(this);
//        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
//        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);

        if (oneFragment == null) {
            oneFragment = new OneFragment();
        }
//        if (twoFragment == null) {
//            twoFragment = new TwoFragment();
//        }
        if (threeFragment == null) {
            threeFragment = new ThreeFragment();
        }
//        if (fourFragment == null) {
//            fourFragment = new FourFragment();
//        }
        if (fiveFragment == null) {
            fiveFragment = new FiveFragment();
        }
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(oneFragment);
//        fragmentList.add(twoFragment);
        fragmentList.add(threeFragment);
//        fragmentList.add(fourFragment);
        fragmentList.add(fiveFragment);
        viewPager.setOffscreenPageLimit(5);//把viewpager设定为5层才销毁

        myFragPagerAdapter = new MyFragPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(myFragPagerAdapter);
        //viewPager.setOnPageChangeListener(this);
        //因为setOnPageChangeListener不推荐使用了，现在用的是addOnPageChangeListener替换它
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
        radioGroup.check(R.id.mainactivity_btn_btn1);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.mainactivity_btn_btn1:
                viewPager.setCurrentItem(0);
                radioGroup.check(R.id.mainactivity_btn_btn1);
                break;
//            case R.id.mainactivity_btn_btn2:
//                viewPager.setCurrentItem(1);
//                radioGroup.check(R.id.mainactivity_btn_btn2);
//                break;
            case R.id.mainactivity_btn_btn3:
                viewPager.setCurrentItem(1);
                radioGroup.check(R.id.mainactivity_btn_btn3);
                break;
//            case R.id.mainactivity_btn_btn4:
//                viewPager.setCurrentItem(3);
//                radioGroup.check(R.id.mainactivity_btn_btn4);
//                break;
            case R.id.mainactivity_btn_btn5:
                viewPager.setCurrentItem(2);
                radioGroup.check(R.id.mainactivity_btn_btn5);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                radioGroup.check(R.id.mainactivity_btn_btn1);
                break;
            case 1:
                radioGroup.check(R.id.mainactivity_btn_btn3);
                break;
            case 2:
                radioGroup.check(R.id.mainactivity_btn_btn5);
                break;
//            case 3:
//                radioGroup.check(R.id.mainactivity_btn_btn4);
//                break;
//            case 4:
//                radioGroup.check(R.id.mainactivity_btn_btn5);
//                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //添加观察者-用于是否显示通知消息
        BmobNotificationManager.getInstance(this).addObserver(this);//需要mainactivity实现objectListener监听
        //进入应用后，通知栏应取消
        BmobNotificationManager.getInstance(this).cancelNotification();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //移除观察者
        BmobNotificationManager.getInstance(this).removeObserver(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清理导致内存泄露的资源
        BmobIM.getInstance().clear();
        //完全退出应用时需调用clearObserver来清除观察者
        BmobNotificationManager.getInstance(this).clearObserver();
    }
}

