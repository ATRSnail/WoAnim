package com.wodm.android.ui.home;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.ReadCarAdapter;
import com.wodm.android.bean.BarrageBean;
import com.wodm.android.bean.CarBean;
import com.wodm.android.bean.ChapterBean;
import com.wodm.android.bean.CommentBean;
import com.wodm.android.bean.DowmBean;
import com.wodm.android.bean.ObjectBean;
import com.wodm.android.dbtools.DBTools;
import com.wodm.android.dialog.ShareDialog;
import com.wodm.android.tools.DanmuControler;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.braageview.BulletSetDialog;
import com.wodm.android.utils.ZipEctractAsyncTask;
import com.wodm.android.view.ChapterWindow;

import org.eteclab.base.annotation.InflateView;
import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.track.Tracker;
import org.eteclab.track.annotation.TrackClick;
import org.eteclab.ui.widget.animation.RotateLoading;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.activity_cartonn_read)
public class CartoonReadActivity extends AppActivity {
    @ViewIn(R.id.anim_dowm)
    private ImageView mDowmView;
    @ViewIn(R.id.anim_share)
    private ImageView mShareView;
    @ViewIn(R.id.collect_boxtop)
    private CheckBox mCollectView;

    @ViewIn(R.id.read_left)
    private ImageButton mLeftBtn;
    @ViewIn(R.id.read_right)
    private ImageButton mRightBtn;

     int flagNum=0;
    @InflateView(R.layout.layout_bottom_port)
    private View mBottomPortView;
    @InflateView(R.layout.layout_bottom_land)
    private View mBottomLandView;

    @ViewIn(R.id.video_name)
    private TextView mTitleView;

    private final static String TITLE = "阅读漫画";

    private ArrayList<ChapterBean> mChapterList;
    private ArrayList<CarBean> mCarList;
//    private ArrayList<CommentBean> commentBeanList;
    private int  index= 0;
    private ChapterBean CurrChapter;
    private ObjectBean bean = null;
    private boolean videoControllerShow = false;//底部状态栏的显示状态
    private boolean animation = false;
    private Dialog dialog = null;


    private int orientation = 1;
    @ViewIn(R.id.recyclerView)
    private RecyclerView pullToLoadView;
    @ViewIn(R.id.layout_bottom)
    private LinearLayout mBottomView;
    @ViewIn(R.id.layout_top)
    private View mTopView;
    @ViewIn(R.id.swipeRefreshLayout)
    private SwipeRefreshLayout refreshLayout;
    @ViewIn(R.id.mDanmakuView_read_top)
    private master.flame.danmaku.ui.widget.DanmakuView mDanmakuView_read_top;
    @ViewIn(R.id.mDanmakuView_read_middle)
    private master.flame.danmaku.ui.widget.DanmakuView mDanmakuView_read_middle;
    @ViewIn(R.id.mDanmakuView_read_bottom)
    private master.flame.danmaku.ui.widget.DanmakuView mDanmakuView_read_bottom;
    @ViewIn(R.id.ll_danmu_background)
    private LinearLayout ll_danmu_background;
    private boolean isSlidingToLast = false;
    private boolean mIsLoading;
    @ViewIn(R.id.progressBar)
    private RotateLoading progressBar;
    @ViewIn(R.id.send_bullet)
    private ImageView send_bullet;
    private ReadCarAdapter adapter;
    private DanmuControler danmuControler_top;
    private DanmuControler danmuControler_middle;
    private DanmuControler danmuControler_bottom;
    private ImageView danmu_kaiguan;
//    private boolean isOpen = false;
    private boolean isOpen = true;
     private String num="";
    int pageNum=0;
    private ArrayList<BarrageBean> beanArrayList=new ArrayList<>();
    //timetask
    private Handler bullethandler=null;
    private Runnable bullettask;
    private  int watchIndex=1;//作品的具体集数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //记录数据
        CartoonReadPosition=0;
        handler.sendEmptyMessage(1);
        setListView();
        setLoadAndRefresh();
//        handler111.sendEmptyMessageDelayed(1,5000);
        if (!getIntent().hasExtra("beanPath")) {
            setListView();
            mChapterList = (ArrayList<ChapterBean>) getIntent().getSerializableExtra("ChapterList");
//            commentBeanList = (ArrayList<CommentBean>) getIntent().getSerializableExtra("commentList");
            index = getIntent().getIntExtra("index", index);
            watchIndex = getIntent().getIntExtra("watchIndex", watchIndex);
            bean = (ObjectBean) getIntent().getSerializableExtra("bean");


            requestHttp(index, true);

        } else {
            String path = getIntent().getStringExtra("beanPath");
            startReadPath(path);
        }


