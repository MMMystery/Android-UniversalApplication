package com.example.administrator.myuniversaltoolapplication.ui.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.myuniversaltoolapplication.R;
import com.example.administrator.myuniversaltoolapplication.ui.adapter.MyFragPagerAdapter;
import com.example.administrator.myuniversaltoolapplication.ui.fragment.FriendPostFragment;
import com.example.administrator.myuniversaltoolapplication.ui.fragment.HotPostFragment;

import java.util.ArrayList;

public class DiscoverActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private Button topBar_btn_left, topBar_btn_right;
    private RadioGroup rg_radiogroup;
    private RadioButton rb_friendpost, rb_hotpost;


    private ViewPager vp_viewpager;
    private MyFragPagerAdapter myFragPagerAdapter;
    private ArrayList<Fragment> fragmentList;
    private FriendPostFragment friendPostFragment;
    private HotPostFragment hotPostFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        initView();
    }

    private void initView() {
        topBar_btn_left = (Button) findViewById(R.id.topbar_btn_left);
        topBar_btn_right = (Button) findViewById(R.id.topbar_btn_right);

        rg_radiogroup = (RadioGroup) findViewById(R.id.discoveractivity_rg_radiogroup);
        rb_friendpost = (RadioButton) findViewById(R.id.discoveractivity_rb_friendpost);
        rb_hotpost = (RadioButton) findViewById(R.id.discoveractivity_rb_hotpost);
        topBar_btn_left.setOnClickListener(this);
        topBar_btn_right.setOnClickListener(this);
        rb_friendpost.setOnClickListener(this);
        rb_hotpost.setOnClickListener(this);
        topBar_btn_left.setBackgroundResource(R.mipmap.bar_back);
        topBar_btn_right.setBackgroundResource(R.mipmap.bar_add);

        vp_viewpager = (ViewPager) findViewById(R.id.discoveractivity_vp_viewpager);
        vp_viewpager.addOnPageChangeListener(this);

        if (friendPostFragment == null) {
            friendPostFragment = new FriendPostFragment();
        }
        if (hotPostFragment == null) {
            hotPostFragment = new HotPostFragment();
        }
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(friendPostFragment);
        fragmentList.add(hotPostFragment);
        myFragPagerAdapter = new MyFragPagerAdapter(getSupportFragmentManager(), fragmentList);
        vp_viewpager.setAdapter(myFragPagerAdapter);

        rg_radiogroup.check(R.id.discoveractivity_rb_friendpost);
        vp_viewpager.setCurrentItem(0);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topbar_btn_left:
                this.finish();
                break;
            case R.id.topbar_btn_right:
                Intent intent = new Intent(DiscoverActivity.this,NewPostActivity.class);
                startActivity(intent);
                break;
            case R.id.discoveractivity_rb_friendpost:
                rg_radiogroup.check(R.id.discoveractivity_rb_friendpost);
                vp_viewpager.setCurrentItem(0);
                break;
            case R.id.discoveractivity_rb_hotpost:
                rg_radiogroup.check(R.id.discoveractivity_rb_hotpost);
                vp_viewpager.setCurrentItem(1);
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
                rg_radiogroup.check(R.id.discoveractivity_rb_friendpost);
                break;
            case 1:
                rg_radiogroup.check(R.id.discoveractivity_rb_hotpost);
                break;
        }
    }
}
