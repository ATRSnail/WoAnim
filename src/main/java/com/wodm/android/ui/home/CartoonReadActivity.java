package com.wodm.android.ui.home;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
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
import com.wodm.android.dialog.ShareDialog;
import com.wodm.android.tools.DanmuControler;
import com.wodm.android.tools.Tools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.utils.UpdataUserInfo;
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

import master.flame.danmaku.ui.widget.DanmakuView;

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


    @InflateView(R.layout.layout_bottom_port)
    private View mBottomPortView;
    @InflateView(R.layout.layout_bottom_land)
    private View mBottomLandView;

    @ViewIn(R.id.video_name)
    private TextView mTitleView;

    private final static String TITLE = "阅读漫画";

    private ArrayList<ChapterBean> mChapterList;
    private ArrayList<CarBean> mCarList;
    private ArrayList<CommentBean> commentBeanList;
    private int index = 0;
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
    @ViewIn(R.id.danmaku_view)
    private DanmakuView danmaku_view;
    private boolean isSlidingToLast = false;
    private boolean mIsLoading;
    @ViewIn(R.id.progressBar)
    private RotateLoading progressBar;
    @ViewIn(R.id.send_bullet)
    private ImageView send_bullet;
    private ReadCarAdapter adapter;
    private DanmuControler danmuControler;
    private ImageView danmu_kaiguan;
    private boolean isOpen = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler.sendEmptyMessageAtTime(0, 10000);
        setListView();
        setLoadAndRefresh();
        if (!getIntent().hasExtra("beanPath")) {
            setListView();
            mChapterList = (ArrayList<ChapterBean>) getIntent().getSerializableExtra("ChapterList");
            commentBeanList = (ArrayList<CommentBean>) getIntent().getSerializableExtra("commentList");
            index = getIntent().getIntExtra("index", index);
            bean = (ObjectBean) getIntent().getSerializableExtra("bean");
            if (mTitleView != null && bean != null) {
                mTitleView.setText(bean.getName());
                mCollectView.setChecked(1 == bean.getIsCollect());
            }
            requestHttp(index, true);

        } else {
            String path = getIntent().getStringExtra("beanPath");
            startReadPath(path);
        }

        barrage_rescourceId = bean.getId();
        barrage_charterId = mChapterList.get(0).getId();
        setBottoms();
        findViewById(R.id.send_bullet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                v.setAlpha(0);
                sendBullet();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            getBarrageResource();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

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

    @Override
    public void refrensh(String content) {
        super.refrensh(content);
        danmuControler.addBuilt(content);
//        getBarrageResource();
    }

    private void requestHttp(final int index, final boolean b) {
        if (mChapterList != null && mChapterList.size() > 0 && index < mChapterList.size()) {

            CurrChapter = mChapterList.get(index);
            barrage_charterId = CurrChapter.getId();
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
                        Log.e("","-------------------"+adapter.getItemCount());
//                        setSeekBarView(adapter.getItemCount(),0);
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
//                            Log.e("AAAAAAAAAAAAA","*****************"+bvy);
                            float tvy = mTopView.getY();
                            if (!videoControllerShow && !animation) {
                                animation = true;
                                videoControllerShow = !videoControllerShow;
                                startAnimation(mBottomView, bvy, bvy + mBottomView.getHeight(), animatorListener);
                            } else if (!animation) {
                                videoControllerShow = !videoControllerShow;
                                startAnimation(mBottomView, bvy, bvy - mBottomView.getHeight(), animatorListener);
                            }

//                            myAnimation(bvy);

                        }
                        break;
                }

                return false;
            }
        });
    }