        barrage_rescourceId = bean.getId();

        barrage_charterId = mChapterList.get(index).getId();
        setBottoms();
        findViewById(R.id.send_bullet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                v.setAlpha(0);
                sendBullet();
            }
        });
        startDanmuTimeTask();
    }
    private BarrageBean showbean=null;
    //主要是控制位置,防止重复出现弹幕
    int showPosition=0;
    private void startDanmuTimeTask(){
        bullethandler = new Handler();
        bullettask = new Runnable() {

            public void run() {
                for (int i = showPosition; i < beanArrayList.size(); i++) {
                    BarrageBean barrageBean=beanArrayList.get(i);
                    if (showbean==null||showbean!=barrageBean){
                        int position=Integer.parseInt(barrageBean.getPlayTime());
                        if (position==CartoonReadPosition){
                            showPosition=i+1;
                            Message msg=new Message();
                            msg.obj=barrageBean;
                            bulletHandler.sendMessageDelayed(msg,200);
                            break;
                        }else if (position!=CartoonReadPosition){

                        }
                    }
                }
//                for (BarrageBean bean:beanArrayList) {
//                        if (showbean==null||showbean!=bean){
//                            int position=Integer.parseInt(bean.getPlayTime());
//                            if (position==CartoonReadPosition){
//                                Message msg=new Message();
//                                msg.obj=bean;
//                                bulletHandler.sendMessageDelayed(msg,200);
//                                break;
//                            }else if (position!=CartoonReadPosition){
//
//                            }
//                        }
//                }
                bullethandler.postDelayed(this, 800);
            }
        };
        bullettask.run();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                getBarrageResource();
            }else if (msg.what==2){
//                getBullets(0);
            }else if (msg.what==3){
                int position= (int) msg.obj;
//                getBullets(position);
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //插入开始时间
        DBTools.getInstance(this).inserDB(barrage_rescourceId);

    }
    @Override
    protected void onPause() {
        super.onPause();
        //更新结束时间
        DBTools.getInstance(this).updateDB(barrage_rescourceId);
        DBTools.getInstance(this).stopService();
        if (bullethandler!=null&&bullettask!=null){
            bullethandler.removeCallbacks(bullettask);
        }

    }

    private void startReadPath(String path) {
        ZipEctractAsyncTask asyncTask = new ZipEctractAsyncTask();
        getIntent().putExtra("beanPath", path);
        asyncTask.setZipEctractCallback(new ZipEctractAsyncTask.ZipEctractCallback() {
            @Override
            public void onEctractSucess(File file) {
                mCarList = new ArrayList<CarBean>();
                if (file.exists() && file.isDirectory()) {
                    File[] fs = file.listFiles();
                    for (File f : fs) {
                        CarBean carBean = new CarBean();
                        carBean.setContentUrl(f.getAbsolutePath());
                        mCarList.add(carBean);
                    }
                }
                adapter.setListData(mCarList);
                pullToLoadView.setAdapter(adapter);

            }

            @Override
            public void onEctractFaulite(File file) {
                Toast.makeText(getApplicationContext(), "文件出错!!", Toast.LENGTH_SHORT).show();
            }
        });
        asyncTask.execute(path, new File(path).getParent() + "/cache");
    }
    int bulletnumfds=10;
    @Override
    public void refrensh(String content,String color,int position,int playTime) {
        super.refrensh(content,color,position,playTime);
        if (playTime!=-1){
            BarrageBean barrageBean=new BarrageBean();
            barrageBean.setContent(content);
            barrageBean.setColor(color);
            barrageBean.setLocation(position);
            barrageBean.setPlayTime(playTime+"");
            beanArrayList.add(barrageBean);
        }
        if (playTime!=-1){
            return;
        }
        int bulletColor=Color.WHITE;
        if (!color.equals("")){
            bulletColor=Color.parseColor(color);
        }

        if (position==1){
            danmuControler_top.setDanmakuView(mDanmakuView_read_top);
            danmuControler_top.addBuilt(content,bulletColor);
        }else if (position==2){
            danmuControler_middle.setDanmakuView(mDanmakuView_read_middle);
            danmuControler_middle.addBuilt(content,bulletColor);
        }else if (position==3){
            danmuControler_bottom.setDanmakuView(mDanmakuView_read_bottom);
            danmuControler_bottom.addBuilt(content,bulletColor);
        }else {
            danmuControler_top.setDanmakuView(mDanmakuView_read_top);
            danmuControler_top.addBuilt(content,bulletColor);
        }

//        getBarrageResource();
    }
    ArrayList<BarrageBean> positionList=new ArrayList<>();
    private void getBullets(int position){
        positionList.clear();
        for (BarrageBean bean:beanArrayList) {
            int platetime=Integer.parseInt(bean.getPlayTime());
            if (platetime==position){
                positionList.add(bean);
            }
        }
//        handler111.sendEmptyMessage(1);
//        if (beanArrayList.size()>0){
//            runnable.run();
//        }
    }
