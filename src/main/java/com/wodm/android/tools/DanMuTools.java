package com.wodm.android.tools;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ImageSpan;

import com.wodm.R;
import com.wodm.android.bean.BarrageBean;
import com.wodm.android.utils.Preferences;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.loader.android.DanmakuLoaderFactory;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDisplayer;
import master.flame.danmaku.danmaku.model.android.BaseCacheStuffer;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.model.android.SpannedCacheStuffer;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.parser.IDataSource;
import master.flame.danmaku.danmaku.parser.android.BiliDanmukuParser;
import master.flame.danmaku.danmaku.util.IOUtils;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * Created by songchenyu on 16/9/26.
 */

public class DanMuTools {
    private DanmakuView mDanmakuView;
    private WoDanmakuParser mParser;
    private master.flame.danmaku.controller.IDanmakuView IDanmakuView;
    private DanmakuContext mDanmakuContext;
    private HashMap<Integer, Integer> maxLinesPair;// 弹幕最大行数
    private HashMap<Integer, Boolean> overlappingEnablePair;// 设置是否重叠
    private String timeArr[]={"11.201999664307","30.43099975586","40.26400756836","60.92900085449","131.8450012207","105.78199768066","233.826000213623"};
    private Context mContext;
    private ArrayList<BarrageBean> mChapterList;
    public DanMuTools(Context context,DanmakuView mDanmakuView){
        this.mContext=context;
        this.mDanmakuView=mDanmakuView;
        handler.sendEmptyMessageAtTime(1,10000);
    }
    private void initDanmaku() {
        mDanmakuContext = DanmakuContext.create();

        // 设置最大行数,从右向左滚动(有其它方向可选)
        maxLinesPair=new HashMap<>();
        maxLinesPair.put(BaseDanmaku.TYPE_SCROLL_RL,1);

        overlappingEnablePair = new HashMap<>();
        overlappingEnablePair.put(BaseDanmaku.TYPE_SCROLL_LR, false);
        overlappingEnablePair.put(BaseDanmaku.TYPE_FIX_BOTTOM, false);



        mDanmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_DEFAULT, 2) //设置描边样式
                .setDuplicateMergingEnabled(false)
                .setScrollSpeedFactor(1.2f) //是否启用合并重复弹幕
                .setScaleTextSize(1.2f) //设置弹幕滚动速度系数,只对滚动弹幕有效
                .setFBDanmakuVisibility(false)
                .setFTDanmakuVisibility(false)
                .setCacheStuffer(new SpannedCacheStuffer(), mCacheStufferAdapter) // 图文混排使用SpannedCacheStuffer  设置缓存绘制填充器，
                // 默认使用{@link SimpleTextCacheStuffer}只支持纯文字显示,
                // 如果需要图文混排请设置{@link SpannedCacheStuffer}
                // 如果需要定制其他样式请扩展{@link SimpleTextCacheStuffer}|{@link SpannedCacheStuffer}
                .setMaximumLines(maxLinesPair) //设置最大显示行数
                .preventOverlapping(overlappingEnablePair); //设置防弹幕重叠，null为允许重叠
        init();
    }
    private void init(){
        if (mDanmakuView != null) {
            int sudu=Preferences.getInstance(mContext).getPreference("bullet_sudu", 3);
            mParser=new WoDanmakuParser();
            if (mChapterList!=null&&mChapterList.size()>0){
                BulletModel bul=new BulletModel();
                List<BulletModel.BarrageListBean> listBean=new ArrayList<>();
                BulletModel.BarrageListBean ba=null;
                Random random=new Random();
                for (BarrageBean bean:mChapterList) {
                    ba=new BulletModel.BarrageListBean();
                    ba.setContext(bean.getContent());
                    ba.setFontColor(ba.getFontColor());
                    ba.setFontSize(mContext.getResources().getDimension(R.dimen.text_size_30_px)+"");
                    int time=random.nextInt(timeArr.length);
                    ba.setTime(time);
                    ba.setId(bean.getId());
                    ba.setState("1");
                    ba.setSudu(sudu);
                    listBean.add(ba);
                }
                bul.setBarrageList(listBean);
                mParser.setmDanmuListData(bul);
            }
            mDanmakuView.start();
//            mParser = createParser(this.getResources().openRawResource(R.raw.comments)); //创建解析器对象，从raw资源目录下解析comments.xml文本
            mDanmakuView.prepare(mParser, mDanmakuContext);
            mDanmakuView.showFPS(false); //是否显示FPS
            mDanmakuView.enableDanmakuDrawingCache(true);
        }

    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                initDanmaku();
            }else if (msg.what==2){
                init();
            }
        }
    };


   //FDAFSA
    private void addDanmaku() {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL,mDanmakuContext);
        if (danmaku == null || mDanmakuView == null) {
            return;
        }

        // for(int i=0;i<100;i++){
        // }

