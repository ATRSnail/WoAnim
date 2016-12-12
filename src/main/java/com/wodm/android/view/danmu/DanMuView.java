package com.wodm.android.view.danmu;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wodm.android.bean.CommentBean;
import com.wodm.android.view.biaoqing.FaceConversionUtil;

import java.util.List;
import java.util.Random;


/**
 * Created by lixueyg on 16/2/19.
 */
public class DanMuView extends RelativeLayout {
    private Context mContext;
    private BarrageHandler mHandler = new BarrageHandler();
    private Random random = new Random(System.currentTimeMillis());
    private static final long BARRAGE_GAP_MIN_DURATION = 1000;//两个弹幕的最小间隔时间
    private static final long BARRAGE_GAP_MAX_DURATION = 1000;//两个弹幕的最大间隔时间
    private int maxSpeed = 8500;//速度，ms
    private int minSpeed = 8200;//速度，ms
    private int maxSize = 14;//文字大小，dp
    private int minSize = 14;//文字大小，dp

    private int totalHeight = 0;
    private int lineHeight = 0;//每一行弹幕的高度
    private int totalLine = 0;//弹幕的行数
//    private String[] itemText = {"是否需要帮忙", "what are you 弄啥来", "哈哈哈哈哈哈哈", "抢占沙发。。。。。。", "************", "是否需要帮忙",
//            "我不会轻易的狗带", "嘿嘿", "这是我见过的最长长长长长长长长长长长的评论"};
    private int textCount;
    private BarrageHandler barrageHandler=new BarrageHandler();
    private List<CommentBean> commonList;
    private boolean isPause=false;
    private static int i=0;
//    private List<BarrageItem> itemList = new ArrayList<BarrageItem>();

    public DanMuView(Context context) {
        this(context, null);
    }

    public DanMuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public DanMuView(Activity context, List<CommentBean> list) {
        super(context);
        this.commonList=list;
        this.mContext=context;
        init();
    }
    public void setData(List<CommentBean> list){
        this.commonList=list;
//        int duration = (int) ((BARRAGE_GAP_MAX_DURATION - BARRAGE_GAP_MIN_DURATION) * Math.random());
//        mHandler.sendEmptyMessage(duration);
    }

    public DanMuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
//        init();
    }
    public void setIsPause(boolean isPause){
        this.isPause=isPause;
        if (!isPause){
            setVisibility(VISIBLE);
//            mHandler.sendEmptyMessage(1);
        }else {
            setVisibility(GONE);
        }
    }
    private void init() {
        textCount = commonList.size();
        barrageHandler.sendEmptyMessageAtTime(0,100);
//        int duration = (int) ((BARRAGE_GAP_MAX_DURATION - BARRAGE_GAP_MIN_DURATION) * Math.random());
//        mHandler.sendEmptyMessageDelayed(0, duration);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        totalHeight = 60;
        lineHeight = getLineHeight();
        totalLine = totalHeight / lineHeight;
    }

    private void generateItem() {
        BarrageItem item = new BarrageItem();
        int index=(int) (Math.random() * textCount);
        if (index>commonList.size()){
            return;
        }
        CommentBean bean=commonList.get((int) (Math.random() * textCount));

        String tx = bean.getSendCommentContent();
//        String tx = bean.getContent();
        SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(mContext,bean.getSendCommentContent());
//        SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(mContext,bean.getContent());
        int sz = (int) (minSize + (maxSize - minSize) * Math.random());
        item.textView = new TextView(mContext);
        if (!bean.getSendCommentContent().trim().equals("")){
//            if (!bean.getContent().trim().equals("")){
            item.textView.setText(spannableString);
        }
        item.textView.setTextSize(sz);
        item.textView.setTextColor(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
        item.textMeasuredWidth = (int) getTextWidth(item, tx, sz);
        item.moveSpeed = (int) (minSpeed + (maxSpeed - minSpeed) * Math.random());
//        if (totalLine == 0) {
//            totalHeight = getMeasuredHeight();
//            lineHeight = getLineHeight();
//            totalLine = totalHeight / lineHeight;
//        }
//        item.verticalPos = random.nextInt(totalLine) * lineHeight;
//        itemList.add(item);
        showBarrageItem(item);
    }

    private void showBarrageItem(final BarrageItem item) {

        int leftMargin = this.getRight() - this.getLeft() - this.getPaddingLeft();

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        int num[]={0,40};
        int posY;
        Paint paint = new Paint();
        paint.setTextSize(item.textView.getTextSize());
        float size =paint.measureText(item.textView.getText().toString());
        if (i%2==0){
            ++i;
            posY=num[0];
        }else {
            --i;
            posY=num[1];
        }
        params.topMargin = posY;
        this.addView(item.textView, params);

        Animation anim = generateTranslateAnim(item, leftMargin);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                item.textView.clearAnimation();
                DanMuView.this.removeView(item.textView);
                int duration = (int) ((BARRAGE_GAP_MAX_DURATION - BARRAGE_GAP_MIN_DURATION) * Math.random());
                barrageHandler.sendEmptyMessage(duration);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        item.textView.startAnimation(anim);
    }

    private TranslateAnimation generateTranslateAnim(BarrageItem item, int leftMargin) {
        TranslateAnimation anim = new TranslateAnimation(leftMargin, -item.textMeasuredWidth, 0, 0);
        anim.setDuration(5000);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.setFillAfter(true);
        return anim;
    }

    /**
     * 计算TextView中字符串的长度
     *
     * @param text 要计算的字符串
     * @param Size 字体大小
     * @return TextView中字符串的长度
     */
    public float getTextWidth(BarrageItem item, String text, float Size) {
        Rect bounds = new Rect();
        TextPaint paint;
        paint = item.textView.getPaint();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
    }

    /**
     * 获得每一行弹幕的最大高度
     *
     * @return
     */
    private int getLineHeight() {
        BarrageItem item = new BarrageItem();
        if (commonList.size()==0){
            return 45;
        }
        String tx = commonList.get(0).getSendCommentContent();
//        String tx = commonList.get(0).getContent();
        if (tx.equals("")){
            tx="哈哈哈";
        }
        item.textView = new TextView(mContext);
        item.textView.setText(tx);
        item.textView.setTextSize(maxSize);

        Rect bounds = new Rect();
        TextPaint paint;
        paint = item.textView.getPaint();
        paint.getTextBounds(tx, 0, tx.length(), bounds);
        return bounds.height();
    }

    class BarrageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!isPause&&commonList.size()>0){
                    generateItem();
            }
            //每个弹幕产生的间隔时间随机
//            int duration = (int) ((BARRAGE_GAP_MAX_DURATION - BARRAGE_GAP_MIN_DURATION) * Math.random());
//            this.sendEmptyMessageDelayed(0, duration);
        }
    }

}