//    //主要是防止滑动太快 造成卡顿
//    private Runnable runnable=new Runnable() {
//        @Override
//        public void run() {
//            for (BarrageBean bean:positionList) {
//                Message msg=new Message();
//                msg.obj=bean;
//                handler111.sendMessageAtTime(msg,100);
//            }
//        }
//    };

//    Handler handler111=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            refrensh("zheshiygeceshishuju ","#ffffff",1,1);
//        }
//    };

    private void requestHttp(final int index, final boolean b) {
        if (mTitleView != null && bean != null) {
            num = bean.getName()+" "+(watchIndex);//新的详情标题

//            num = bean.getName()+" "+(index+1);
            mTitleView.setText(num);//漫画标题+集数
            mCollectView.setChecked(1 == bean.getIsCollect());
        }
        if (mChapterList != null && mChapterList.size() > 0 && index < mChapterList.size()) {

            CurrChapter = mChapterList.get(index);
            barrage_charterId = CurrChapter.getId();
            handler.sendEmptyMessage(1);
            httpGet(Constants.HOST + "resource/cartoon/" + CurrChapter.getId(), new HttpCallback() {

                @Override
                public void doRequestFailure(Exception exception, String msg) {
                    super.doRequestFailure(exception, msg);
                    stopLoad();
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthFailure(result, obj);
                    stopLoad();
                }

                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthSuccess(result, obj);
                    try {
//                        PullCallbackImpl
                        ArrayList<CarBean> mCarList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<CarBean>>() {
                        }.getType());
                        ArrayList<CarBean> lists = new ArrayList<>(adapter.getData());
//                        Toast.makeText(getApplicationContext(), index + "lists -- " + lists.contains(mCarList), 0).show();
                        if (b) {
                            lists.clear();
                            lists.addAll(0, mCarList);
                        } else {
                            lists.addAll(mCarList);
                        }
                        adapter.setListData(lists);
                        adapter.notifyDataSetChanged();
                        //（修改）每一次请求漫画时，就重新设定seekbar的值
                        if(lists!=null&& lists.size()>0)
                            setSeekBarView(lists.size(),0);
                        stopLoad();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            getBarrageResource();

        }
    }

    boolean flag = true;
    int mBottomViewHight=0;

    private void setListView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pullToLoadView.setLayoutManager(layoutManager);

        orientation = layoutManager.getOrientation();
        adapter = new ReadCarAdapter(this);
        pullToLoadView.setAdapter(adapter);



        pullToLoadView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startx = (int) event.getX();
                        starty = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        int endx = (int) event.getX();
                        int endy = (int) event.getY();

                        if (Math.sqrt(((startx - endx) * (startx - endx) + (starty - endy) * (starty - endy))) < 5) {
                            float bvy = mBottomView.getY();
                            int hight=mBottomView.getHeight();
                            if (mBottomViewHight<hight){
                                mBottomViewHight=hight;
                            }

                            if (!videoControllerShow && !animation) {
                                animation = true;
                                videoControllerShow = !videoControllerShow;
                                startAnimation(mBottomView, bvy, bvy +mBottomViewHight, animatorListener);
                            } else if (!animation) {
                                videoControllerShow = !videoControllerShow;
                                startAnimation(mBottomView, bvy, bvy - mBottomViewHight, animatorListener);
                            }

                        }
                        break;
                }

                return false;
            }
        });
    }

    private void setLoadAndRefresh() {
        refreshLayout.setColorSchemeResources(R.color.colorPrimary);
        progressBar.setColor(R.color.colorPrimary);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新
                if (index > 0) {
                    index -= 1;
                } else {
                    index = 0;
                    stopLoad();
                    return;
                }
                if (!mIsLoading)
                    requestHttp(index, true);
                mIsLoading = true;
            }
        });
        pullToLoadView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int position = 0;

                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                } else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    position = ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                }
                if (pullToLoadView.getAdapter() != null)

                    setSeekBarView(pullToLoadView.getAdapter().getItemCount(), position);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!mIsLoading) {
                    int position;
                    if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                        position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    } else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                        position = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                        StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                        int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
                        position = getMaxPosition(lastPositions);
                    } else {
                        position = recyclerView.getLayoutManager().getItemCount() - 1;
                    }

                    int lastVisiblePosition = position;
                    if (lastVisiblePosition + 1 == recyclerView.getAdapter().getItemCount()) {
                        if (mChapterList != null && index < mChapterList.size() - 1) {
                            pageNum=recyclerView.getAdapter().getItemCount();
                            index += 1;
                        } else {
                            return;
                        }
                        progressBar.start();
                        mIsLoading = true;
                        requestHttp(index, false);
                    }
                }
            }
        });

    }

    private int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            maxPosition = Math.max(maxPosition, positions[i]);
        }
        return maxPosition;
    }


    private void stopLoad() {
        mIsLoading = false;
        refreshLayout.setRefreshing(false);
        progressBar.stop();
    }

    private void getBarrageResource() {
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("resourceId", resourceId);
//            obj.put("chapterId", bean.getId());
//            obj.put("page", 1);
//            obj.put("size", 100);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        httpGet(Constants.URL_GET_BARRAGE + "?resourceId=" + barrage_rescourceId + "&chapterId=" + barrage_charterId, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    ArrayList<BarrageBean> list = new Gson().fromJson(obj.getString("data"), new TypeToken<List<BarrageBean>>() {
                    }.getType());
                    beanArrayList.clear();
                    beanArrayList.addAll(list);
                    initDanMu(beanArrayList);
