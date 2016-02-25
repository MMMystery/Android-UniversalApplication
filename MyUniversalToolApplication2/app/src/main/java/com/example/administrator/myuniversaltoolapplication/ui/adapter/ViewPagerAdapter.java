package com.example.administrator.myuniversaltoolapplication.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myuniversaltoolapplication.ui.fragment.TwoFragment;

/**
 * Created by Administrator on 2016/2/25.
 */
public class ViewPagerAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return TwoFragment.imageResId.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
         container.removeView(TwoFragment.imgList.get(position));
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        container.addView(TwoFragment.imgList
                .get(position));
        return TwoFragment.imgList.get(position);
    }
}
