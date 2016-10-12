package com.wodm.android.view.newview;

import android.content.Context;
import android.util.AttributeSet;
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


}