//                    handler.sendEmptyMessage(2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void initDanMu(final ArrayList<BarrageBean> barrageBeanList) {
        //弹幕
//        List<IDanmakuItem> list = initItems(commentbeanList);
//        Collections.shuffle(list);
//        mDanmakuView.addItem(list, true);
//        mDanmakuView.show();
        ArrayList<BarrageBean> arrayList_top=new ArrayList<>();
        ArrayList<BarrageBean> arrayList_middle=new ArrayList<>();
        ArrayList<BarrageBean> arrayList_bottom=new ArrayList<>();
        for (BarrageBean bean:barrageBeanList){
            if (bean.getLocation()==1){
                arrayList_top.add(bean);
            }else if (bean.getLocation()==2){
                arrayList_middle.add(bean);
            }else {
                arrayList_bottom.add(bean);
            }
        }
//        int progress= Preferences.getInstance(this).getPreference("bullet_toumingdu", 0);
//        int alpha= (int) (progress*2.5);
//        ll_danmu_background.setAlpha(alpha);
//        ll_danmu_background.invalidate();
        danmuControler_top = new DanmuControler(this, mDanmakuView_read_top);
//        danmuControler_top.addData(arrayList_top);
        danmuControler_middle = new DanmuControler(this, mDanmakuView_read_middle);
