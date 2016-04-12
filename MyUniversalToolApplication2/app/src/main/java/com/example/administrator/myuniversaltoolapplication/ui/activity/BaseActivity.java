package com.example.administrator.myuniversaltoolapplication.ui.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.administrator.myuniversaltoolapplication.utils.ActivityCollectorUtil;

/**
 * Created by Administrator on 2015/12/17.
 */
public  class BaseActivity extends FragmentActivity {
//    private FrameLayout frameLayout;
//    private View rootView;
//
//    protected abstract int getViewResource();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCollectorUtil.addActivity(this);

//        frameLayout = new FrameLayout(this);
//        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
//        rootView = LayoutInflater.from(this).inflate(getViewResource(), null);
//        rootView.setLayoutParams(lp);
//        frameLayout.addView(rootView);
//        setContentView(frameLayout);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(this);
    }
}
