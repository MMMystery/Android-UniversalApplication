package com.example.administrator.myuniversaltoolapplication.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/18.
 */
public class MyFragPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragmentList;

    public MyFragPagerAdapter(FragmentManager fragmentManager, ArrayList<Fragment> fragmentList) {
        super(fragmentManager);
        // TODO Auto-generated constructor stub
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        // TODO Auto-generated method stub
        return fragmentList == null ? null : fragmentList.get(position);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return fragmentList == null ? 0 : fragmentList.size();
    }

}
