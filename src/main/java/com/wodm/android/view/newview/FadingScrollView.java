package com.wodm.android.view.newview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.wodm.R;

/**
 * Created by ATRSnail on 2016/12/30.
 * 自定义详情布局 向下滑动导航条消失，背景图片高斯模糊放大 ，松手后导航条完全展示图片，恢复原来状态
 */

public class FadingScrollView extends ScrollView{


    //滑动距离，默认设置滑动500 时完全透明显示，根据实际需求自己设置
    private int fadingHeight = 500;
    //模糊放大的背景图片
    private ImageView imageView;
    //背景图片上层的其他视图
    private View otherView;
    private AtyTopLayout aty;//自定义导航条
    private String TAG = "DragScaleImageView";
    private static final int BACK_SCALE = 1010;


    private int displayWidth = 0;
    private int displayHeight = 0;
    private int mImageId;
    private Bitmap bmp;
    private  Context mContext;
    private AttributeSet attrs;

    /** 是否处在回弹状态 */
    private boolean isBacking = false;

    /** 用于记录拖拉图片移动的坐标位置 */
    private Matrix matrix = new Matrix();
    /** 用于记录图片要进行拖拉时候的坐标位置 */
    private Matrix currentMatrix = new Matrix();
    private Matrix defaultMatrix = new Matrix();
    /** 背景图片的宽高 */
    private float imgHeight, imgWidth;
    /** 初始状态 */
    private int mode = 0;
    /** 拖拉照片模式 */
    private final int MODE_DRAG = 1;

    private float scaleY = 0;

    /** 用于记录开始时候的坐标位置 */
    private PointF startPoint = new PointF();

    /** 用于记录开始时候的在整个屏幕中的Y坐标位置 */
    private float startRawY = 0;
    float scale = 1;

    public FadingScrollView(Context context) {
        super(context);
        this.mContext = context;
        initData();
    }

    public FadingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.attrs = attrs;
        initData();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
    private void initData() {
        otherView= LayoutInflater.from(getContext()).inflate(R.layout.detail_up,null);
        aty = (AtyTopLayout) otherView.findViewById(R.id.set_topbar);
        this.addView(otherView);
    }



    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //        l,t  滑动后 xy位置， oldl lodt 滑动前 xy 位置
        if (t==500){
              aty.setAlpha(0);
        }
    }

}