//        danmuControler_middle.addData(arrayList_middle);
        danmuControler_bottom = new DanmuControler(this, mDanmakuView_read_bottom);
//        danmuControler_bottom.addData(arrayList_bottom);
        //开启弹幕时钟
    }

    private void setBottoms() {
        mBottomView.removeAllViews();
        int i = getResources().getConfiguration().orientation;
        int visibility = View.GONE;
        if (i == Configuration.ORIENTATION_PORTRAIT) {
            mBottomView.forceLayout();
            mBottomView.removeAllViews();
            adapter.notifyDataSetChanged();
            mBottomView.addView(mBottomPortView,-1);
            mBottomPortView.findViewById(R.id.collect_box).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Constants.CURRENT_USER == null) {
                        Toast.makeText(getApplicationContext(), "未登录，请先登录", Toast.LENGTH_SHORT).show();
                        CheckBox   mCollect_box = (CheckBox) mBottomPortView.findViewById(R.id.collect_box);
                        mCollect_box.setChecked(!mCollect_box.isChecked());
                        return;
                    }else {
                        collction((CheckBox) v);
                    }

                }
            });
        } else if (i == Configuration.ORIENTATION_LANDSCAPE) {
            mBottomView.forceLayout();
            mBottomView.removeAllViews();
            //notifi主要是为了切换屏幕时让图片跟着变换
            adapter.notifyDataSetChanged();
            mBottomView.addView(mBottomLandView,-1);
//            mBottomLandView.setX(0);
//            mBottomLandView.setY(Tools.getScreenHeight(this));
            visibility = View.VISIBLE;
        }
        final TextView mScreenText = (TextView) mBottomView.findViewById(R.id.screen_orient);
        final TextView mScrollText = (TextView) mBottomView.findViewById(R.id.scroll_orient);
        final int org = getResources().getConfiguration().orientation;
        if (org == Configuration.ORIENTATION_PORTRAIT) {
            mScreenText.setText("横屏");
        } else {
            mScreenText.setText("竖屏");
        }
        mScrollText.setText(orientation == 1 ? "左右" : "上下");
//将缓存图标隐藏
//        mDowmView.setVisibility(visibility);
        mShareView.setVisibility(visibility);
        mCollectView.setVisibility(visibility);

        CheckBox mCollect_box = (CheckBox) mBottomView.findViewById(R.id.collect_box);
        if (mCollect_box == null) {
            mCollect_box = (CheckBox) mBottomPortView.findViewById(R.id.collect_box);
        }
        if (bean == null) {
            mCollect_box.setEnabled(false);
        } else {
            mCollect_box.setChecked(1 == bean.getIsCollect());
            mCollectView.setChecked(mCollect_box.isChecked());
        }
        final TextView tv_danmu_kaiguan = (TextView) mBottomView.findViewById(R.id.tv_danmu_kaiguan);
        final ImageView danmu_kaiguan = (ImageView) mBottomView.findViewById(R.id.danmu_kaiguan);
        if (isOpen) {
            tv_danmu_kaiguan.setText("开启弹幕");
            danmu_kaiguan.setBackgroundResource(R.mipmap.danmu_open_white);
        } else {
            tv_danmu_kaiguan.setText("关闭弹幕");
            danmu_kaiguan.setBackgroundResource(R.mipmap.danmu_close_white);
        }