//        mParser = new WoDanmakuParser();
//        mDanmakuView.resume();
//        mDanmakuView.showFPS(false);
//        mDanmakuView.enableDanmakuDrawingCache(true);
        if (mParser!=null&& mParser.getDisplayer()!=null){
            danmaku.text = "bullet.getContent()";
            danmaku.padding = 100;
            danmaku.priority = 0;  // 可能会被各种过滤器过滤并隐藏显示
            danmaku.time = mDanmakuView.getCurrentTime() + 1200;
            danmaku.textSize = 12;
//            danmaku.textSize=20f;
            danmaku.textColor = Color.RED;
            danmaku.textShadowColor = Color.WHITE;
//            danmaku.underlineColor = Color.GREEN;
//            danmaku.borderColor = Color.GREEN;
            mDanmakuView.addDanmaku(danmaku);
//            getBullets();
        }
    }

    /**
     * 添加文本弹幕
     */
    private void addDanmaku(String text,int color) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku == null || mDanmakuView == null) {
            return;
        }
        danmaku.text = text;
        danmaku.padding = 5;
        danmaku.priority = 0;  //0 表示可能会被各种过滤器过滤并隐藏显示 //1 表示一定会显示, 一般用于本机发送的弹幕
        danmaku.isLive = true; //是否是直播弹幕
        danmaku.time =mDanmakuView.getCurrentTime()+1200; //显示时间
        danmaku.textSize = mContext.getResources().getDimension(R.dimen.text_size_30_px);
        danmaku.textColor = color;
