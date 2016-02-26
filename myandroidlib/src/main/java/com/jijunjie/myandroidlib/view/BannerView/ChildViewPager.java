package com.jijunjie.myandroidlib.view.BannerView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;

/**
 * Created by jijunjie on 16/2/26.
 */
public class ChildViewPager extends ViewPager {
    public ChildViewPager(Context context) {
        super(context);
    }

    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    // to consume the hover event
    @Override
    protected boolean dispatchHoverEvent(MotionEvent event) {
        return true;
    }
    // to consume the drag event
    @Override
    public boolean dispatchDragEvent(DragEvent event) {
        return super.dispatchDragEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {


        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (this.getChildCount() > 1) { // 有内容，多于1个时
                    // 通知其父控件，现在进行的是本控件的操作，不允许拦�?
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (this.getChildCount() > 1) { // 有内容，多于1个时
                    // 通知其父控件，现在进行的是本控件的操作，不允许拦�?
                }
                break;
            case MotionEvent.ACTION_UP:
                // 在up时判断是否按下和松手的坐标为�?个点
                break;
        }

        return super.onTouchEvent(ev);
    }
}