//        danmu_kaiguan.setBackgroundResource(R.mipmap.danmu_open_white);
        mBottomView.findViewById(R.id.ll_danmu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen=!isOpen;
                if (isOpen) {
                    danmuControler_top.show();
                    danmuControler_middle.show();
                    danmuControler_bottom.show();
                    tv_danmu_kaiguan.setText("开启弹幕");
                    danmu_kaiguan.setBackgroundResource(R.mipmap.danmu_open_white);
//                    isOpen = false;
                } else {
                    danmuControler_top.hide();
                    danmuControler_middle.hide();
                    danmuControler_bottom.hide();
                    tv_danmu_kaiguan.setText("关闭弹幕");
                    danmu_kaiguan.setBackgroundResource(R.mipmap.danmu_close_white);
//                    isOpen = true;
                }
            }
        });
        mBottomView.findViewById(R.id.chapter_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tracker.getInstance(getApplicationContext()).trackMethodInvoke(TITLE, "点击选集");
//                                           finish();
                ChapterWindow chapterWindow = new ChapterWindow() {
                    @Override
                    public void requestHttps(int position) {
                        super.requestHttps(position);
                        index = position;
                        //（修改）点击选集，重新加载内容，从零开始
                        setListView();

                        requestHttp(index, true);
                    }

                    @Override
                    public void requestPaths(DowmBean bean) {
                        super.requestPaths(bean);

                        startReadPath(bean.getPath());
                    }
                };
                ArrayList<DowmBean> dowmBeanArrayList = (ArrayList<DowmBean>) getIntent().getSerializableExtra("pathList");
                if (dowmBeanArrayList == null) {
                    chapterWindow.showPopWindow(CartoonReadActivity.this, mBottomView, mChapterList, index);
                } else {
                    chapterWindow.showPopWindows(CartoonReadActivity.this, mBottomView, dowmBeanArrayList, getIntent().getStringExtra("beanPath"));
                }
            }
        });


        mBottomView.findViewById(R.id.orientation_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tracker.getInstance(getApplicationContext()).trackMethodInvoke(TITLE, "滑动方向切换");
                LinearLayoutManager manager = new LinearLayoutManager(CartoonReadActivity.this);
                manager.setOrientation(orientation == LinearLayoutManager.HORIZONTAL ? 1 : 0);
                orientation = manager.getOrientation();
                mScrollText.setText(orientation == 1 ? "左右" : "上下");
                mLeftBtn.setVisibility(orientation == 0 ? View.VISIBLE : View.INVISIBLE);
                mRightBtn.setVisibility(orientation == 0 ? View.VISIBLE : View.INVISIBLE);
                String textStr = mScrollText.getText().toString();
                String textScreen = mScreenText.getText().toString();
                setType(textStr,textScreen);
                pullToLoadView.setLayoutManager(manager);
            }
        });


        mBottomView.findViewById(R.id.screen_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tracker.getInstance(getApplicationContext()).trackMethodInvoke(TITLE, "横屏竖屏切换");
                int i = getResources().getConfiguration().orientation;
                if (i == Configuration.ORIENTATION_PORTRAIT) {
                    mScreenText.setText("横屏");
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else if (i == Configuration.ORIENTATION_LANDSCAPE) {
                    mScreenText.setText("竖屏");
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }

            }
        });
        String textStr = mScrollText.getText().toString();
        String textScreen = mScreenText.getText().toString();
        setType(textStr,textScreen);

    }
    private void setType(String text,String textScreen){
        int type = 0;
        if (textScreen.equals("横屏")) {
            if (text.equals("上下")) {
                type = 1;
            }else {
                type = 0;
            }
        } else if (textScreen.equals("竖屏")) {
            adapter.setType(type);
            if (text.equals("上下")) {
                type = 3;
            }else {
                type = 2;
            }
        }
        adapter.setType(type);
        adapter.notifyDataSetChanged();
    }

    int startx = 0;
    int starty = 0;

    Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator ag) {
            animation = false;
        }

        @Override
        public void onAnimationCancel(Animator ag) {

        }

        @Override
        public void onAnimationRepeat(Animator ag) {

        }
    };

    private void startAnimation(View vi, float fromy, float toy, Animator.AnimatorListener l) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(vi, "y",
                fromy, toy);
        animator.setDuration(300);
        animator.start();
        if (l != null)
            animator.addListener(l);
    }

    @TrackClick(R.id.read_left)
    public void clickLeft(View v) {

        LinearLayoutManager manager = (LinearLayoutManager) pullToLoadView.getLayoutManager();
        int postion = manager.findFirstVisibleItemPosition();
        pullToLoadView.smoothScrollToPosition(postion == 0 ? 0 : postion - 1);
    }

    @TrackClick(R.id.read_right)
    public void clickRight(View v) {
        LinearLayoutManager manager = (LinearLayoutManager) pullToLoadView.getLayoutManager();
        int postion = manager.findLastVisibleItemPosition();
        pullToLoadView.smoothScrollToPosition(postion == manager.getItemCount() - 1 ? postion : postion + 1);
    }

    @TrackClick(value = R.id.exit_screen, location = TITLE, eventName = "退出界面")
    private void clickfinish(View view) {
            Intent intent=new Intent();
            intent.putExtra("key",flagNum);
            intent.putExtra("bean", bean);
            setResult(RESULT_OK, intent);
            flagNum=0;
        finish();
    }
    @TrackClick(value = R.id.img_set, location = TITLE, eventName = "弹出设置")
    private void clickimg_set(View view) {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        BulletSetDialog bulletSetDialog= BulletSetDialog.newInstance(new BulletSetDialog.setBulletSetDialogListener() {
            @Override
            public void setDialogListener(int progress) {
                float progress1=1-(float)(progress/100);
                mDanmakuView_read_top.setAlpha(progress1);
                mDanmakuView_read_middle.setAlpha(progress1);
                mDanmakuView_read_bottom.setAlpha(progress1);
//                ll_danmu_background.setBackgroundColor(getResources().getColor(R.color.color_333333));
//                ll_danmu_background.getBackground().setAlpha(progress);
            }
        });
        bulletSetDialog.show(ft,"dialog");
    }


    @TrackClick(value = R.id.collect_boxtop)
    private void clickCollect(View view) {

        if (Constants.CURRENT_USER == null) {
            mCollectView.setChecked(!mCollectView.isChecked());
            Toast.makeText(getApplicationContext(), "未登录，请先登录", Toast.LENGTH_SHORT).show();
            return;
        }else {
            if (view.getId() == R.id.collect_boxtop) {
//                mCollectView.setChecked(!mCollectView.isChecked());
                Tracker.getInstance(getApplicationContext()).trackMethodInvoke(TITLE, "点击收藏");
                collction((CheckBox) view);
            }

        }

    }

    @TrackClick(value = R.id.anim_share)
    private void clickShare(View view) {
        Tracker.getInstance(getApplicationContext()).trackMethodInvoke(TITLE, "分享");
        dialog = new ShareDialog(this, bean.getName(), bean.getDesp(), Constants.SHARE_URL + bean.getId(), bean.getShowImage());
        dialog.show();
//        OnkeyShare share = new OnkeyShare(this);
//        share.setTitle(bean.getName());
//        share.setImageUrl(bean.getShowImage());
//        share.setDescription(bean.getDesp());
//        share.setTargUrl(Constants.SHARE_URL + bean.getId());
//        share.addShareCall(new ShareResultCall() {
//            @Override
//            public void onShareSucess() {
//                super.onShareSucess();
//                Toast.makeText(getApplicationContext(), "分享成功", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onShareCancel() {
//                super.onShareCancel();
//                Toast.makeText(getApplicationContext(), "用户取消分享", Toast.LENGTH_SHORT).show();
//            }
//        });
//        share.setShareType(OnkeyShare.SHARE_TYPE.SHARE_WEB);
//        share.show();
    }

    @TrackClick(value = R.id.anim_dowm)
    private void clickDowm(View view) {
        Tracker.getInstance(getApplicationContext()).trackMethodInvoke(TITLE, "下载");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setBottoms();
    }


    private void setSeekBarView(final int max, int progress) {
        if (CartoonReadPosition>progress||CartoonReadPosition<progress){
            if (showPosition==beanArrayList.size()){
                showPosition=0;
            }else if (CartoonReadPosition==0||CartoonReadPosition==1){
                showPosition=0;
            }
        }
        CartoonReadPosition=progress;
        if (pageNum!=0&&pageNum<progress){
            CartoonReadPosition=progress-pageNum;
            showPosition=0;
        }
        final TextView mProView = (TextView) mBottomView.findViewById(R.id.progress);
        final SeekBar mSeek = (SeekBar) mBottomView.findViewById(R.id.carSeekBar);
        mSeek.setMax(max-1);
        mSeek.setProgress(progress);
//        if(progress==1||progress==0) mSeek.setProgress(0);
        mProView.setText((progress+1) + "/" + (max));
        mSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                pullToLoadView.smoothScrollToPosition(seekBar.getProgress());
            }
        });
    }