//        danmaku.textShadowColor = Color.WHITE; //阴影/描边颜色
//        danmaku.borderColor = Color.GREEN; //边框颜色，0表示无边框
        mDanmakuView.addDanmaku(danmaku);
    }
    public void setDanMuView(DanmakuView mDanmakuView){
        this.mDanmakuView=mDanmakuView;
    }

    /**
     * 添加图文混排弹幕
     * @param islive
     */
    private void addDanmaKuShowTextAndImage(boolean islive) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        Drawable drawable = mContext.getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(0, 0, 100, 100);
        SpannableStringBuilder spannable = createSpannable(drawable);
        danmaku.text = spannable;
        danmaku.padding = 5;
        danmaku.priority = 1;  // 一定会显示, 一般用于本机发送的弹幕
        danmaku.isLive = islive;
        danmaku.time = mDanmakuView.getCurrentTime() + 1200;
        danmaku.textSize = 25f * (mParser.getDisplayer().getDensity() - 0.6f);
        danmaku.textColor = Color.RED;
        danmaku.textShadowColor = 0; // 重要：如果有图文混排，最好不要设置描边(设textShadowColor=0)，否则会进行两次复杂的绘制导致运行效率降低
        danmaku.underlineColor = Color.GREEN;
        mDanmakuView.addDanmaku(danmaku);
    }

    /**
     * 创建图文混排模式
     * @param drawable
     * @return
     */
    private SpannableStringBuilder createSpannable(Drawable drawable) {
        String text = "bitmap";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        ImageSpan span = new ImageSpan(drawable);//ImageSpan.ALIGN_BOTTOM);
        spannableStringBuilder.setSpan(span, 0, text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append("图文混排");
        spannableStringBuilder.setSpan(new BackgroundColorSpan(Color.parseColor("#8A2233B1")), 0, spannableStringBuilder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableStringBuilder;
    }


    private BaseCacheStuffer.Proxy mCacheStufferAdapter = new BaseCacheStuffer.Proxy() {

        private Drawable mDrawable;

        /**
         * 在弹幕显示前使用新的text,使用新的text
         * @param danmaku
         * @param fromWorkerThread 是否在工作(非UI)线程,在true的情况下可以做一些耗时操作(例如更新Span的drawblae或者其他IO操作)
         * @return 如果不需重置，直接返回danmaku.text
         */

        public void prepareDrawing(final BaseDanmaku danmaku, boolean fromWorkerThread) {
            if (danmaku.text instanceof Spanned) { // 根据你的条件检查是否需要需要更新弹幕
                // FIXME 这里只是简单启个线程来加载远程url图片，请使用你自己的异步线程池，最好加上你的缓存池
                new Thread() {

                    @Override
                    public void run() {
                        String url = "http://www.bilibili.com/favicon.ico";
                        InputStream inputStream = null;
                        Drawable drawable = mDrawable;
                        if (drawable == null) {
                            try {
                                URLConnection urlConnection = new URL(url).openConnection();
                                inputStream = urlConnection.getInputStream();
                                drawable = BitmapDrawable.createFromStream(inputStream, "bitmap");
                                mDrawable = drawable;
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                IOUtils.closeQuietly(inputStream);
                            }
                        }
                        if (drawable != null) {
                            drawable.setBounds(0, 0, 100, 100);
                            SpannableStringBuilder spannable = createSpannable(drawable);
                            danmaku.text = spannable;
                            if (mDanmakuView != null) {
                                mDanmakuView.invalidateDanmaku(danmaku, false);
                            }
                            return;
                        }
                    }
                }.start();
            }
        }

        @Override
        public void releaseResource(BaseDanmaku danmaku) {
            // TODO 重要:清理含有ImageSpan的text中的一些占用内存的资源 例如drawable
        }
    };

    /**
     * 创建解析器对象，解析输入流
     * @param stream
     * @return
     */
    private BaseDanmakuParser createParser(InputStream stream) {

        if (stream == null) {
            return new BaseDanmakuParser() {

                @Override
                protected Danmakus parse() {
                    return new Danmakus();
                }
            };
        }

        // DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI) //xml解析
        // DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_ACFUN) //json文件格式解析
        ILoader loader = DanmakuLoaderFactory.create(DanmakuLoaderFactory.TAG_BILI);

        try {
            loader.load(stream);
        } catch (IllegalDataException e) {
            e.printStackTrace();
        }
        BaseDanmakuParser parser = new BiliDanmukuParser();
        IDataSource<?> dataSource = loader.getDataSource();
        parser.load(dataSource);
        return parser;

    }

    protected void onDestroy() {
        // 释放弹幕资源
        if (mDanmakuView != null) {
            // dont forget release!
            mDanmakuView.release();
            mDanmakuView = null;
        }
    }
    protected void show(){
        mDanmakuView.show();
    }

    protected void hide(){
        mDanmakuView.hide();
    }
    protected void addData(ArrayList<BarrageBean> mChapterList){
        if (this.mChapterList!=null&&this.mChapterList.size()>0){
            this.mChapterList.addAll(mChapterList);
        }else {
            this.mChapterList=mChapterList;
        }
        initDanmaku();

    }

    protected void addDanmakuContraller(String text,int color){
        addDanmaku(text,color);
    }
}