//    private void myAnimation(float bvy) {
//        if (flag) {
//            flag = false;
//            startAnimation(mBottomView, bvy, bvy + mBottomView.getHeight(), animatorListener);
//            Log.e("AAAAAAAAAAAAA","*****************"+bvy+"----------"+mBottomView.getHeight()+"-----------"+ Tools.getScreenHeight(this));
//        } else if (!flag ) {
//            startAnimation(mBottomView, bvy, bvy - mBottomView.getHeight(), animatorListener);
//            Log.e("BBBBBBBBBBBBBBB","*****************"+bvy+"----------"+mBottomView.getHeight()+"-----------"+ Tools.getScreenHeight(this));
//            flag = true;
//        }
//    }

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
                    Log.e("","*******************"+pullToLoadView.getAdapter().getItemCount());
                    setSeekBarView(pullToLoadView.getAdapter().getItemCount(), position + 1);
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
                    ArrayList<BarrageBean> beanArrayList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<BarrageBean>>() {
                    }.getType());
                    initDanMu(beanArrayList);
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
        danmuControler = new DanmuControler(this, danmaku_view);
        danmuControler.addData(barrageBeanList);

    }

    private void setBottoms() {
        mBottomView.removeAllViews();
        int i = getResources().getConfiguration().orientation;
        int visibility = View.GONE;
        if (i == Configuration.ORIENTATION_PORTRAIT) {
            adapter.notifyDataSetChanged();
            mBottomView.addView(mBottomPortView);
//            mBottomPortView.setX(0);
//            mBottomPortView.setY(Tools.getScreenHeight(this));
            mBottomPortView.findViewById(R.id.collect_box).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    collction((CheckBox) v);
                }
            });
        } else if (i == Configuration.ORIENTATION_LANDSCAPE) {
            //notifi主要是为了切换屏幕时让图片跟着变换
            adapter.notifyDataSetChanged();
            mBottomView.addView(mBottomLandView);
//            mBottomLandView.setX(0);
//            mBottomLandView.setY(Tools.getScreenHeight(this));
            visibility = View.VISIBLE;
        }
        final TextView mScreenText = (TextView) mBottomView.findViewById(R.id.screen_orient);
        final TextView mScrollText = (TextView) mBottomView.findViewById(R.id.scroll_orient);
        int org = getResources().getConfiguration().orientation;
        if (org == Configuration.ORIENTATION_PORTRAIT) {
            mScreenText.setText("横屏");
        } else {
            mScreenText.setText("竖屏");
        }
        mScrollText.setText(orientation == 1 ? "左右" : "上下");

        mDowmView.setVisibility(visibility);
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
        danmu_kaiguan.setBackgroundResource(R.mipmap.danmu_close_white);
        mBottomView.findViewById(R.id.ll_danmu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                    danmuControler.hide();
                    tv_danmu_kaiguan.setText("开启弹幕");
                    danmu_kaiguan.setBackgroundResource(R.mipmap.danmu_open_white);
                    isOpen = false;
                } else {
                    danmuControler.show();
                    tv_danmu_kaiguan.setText("关闭弹幕");
                    danmu_kaiguan.setBackgroundResource(R.mipmap.danmu_close_white);
                    isOpen = true;
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
                pullToLoadView.setLayoutManager(manager);
                String textStr = mScrollText.getText().toString();
                int type = 0;
                if (textStr.equals("上下")) {
                    type = 1;
                }
                adapter.setType(type);
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

    @TrackClick(value = R.id.layout_top, location = TITLE, eventName = "退出界面")
    private void clickfinish(View view) {
        finish();
    }

    @TrackClick(value = R.id.collect_boxtop)
    private void clickCollect(View view) {
        if (!UpdataUserInfo.isLogIn(this, true)) {
            return;
        }
        if (view.getId() == R.id.collect_boxtop) {
            mCollectView.setChecked(!mCollectView.isChecked());
        }
        Tracker.getInstance(getApplicationContext()).trackMethodInvoke(TITLE, "点击收藏");
        collction((CheckBox) view);
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
        TextView mProView = (TextView) mBottomView.findViewById(R.id.progress);
        SeekBar mSeek = (SeekBar) mBottomView.findViewById(R.id.carSeekBar);
        mSeek.setMax(max);
        mSeek.setProgress(progress);

        mProView.setText(progress + "/" + max);
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


    private void collction(final CheckBox v) {
        if (!UpdataUserInfo.isLogIn(this, true)) {
            return;
        }

        httpGet(Constants.ULR_COLLECT + Constants.CURRENT_USER.getData().getAccount().getId() + "&resourceId=" + (bean == null ? "-1" : bean.getId()), new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    if (obj.getString("code").equals("1000")) {
                        bean.setIsCollect(1);
                        v.setChecked(bean.getIsCollect() == 1);

                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT
                        ).show();
                    }
                    mCollectView.setChecked(bean.getIsCollect() == 1);
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
        if (danmuControler != null)
            danmuControler.release();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}