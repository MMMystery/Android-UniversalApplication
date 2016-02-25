package com.example.administrator.myuniversaltoolapplication.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.ui.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class TwoFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private Button topBar_btn_left, topBar_btn_right;
    private TextView topBar_tv_title;
    private ViewPager vp_viewPager;
    public static List<ImageView> imgList;
    public static int[] imageResId = new int[]{R.drawable.viewpager1, R.drawable.viewpager2,
            R.drawable.viewpager3, R.drawable.viewpager4,
            R.drawable.viewpager5};
    private LinearLayout ll_dotAll;
    ImageView[] imgDots;
    private int currentItem = 0;
    private ScheduledExecutorService scheduledExecutorService;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            vp_viewPager.setCurrentItem(currentItem);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);

        initView(view);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 5,
                TimeUnit.SECONDS);
    }

    private class ScrollTask implements Runnable {

        public void run() {
            synchronized (vp_viewPager) {
                currentItem = (currentItem + 1) % imgList.size();
                handler.obtainMessage().sendToTarget();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        scheduledExecutorService.shutdown();//因为我用的都是fragment，只要还在5个fregment的中任何一个页面上，这个服务都还没别关闭
    }

    private void initView(View view) {
        topBar_btn_left = (Button) view.findViewById(R.id.topbar_btn_left);
        topBar_btn_right = (Button) view.findViewById(R.id.topbar_btn_right);
        topBar_tv_title = (TextView) view.findViewById(R.id.topbar_tv_title);
        vp_viewPager = (ViewPager) view.findViewById(R.id.fragment_two_vp_viewPager);
        ll_dotAll = (LinearLayout) view.findViewById(R.id.fragment_two_ll_dotAll);
        topBar_btn_left.setOnClickListener(this);
        topBar_btn_right.setOnClickListener(this);
        vp_viewPager.addOnPageChangeListener(this);
        vp_viewPager.setAdapter(new ViewPagerAdapter());
        //加上轮播里的图片
        imgList = new ArrayList<ImageView>();
        for (int i = 0; i < imageResId.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageResource(imageResId[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgList.add(imageView);
        }
        // 加上轮播图片里的小点点
        imgDots = new ImageView[imgList.size()];

        ll_dotAll.removeAllViews();// 这个很重要，每次都清除上次加载的点点，不然会重复加载
        if (imgDots.length > 1) {// 如果图片为一张，不显示小圆点
            for (int i = 0; i < imgList.size(); i++) {

                ImageView imgDot = new ImageView(getActivity());
                imgDot.setBackgroundResource(R.drawable.shape_dot_normal);
                imgDot.setScaleType(ImageView.ScaleType.CENTER_CROP);// 设置图片填充方式
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(20, 20);
                lp.setMargins(5, 0, 0, 0);
                imgDot.setLayoutParams(lp);

                imgDots[i] = imgDot;
                ll_dotAll.addView(imgDots[i]);
                if (i == 0) {
                    imgDot.setBackgroundResource(R.drawable.shape_dot_focused);
                } else {
                    imgDot.setBackgroundResource(R.drawable.shape_dot_normal);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topbar_btn_left:
                Toast.makeText(getActivity(), "lefttwo", Toast.LENGTH_LONG).show();
                break;
            case R.id.topbar_btn_right:
                Toast.makeText(getActivity(), "rigthtwo", Toast.LENGTH_LONG).show();
                break;
        }

    }

    @Override
    public void onPageSelected(int position) {
        if (imgDots.length > 1) {// 如果图片为一张，不显示小圆点
            for (int i = 0; i < imgDots.length; i++) {
                if (i == position % imgDots.length) {
                    imgDots[i].setBackgroundResource(R.drawable.shape_dot_focused);
                } else {
                    imgDots[i].setBackgroundResource(R.drawable.shape_dot_normal);
                }
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
