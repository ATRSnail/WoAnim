package com.wodm.android.view.newview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.tools.DisplayUtil;


/**
 * Created by songchenyu on 16/10/21.
 */

public class OfenUseView extends RelativeLayout{
    // 自定义的控件和自定义的属性（values下mytopbar.xml）的声明



    private RelativeLayout.LayoutParams leftLayoutParams, titleLayoutParams, rightLayoutParams;

    private OfenUseView.MyonClickListener clicklistenter;
    private int left_color=0;
    private Drawable rightDrawable;
    private String title;
    private int title_color;
    private float title_size;
    private int backgroundColor;

    //重写构造方法
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public OfenUseView(Context context, AttributeSet attrs) {

        super(context, attrs);

        // 获取自定义的属性，并将自定义的属性映射到自定义的属性值中去
        // 通过obtainStyledAttributes获取自定义属性，并存到TypedArray
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ofenuseview);
        left_color=ta.getColor(R.styleable.ofenuseview_leftBackGround_use,0);
        title=ta.getString(R.styleable.ofenuseview_Customtoptitle_use);
        title_color=ta.getInt(R.styleable.ofenuseview_CustomtitleTextColor_use,0);
        title_size=ta.getDimension(R.styleable.ofenuseview_CustomtitleTextSize_use,24);
        backgroundColor=ta.getColor(R.styleable.ofenuseview_BackGroundColor_use, Color.TRANSPARENT);
        rightDrawable=ta.getDrawable(R.styleable.ofenuseview_rightBackGround_use);

        ImageView imageView=new ImageView(getContext());
        imageView.setBackgroundColor(left_color);
//        imageView.setId(10);

        //将自定义的控件放到Layout中（以LayoutParams的形式）
        leftLayoutParams = new RelativeLayout.LayoutParams(6, 24);
        leftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        leftLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        leftLayoutParams.setMargins(30,0,0,0);
//        leftLayoutParams.setMarginStart((int) getResources().getDimension(R.dimen.activity_horizontal_margin));
        addView(imageView, leftLayoutParams);  //leftButton以leftLayoutParams的形式加入到ViewGroup中
        TextView textView=new TextView(getContext());
        textView.setText(title);
        textView.setTextColor(title_color);
        textView.setTextSize(TypedValue.DENSITY_DEFAULT,title_size);
        titleLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        titleLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);  //设置居中对齐
        titleLayoutParams.addRule(RelativeLayout.RIGHT_OF, 10);
        titleLayoutParams.setMargins(48,0,0,0);
        addView(textView, titleLayoutParams);    //tvTitle以titleLayoutParams的形式加入到ViewGroup中
//
        ImageView img_right=new ImageView(getContext());
        img_right.setBackground(rightDrawable);

        rightLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE); //设置右对齐
        rightLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE); //设置右对齐\
        int padding = DisplayUtil.px2dip(getContext(), 30);
        rightLayoutParams.setMargins(0, 0, padding, 0);
        addView(img_right,rightLayoutParams);
       setBackgroundColor(backgroundColor);

    }

    //自定义一个setOnClickListenter方法
    public void setOnTopbarClickListenter(OfenUseView.MyonClickListener clicklistenter) {
        this.clicklistenter = clicklistenter;   //调用的时候通过一个匿名内部类映射进来
    }

    //自定义click的监听回调接口
    public interface MyonClickListener {
        void leftClick();

        void rightClick();
    }
}
