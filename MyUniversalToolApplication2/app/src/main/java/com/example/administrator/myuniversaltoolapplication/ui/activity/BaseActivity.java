package com.example.administrator.myuniversaltoolapplication.ui.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.myuniversaltoolapplication.utils.ActivityCollectorUtil;

/**
 * Created by Administrator on 2015/12/17.
 */
public  class BaseActivity extends AppCompatActivity {
//    private FrameLayout frameLayout;
//    private View rootView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCollectorUtil.addActivity(this);

/*        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        findViewById(android.R.id.content).setBackgroundColor(getResources().getColor(R.color.col_424242));
        findViewById(android.R.id.content).setFitsSystemWindows(true);*/

//        int statusBarHeight = ScreenUtils.getStatusHeight(this.getBaseContext());
//        findViewById(android.R.id.content).setPadding(0, statusBarHeight, 0, 0);

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
