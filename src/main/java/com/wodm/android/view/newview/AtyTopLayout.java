package com.wodm.android.view.newview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wodm.R;
import com.wodm.android.tools.DisplayUtil;

/**
 * Created by songchenyu on 16/9/26.
 */

public class AtyTopLayout extends RelativeLayout {
    // 自定义的控件和自定义的属性（values下mytopbar.xml）的声明
    private ImageView leftImage, rightImage;
    private TextView tv_right;
    private TextView tvTitle;

    private Drawable leftDrawable;

    private float titleTextSize;
    private int titleTextColor;
    private int backgroundColor;
    private String titleText;
    /**
     * 1.代表现实字体   2.表示显示图片  0 表示不显示
     */
    private int rightType;

    private int rightDrawable;
    private int rightTextColor;
    private String rightText;
    private float rightTextSize;

    private LayoutParams leftLayoutParams, titleLayoutParams, rightLayoutParams;

    private myTopbarClicklistenter clicklistenter;

    //重写构造方法
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public AtyTopLayout(Context context, AttributeSet attrs) {

        super(context, attrs);

        // 获取自定义的属性，并将自定义的属性映射到自定义的属性值中去
        // 通过obtainStyledAttributes获取自定义属性，并存到TypedArray
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.secondTopbar);

        leftDrawable = ta.getDrawable(R.styleable.secondTopbar_leftBackGround);

        titleTextSize = ta.getDimension(R.styleable.secondTopbar_CustomtitleTextSize, 0);
        titleTextColor = ta.getColor(R.styleable.secondTopbar_CustomtitleTextColor, 0);
        backgroundColor = ta.getColor(R.styleable.secondTopbar_BackGroundColor, Color.TRANSPARENT);
        titleText = ta.getString(R.styleable.secondTopbar_Customtoptitle);
        rightType = ta.getInteger(R.styleable.secondTopbar_rightType,0);
        if (rightType==2){
            rightDrawable = ta.getResourceId(R.styleable.secondTopbar_rightBackGround, 0);
        }else if (rightType==1){
            rightText= ta.getString(R.styleable.secondTopbar_rightText);
            rightTextColor = ta.getColor(R.styleable.secondTopbar_rightTextColor,0);
            rightTextSize = ta.getDimension(R.styleable.secondTopbar_rightTextSize,0);
        }

        ta.recycle();

        //组件定义
        leftImage = new ImageView(context);
        tvTitle = new TextView(context);


        // 将自定义的属性设置到控件上
        leftImage.setImageDrawable(leftDrawable);

        tvTitle.setTextColor(titleTextColor);
        tvTitle.setTextSize(TypedValue.DENSITY_DEFAULT, getResources().getDimension(R.dimen.text_size_32_px));
        tvTitle.setText(titleText);
        tvTitle.setGravity(Gravity.CENTER);    // 设置文字居中

//        rightImage.setImageDrawable(rightDrawable);


        setBackgroundColor(backgroundColor);    // 设置背景颜色

        //将自定义的控件放到Layout中（以LayoutParams的形式）
        leftLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        leftLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);     //设置左对齐
        leftLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
//        leftLayoutParams.setMarginStart((int) getResources().getDimension(R.dimen.activity_horizontal_margin));
        leftImage.setPaddingRelative(20, 0, 15, 0);
        addView(leftImage, leftLayoutParams);  //leftButton以leftLayoutParams的形式加入到ViewGroup中

        titleLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        titleLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);  //设置居中对齐
        addView(tvTitle, titleLayoutParams);    //tvTitle以titleLayoutParams的形式加入到ViewGroup中

        rightLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rightLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE); //设置右对齐
        rightLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE); //设置右对齐
//        rightLayoutParams.setMarginEnd((int) getResources().getDimension(R.dimen.activity_horizontal_margin));
        int padding= DisplayUtil.px2dip(getContext(),30);
        if (rightType==2){
            rightImage = new ImageView(context);
            Toast.makeText(context, ""+rightDrawable, Toast.LENGTH_SHORT).show();
            rightImage.setImageResource(rightDrawable);
            rightImage.setPaddingRelative(padding, 0, padding, 0);
            addView(rightImage, rightLayoutParams);//rightButton以rightLayoutParams的形式加入到ViewGroup中
            rightImage.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicklistenter.rightClick();
                }
            });
        }else if (rightType==1){
            tv_right = new TextView(context);
            tv_right.setText(rightText);
            tv_right.setTextSize(TypedValue.DENSITY_DEFAULT, getResources().getDimension(R.dimen.text_size_28_px));
            tv_right.setTextColor(rightTextColor);
            tv_right.setPaddingRelative(padding, 0, padding, 0);
            addView(tv_right, rightLayoutParams);

            tv_right.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    clicklistenter.rightClick();
                }
            });
        }

        //设置监听事件
        leftImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clicklistenter.leftClick();
            }
        });



    }

    //自定义一个setOnClickListenter方法
    public void setOnTopbarClickListenter(myTopbarClicklistenter clicklistenter) {
        this.clicklistenter = clicklistenter;   //调用的时候通过一个匿名内部类映射进来
    }

    //设置左Button是否显示
    public void setLeftIsVisible(boolean flag) {
        if (flag) {
            leftImage.setVisibility(View.VISIBLE);
        } else {
            leftImage.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    // 设置右Button是否显示
    public void setRightIsVisible(boolean flag) {
        if (flag) {
            rightImage.setVisibility(View.VISIBLE);
        } else {
            if (rightImage!=null) {
                rightImage.setVisibility(View.GONE);
            }
        }
    }
    // 设置右Button是否显示
    public void setRightImageResource(int imageResource) {
        rightImage.setImageResource(imageResource);
    }
    public void setBackgroundResource(String backgroundColor) {
        setBackgroundColor(Color.parseColor(backgroundColor));
    }

    public void setTvTitle(String text) {
        tvTitle.setText(text);
    }

    //自定义click的监听回调接口
    public interface myTopbarClicklistenter {
        void leftClick();

        void rightClick();
    }
}
