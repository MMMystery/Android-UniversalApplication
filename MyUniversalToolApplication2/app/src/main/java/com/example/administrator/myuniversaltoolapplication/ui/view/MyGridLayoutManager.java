package com.example.administrator.myuniversaltoolapplication.ui.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by y8042 on 2016/5/23.
 */
public class MyGridLayoutManager extends GridLayoutManager {
    public MyGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public MyGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
//        int height = 0;
//        int childCount = getItemCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = recycler.getViewForPosition(i);
//            measureChild(child, widthSpec, heightSpec);
//            if (i % getSpanCount() == 0) {
//                int measuredHeight = child.getMeasuredHeight() + getDecoratedBottom(child);
//                height += measuredHeight;
//            }
//        }
//        setMeasuredDimension(View.MeasureSpec.getSize(widthSpec), height);
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                View.MeasureSpec.AT_MOST);
        super.onMeasure(recycler,state,widthSpec, expandSpec);
    }
}
