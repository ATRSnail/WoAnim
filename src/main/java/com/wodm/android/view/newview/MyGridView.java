package com.wodm.android.view.newview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

import com.wodm.android.adapter.newadapter.LevelAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by songchenyu on 16/9/26.
 */

public class MyGridView extends GridView {
    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyGridView(Context context) {
        super(context);
    }
    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    /**
     * 屏幕高度
     */
    private int mScreenHeight;
    /**
     * 上一次的坐标
     */
    private float mLastY;
    /**
     * 当前View滑动距离
     */
    private int mScrollY;
    /**
     * 当前View内子控件高度
     */
    private int mChildH;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
       //默认设定顶层View不拦截
        getParent().getParent().requestDisallowInterceptTouchEvent(true);
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
        }
        return super.onTouchEvent(ev);
    }
}