//    private Runnable runnable=new Runnable() {
//        @Override
//        public void run() {
//            bulletHandler.sendEmptyMessage(1);
//        }
//    };
//    private BarrageBean showbean=null;
    int num111=100;
    private Handler bulletHandler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BarrageBean bean= (BarrageBean) msg.obj;
            if (bean==null){
                return;
            }
            refrensh(bean.getContent(),bean.getColor(),bean.getLocation(),-1);
//            for (BarrageBean bean:beanArrayList) {
//               if (showbean==null||showbean!=bean){
//                   int position=Integer.parseInt(bean.getPlayTime());
//                   if (position==CartoonReadPosition){
//                       refrensh(bean.getContent(),bean.getColor(),bean.getLocation(),-1);
//                   }
//               }
//            }
        }
    };


    public void collction(final CheckBox v) {

        httpGet(Constants.ULR_COLLECT + Constants.CURRENT_USER.getData().getAccount().getId() + "&resourceId=" + (bean == null ? "-1" : bean.getId()), new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    if (obj.getString("code").equals("1000")) {
                        bean.setIsCollect(1);
                        v.setChecked(bean.getIsCollect() == 1);
                        mCollectView.setChecked(bean.getIsCollect() == 1);
                        flagNum=1;
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT
                        ).show();

                    }
//                    mCollectView.setChecked(bean.getIsCollect() == 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
                try {
                    if (obj.getString("code").equals("2000")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT
                        ).show();
                        bean.setIsCollect(0);

                    } else {
                        CheckBox box = v;
                        box.setChecked(!box.isChecked());
                    }
                    setResult(RESULT_CANCELED);
                    v.setChecked(bean.getIsCollect() == 1);
                    mCollectView.setChecked(bean.getIsCollect() == 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (danmuControler_top != null)
            danmuControler_top.release();
        if (danmuControler_middle != null)
            danmuControler_middle.release();
        if (danmuControler_bottom != null)
            danmuControler_bottom.release();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}