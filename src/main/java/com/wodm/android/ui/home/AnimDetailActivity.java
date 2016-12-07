package com.wodm.android.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.CommentAdapter;
import com.wodm.android.adapter.SeriesAdapter;
import com.wodm.android.bean.AnimLookCookieBean;
import com.wodm.android.bean.BarrageBean;
import com.wodm.android.bean.ChapterBean;
import com.wodm.android.bean.CommentBean;
import com.wodm.android.bean.ObjectBean;
import com.wodm.android.db.WoDbUtils;
import com.wodm.android.dbtools.DBTools;
import com.wodm.android.dialog.ChapterDialog;
import com.wodm.android.dialog.DownDialog;
import com.wodm.android.dialog.ShareDialog;
import com.wodm.android.qq.KeyboardLayout;
import com.wodm.android.receiver.NetworkChangeListener;
import com.wodm.android.receiver.NetworkChangeReceive;
import com.wodm.android.run.DBService;
import com.wodm.android.tools.BiaoqingTools;
import com.wodm.android.tools.DanmuControler;
import com.wodm.android.tools.JianpanTools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.braageview.BulletSetDialog;
import com.wodm.android.utils.DialogUtils;
import com.wodm.android.utils.Preferences;
import com.wodm.android.utils.ScreenSwitchUtils;
import com.wodm.android.utils.UpdataUserInfo;
import com.wodm.android.view.CommonVideoView;
import com.wodm.android.view.DividerLine;
import com.wodm.android.view.biaoqing.FaceConversionUtil;
import com.wodm.android.view.biaoqing.FaceRelativeLayout;
import com.wodm.android.view.danmu.DanmakuItem;
import com.wodm.android.view.danmu.IDanmakuItem;

import org.eteclab.base.annotation.InflateView;
import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.base.utils.CommonUtil;
import org.eteclab.track.Tracker;
import org.eteclab.ui.widget.CircularImage;
import org.eteclab.ui.widget.NoScrollGridView;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


@Layout(R.layout.activity_anim_detail)
public class AnimDetailActivity extends AppActivity implements NetworkChangeListener,FaceRelativeLayout.BiaoQingClickListener,CommonVideoView.setTimeDBListener {
    @ViewIn(R.id.common_videoView)
    private static CommonVideoView videoView;
    private final String TITLE = "动画详情";

    private List<ChapterBean> mChapterList;

    @ViewIn(R.id.pull_list)
    private PullToLoadView pullToLoadView;

    @InflateView(R.layout.layout_cartoon_detail)
    private View mHeaderView;
    private NoScrollGridView mChapterView;
    private CircularImage img_xiaolian;

    private TextView mCarDesp;
    private TextView mChapterDesp;
    private TextView dianji_num;

    private TextView mTitleDesp;
    public EditText mCommentView;

    private int resourceId = -1;
    private ObjectBean bean = null;
    private ChapterBean mCurrintChapter;
    private FaceRelativeLayout ll_qq_biaoqing;

    private CheckBox isCollectBox;
    private BiaoqingTools biaoqingtools;
    private ArrayList<CommentBean> commentBeanList;
    @ViewIn(R.id.danmaku_view_top)
    private master.flame.danmaku.ui.widget.DanmakuView mDanmakuView_top;
    @ViewIn(R.id.danmaku_view_middle)
    private master.flame.danmaku.ui.widget.DanmakuView mDanmakuView_middle;
    @ViewIn(R.id.danmaku_view_bottom)
    private master.flame.danmaku.ui.widget.DanmakuView mDanmakuView_bottom;

    @ViewIn(R.id.ll_bottom)
    private LinearLayout ll_bottom;
    private DanmuControler danmuControler;
    @ViewIn(R.id.header)
    private CircularImage header;
    @ViewIn(R.id.ll_danmu_background)
    private LinearLayout ll_danmu_background;
    private ImageView danmu_kaiguan;
    private boolean isOpen = false;
    private Dialog dialog = null;
    private boolean isLandscape;
    private boolean isClickFullScreenButton;
    private boolean isSennor=true;
    private boolean isLoadMore=false;
    private Intent serviceIntent;
    private static Context context;

    private NetworkChangeReceive networkChangeReceive;
    private AnimDetailActivity.MyVideoReceiver myvideoReceiver;
    //---------------------
    private KeyboardLayout mKeyboardLayout;
    private View mEmojiView;
    private CircularImage mEmojiBtn;
    private EditText mInput;
    private RelativeLayout ll_car_details;
    private ScreenSwitchUtils screenSwitchUtils;
    private DBTools dbTools;

    int mKeyboardHeight = 400; // 输入法默认高度为400
    private void initHeaderViews() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        videoView.setSendBulletListener(this);
        dianji_num = (TextView) mHeaderView.findViewById(R.id.dianji_num);
        danmu_kaiguan = (ImageView) mHeaderView.findViewById(R.id.danmu_kaiguan);
        danmu_kaiguan.setOnClickListener(onClickListener);
        mTitleDesp = (TextView) mHeaderView.findViewById(R.id.car_title);
        mCarDesp = (TextView) mHeaderView.findViewById(R.id.desc_op_tv);
        mChapterDesp = (TextView) mHeaderView.findViewById(R.id.chapter_desp);
        mChapterView = (NoScrollGridView) mHeaderView.findViewById(R.id.grid_new);
        isCollectBox = (CheckBox) mHeaderView.findViewById(R.id.anim_collect2);
        mHeaderView.findViewById(R.id.anim_dowm1).setOnClickListener(onClickListener);
        mHeaderView.findViewById(R.id.anim_share3).setOnClickListener(onClickListener);
        isCollectBox.setOnClickListener(onClickListener);
        screenSwitchUtils=ScreenSwitchUtils.init(this);
        videoView.findViewById(R.id.img_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                BulletSetDialog bulletSetDialog= BulletSetDialog.newInstance(new BulletSetDialog.setBulletSetDialogListener() {
                    @Override
                    public void setDialogListener(int progress) {
                        ll_danmu_background.setBackgroundColor(getResources().getColor(R.color.color_333333));
                        ll_danmu_background.getBackground().setAlpha(progress);
                    }
                });
                bulletSetDialog.show(ft,"dialog");
            }
        });
    }

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
    }
    private void initView(){

        // 起初的布局可自动调整大小
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ll_car_details= (RelativeLayout) findViewById(R.id.ll_anim_details);
        ll_car_details.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                JianpanTools.HideKeyboard(mInput);
                return false;
            }
        });
        mKeyboardLayout = (KeyboardLayout) findViewById(R.id.keyboard_layout);
        mEmojiView = findViewById(R.id.ll_qq_biaoqing);
        mEmojiBtn = (CircularImage) findViewById(R.id.img_xiaolian);

        mInput = (EditText) findViewById(R.id.comment_text);

        // 点击输入框
        mInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mKeyboardLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() { // 输入法弹出之后，重新调整
                        mEmojiBtn.setSelected(false);
                        mEmojiView.setVisibility(View.GONE);
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    }
                }, 250); // 延迟一段时间，等待输入法完全弹出
            }
        });

        mEmojiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmojiBtn.setSelected(!mEmojiBtn.isSelected());

                if (mKeyboardLayout.isKeyboardActive()) { // 输入法打开状态下
                    if (mEmojiBtn.isSelected()) { // 打开表情
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING); //  不改变布局，隐藏键盘，emojiView弹出
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(mInput.getApplicationWindowToken(), 0);
                        mEmojiView.setVisibility(View.VISIBLE);
                    } else {
                        mEmojiView.setVisibility(View.GONE);
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(mInput.getApplicationWindowToken(), 0);
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    }
                } else { //  输入法关闭状态下
                    if (mEmojiBtn.isSelected()) {
                        // 设置为不会调整大小，以便输入弹起时布局不会改变。若不设置此属性，输入法弹起时布局会闪一下
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                        mEmojiView.setVisibility(View.VISIBLE);
                    } else {
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        mEmojiView.setVisibility(View.GONE);
                    }
                }
            }
        });

        mKeyboardLayout.setKeyboardListener(new KeyboardLayout.KeyboardLayoutListener() {
            @Override
            public void onKeyboardStateChanged(boolean isActive, int keyboardHeight) {

                if (isActive) { // 输入法打开
                    if (mKeyboardHeight != keyboardHeight) { // 键盘发生改变时才设置emojiView的高度，因为会触发onGlobalLayoutChanged，导致onKeyboardStateChanged再次被调用
                        mKeyboardHeight = keyboardHeight;
                        initEmojiView(); // 每次输入法弹起时，设置emojiView的高度为键盘的高度，以便下次emojiView弹出时刚好等于键盘高度
                    }
                    if (mEmojiBtn.isSelected()) { // 表情打开状态下
                        mEmojiView.setVisibility(View.GONE);
                        mEmojiBtn.setSelected(false);
                    }
                }
            }
        });
    }

    // 设置表情栏的高度
    private void initEmojiView() {
        ViewGroup.LayoutParams layoutParams = mEmojiView.getLayoutParams();
        layoutParams.height = mKeyboardHeight;
        mEmojiView.setLayoutParams(layoutParams);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeaderViews();
        dbTools=DBTools.getInstance(this);
        videoView.setTimeListener(AnimDetailActivity.this);
        initView();
        context=AnimDetailActivity.this;
        videoView.setTimeListener(this);
        myvideoReceiver=new AnimDetailActivity.MyVideoReceiver();
        IntentFilter intentfilter=new IntentFilter("com.vodeo.play.notifition");
        getBaseContext().registerReceiver(myvideoReceiver,intentfilter);
        biaoqingtools = BiaoqingTools.getInstance();
        resourceId = getIntent().getIntExtra("resourceId", -1);
        DividerLine line = new DividerLine();
        line.setSize(getResources().getDimensionPixelSize(R.dimen.px_1));
        line.setColor(Color.rgb(0xD8, 0xD8, 0xD8));

//        mCommentView = (EditText) findViewById(R.id.comment_text);
//        mCommentView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (JianpanTools.KeyBoard(mCommentView)) {
//                    ll_qq_biaoqing.setVisibility(View.GONE);
//                }
//            }
//        });
        if (Constants.CURRENT_USER != null) {
            new AsyncImageLoader(this, R.mipmap.default_header, R.mipmap.default_header).display(header, Constants.CURRENT_USER.getData().getAccount().getPortrait());
        }
        findViewById(R.id.anim_send_comment).setOnClickListener(onClickListener);
//        img_xiaolian = (CircularImage) findViewById(R.id.img_xiaolian);
//        img_xiaolian.setOnClickListener(onClickListener);
        //增加表情
        ll_qq_biaoqing = (FaceRelativeLayout) findViewById(R.id.ll_qq_biaoqing).findViewById(R.id.faceRelativeLayout);
        ll_qq_biaoqing.setOnCorpusSelectedListener(this);
        if (Constants.CURRENT_USER != null){
            findViewById(R.id.anim_send_comment).setEnabled(Constants.CURRENT_USER != null);
        }
        registerReceiver();
        pullToLoadView.getRecyclerView().addItemDecoration(line);
        pullToLoadView.setLoadingColor(R.color.colorPrimary);
        commentBeanList = new ArrayList<>();
        pullToLoadView.setPullCallback(new PullCallbackImpl(pullToLoadView) {
            @Override
            protected void requestData(final int pager, final boolean b) {
                //解决分页重复请求只能请求到同一个数据BUG
                if (commentBeanList.size() % 10 == 0||isLoadMore) {
                    httpGet(Constants.URL_GET_COMMENTS + resourceId + "&page=" + pager, new HttpCallback() {

                        @Override
                        public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                            super.doAuthSuccess(result, obj);
                            try {
                                ArrayList<CommentBean> beanList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<CommentBean>>() {
                                }.getType());
                                if (beanList.size() == 0) {
                                    beanList.add(new CommentBean());
                                }
                                commentBeanList = beanList;
                                handleData(pager, beanList, CommentAdapter.class, b, mHeaderView);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                            super.doAuthFailure(result, obj);
                        }
                    });
                } else {
                    isLoadMore=false;
                    pullToLoadView.setComplete();
                }

            }
        });

        if (Constants.CURRENT_USER != null) {
            String url = Constants.USER_ADD_WATCH_RECORD + "?userId=" + Constants.CURRENT_USER.getData().getAccount().getId() + "&resourceId=" + resourceId + "&taskType=1&taskValue=2";
            httpGet(url, new HttpCallback());
        }
        httpGet(Constants.APP_UPDATERESOURCECOUNT + resourceId, new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }
        });
        httpGet(Constants.APP_GETATERESOURCECOUNT + resourceId, new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    JSONObject jsonObject = new JSONObject(obj.optString("data"));
                    int playcount = jsonObject.optInt("playCount");
                    dianji_num.setText("点击量:" + playcount + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }
        });

        videoView.setVideoCall(new CommonVideoView.VideoViewCall() {
            @Override
            public void doVideoCollection(CheckBox box) {
                if (Constants.CURRENT_USER == null) {
                    box.setChecked(!box.isChecked());
                    Toast.makeText(getApplicationContext(), "未登录，请先登录", Toast.LENGTH_SHORT).show();
                    return;
                }else
                {box.setChecked(1 != bean.getIsCollect());
                collction(box);}
            }

            @Override
            public void doVideoPlayNext() {
                super.doVideoPlayNext();
                int index = mChapterList.indexOf(mCurrintChapter);
                if (mChapterList != null && index >= 0 && index + 1 < mChapterList.size()) {
                    startPlay(mChapterList.get(index + 1));
                } else {
//
                }
            }

            @Override
            public void doChapterList() {
                super.doChapterList();
                ChapterDialog dialog = new ChapterDialog(AnimDetailActivity.this);
                dialog.setListViews(mChapterList, mCurrintChapter);
                dialog.show(true);
            }

            @Override
            public void doVideoDowm() {
                showDowmData();

            }

            @Override
            public void doVideoShare() {
                showShare();
                super.doVideoShare();
            }
        });
        if (getIntent().hasExtra("beanPath")) {
            resourceId = Integer.valueOf(getIntent().getStringExtra("resourceId"));
            videoView.start(getIntent().getStringExtra("beanPath"));
        }
        getCarToon();
    }

    private void CommentData() {
        httpGet(Constants.URL_GET_COMMENTS + resourceId + "&page=" + 1, new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    List<CommentBean> beanList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<CommentBean>>() {
                    }.getType());
                    if (beanList.size() == 0) {
                        beanList.add(new CommentBean());
                    }
                    if (beanList.size()%10==0){
                        isLoadMore=false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }
        });
    }


    //
    SeriesAdapter seriesAdapter;

    private void setSeriesView() {
        mChapterView.setNumColumns(8);
        seriesAdapter = new SeriesAdapter(this, mChapterList, 8);

        mChapterView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ChapterBean bn = mChapterList.get(position);
                if (((TextView) view.findViewById(R.id.button)).getText().equals("更多")) {
                    seriesAdapter.setShowAll();
                } else {
                    if (!getIntent().hasExtra("beanPath"))
                        startPlay(bn);
                }
            }
        });
        mChapterView.setAdapter(seriesAdapter);
        if (mChapterList != null && mChapterList.size() > 0 && !(getIntent().hasExtra("beanPath"))) {
            startPlay(mChapterList.get(0));
        }
        pullToLoadView.initLoad();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        JianpanTools.HideKeyboard(mInput);
        return super.onTouchEvent(event);
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
        danmuControler = new DanmuControler(this, mDanmakuView_top);
        danmuControler.addData(arrayList_top);
        danmuControler = new DanmuControler(this, mDanmakuView_middle);
        danmuControler.addData(arrayList_middle);
        danmuControler = new DanmuControler(this, mDanmakuView_bottom);
        danmuControler.addData(arrayList_bottom);
    }

    private List<IDanmakuItem> initItems(List<CommentBean> commentbeanList) {
        List<IDanmakuItem> list = new ArrayList<>();
        for (CommentBean iDanmakuItem : commentbeanList) {
            SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(this, iDanmakuItem.getContent());
            IDanmakuItem item = new DanmakuItem(this, spannableString, mDanmakuView_top.getWidth(), R.color.colorAccent, R.color.colorAccent, 0, 1.5f);
            list.add(item);
        }

        return list;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (danmuControler != null)
            danmuControler.release();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        if (serviceIntent!=null){
            stopService(serviceIntent);
        }
        unregisterReceiver();
        screenSwitchUtils.stop();
    }

    private void showDowmData() {

        final DownDialog downDialog = new DownDialog(this, 1) {
            @Override
            public String getResourceId() {
                return String.valueOf(resourceId);
            }

            @Override
            public String getTitle() {
                return bean.getName();
            }

            @Override
            public String getLogo() {
                return bean.getShowImage();
            }
        };
        downDialog.setListViews(seriesAdapter.getData());
        downDialog.show(videoView.isLandscape());
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            String eventName = "";
            switch (v.getId()) {
//                case R.id.img_xiaolian:
//                    int visibility = ll_qq_biaoqing.getVisibility();
//                    if (visibility == View.GONE) {
////                        JianpanTools.HideKeyboard(mInput);
//                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING); //  不改变布局，隐藏键盘，emojiView弹出
//                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(mInput.getApplicationWindowToken(), 0);
//                        ll_qq_biaoqing.setVisibility(View.VISIBLE);
//                    } else {
//                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(mInput.getApplicationWindowToken(), 0);
//                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//                        ll_qq_biaoqing.setVisibility(View.GONE);
//                    }
//                    break;
                case R.id.anim_dowm1:
                    eventName = "弹出下载界面";
                    showDowmData();
                    break;
                case R.id.anim_share3:
                    eventName = "弹出分享界面";
                    showShare();
                    break;
                case R.id.danmu_kaiguan:
                    if (isOpen) {
                        danmuControler.show();
                        danmu_kaiguan.setImageResource(R.mipmap.danmu_open);
                        isOpen = false;
                    } else {
                        danmuControler.hide();
                        danmu_kaiguan.setImageResource(R.mipmap.danmu_close);
                        isOpen = true;

                    }
                    break;
                case R.id.anim_collect2:
                    eventName = "收藏/取消收藏 操作";
                    if (!UpdataUserInfo.isLogIn(AnimDetailActivity.this,true,null)) {
                        isCollectBox.setChecked(!isCollectBox.isChecked());
                        Toast.makeText(getApplicationContext(), "未登录，请先登录", Toast.LENGTH_SHORT).show();
                        return;
                    }else
                    { collction((CheckBox) v);}
                    break;
                case R.id.anim_send_comment:
                    eventName = "发布评论操作";

                    if (!UpdataUserInfo.isLogIn(AnimDetailActivity.this,true,null)) {
//           未登录
                        Toast.makeText(getApplicationContext(), "未登录，请先登录", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String text = mInput.getText().toString();
                    if (text.equals("")) {
                        Toast.makeText(getApplicationContext(), "评论内容不能为空!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    CommonUtil.hideKeyboard(getApplicationContext(), mInput);
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("resourceId", resourceId);
                        obj.put("sendId", Constants.CURRENT_USER.getData().getAccount().getId());
//                      obj.put("sendId", 1);
                        obj.put("content", mInput.getText().toString());
                        obj.put("taskType", 1);
                        obj.put("taskValue", 3);

                        httpPost(Constants.URL_COMMENTS, obj, new HttpCallback() {
                            @Override
                            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                                super.doAuthSuccess(result, obj);
                                try {
                                    if (obj.getString("code").equals("1000")) {
                                        isLoadMore=true;
                                        Toast.makeText(getApplicationContext(), "评论成功", Toast.LENGTH_SHORT
                                        ).show();
                                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                                        mEmojiView.setVisibility(View.GONE);
                                        mInput.setText("");
                                        pullToLoadView.initLoad();
                                        CommentData();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                                super.doAuthFailure(result, obj);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            Tracker.getInstance(getApplicationContext()).trackMethodInvoke(TITLE, eventName);

        }
    };



    private void collction(final CheckBox v) {



        httpGet(Constants.ULR_COLLECT + Constants.CURRENT_USER.getData().getAccount().getId() + "&resourceId=" + resourceId, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    if (obj.getString("code").equals("1000")) {
                        bean.setIsCollect(1);
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT
                        ).show();
                        isCollectBox.setChecked(bean.getIsCollect() == 1);
                        videoView.more.setChecked(bean.getIsCollect() == 1);
                    }
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

                    isCollectBox.setChecked(bean.getIsCollect() == 1);
                    videoView.more.setChecked(bean.getIsCollect() == 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showShare() {
        dialog = new ShareDialog(this, bean.getName(), bean.getDesp(), Constants.SHARE_ANIM_URL + resourceId, bean.getShowImage());

        dialog.show();
//        OnkeyShare share = new OnkeyShare(this);
//        share.setPlatform();
//        share.setTitle(bean.getName());
//        share.setDescription(bean.getDesp());
//        share.setTargUrl(Constants.SHARE_URL + resourceId);
//        share.setImageUrl(bean.getShowImage());
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


    @Override
    public void onBackPressed() {
        if (mEmojiView.getVisibility()==View.VISIBLE){
            mEmojiView.setVisibility(View.GONE);
            return;
        }

        int i = getResources().getConfiguration().orientation;
        if (i == Configuration.ORIENTATION_LANDSCAPE) {
            setLandPort();
        } else {
            finish();
        }
    }

    public void setLandPort() {
        int i = getResources().getConfiguration().orientation;
        if (i == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if (i == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        screenSwitchUtils.start(AnimDetailActivity.this);
        videoView.orientation(this);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
//            videoView.orientationLanScape();
            WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(attrs);
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            pullToLoadView.setVisibility(View.GONE);
            videoView.setFullScreen();
            ll_bottom.setVisibility(View.GONE);
        }
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            pullToLoadView.setVisibility(View.VISIBLE);
            ll_bottom.setVisibility(View.VISIBLE);
            videoView.setNormalScreen();
        }


    }


    /**
     * 获取作品详情
     */
    private void getCarToon() {
        String url = Constants.URL_CARTTON_DETAIL + resourceId;
        if (Constants.CURRENT_USER != null) {
            url += ("?userId=" + Constants.CURRENT_USER.getData().getAccount().getId());
        }
        httpGet(url, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    bean = new Gson().fromJson(obj.getString("data"), ObjectBean.class);
                    setViews(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setViews(ObjectBean bean) {
        mCarDesp.setText(bean.getDesp() + "");
        isCollectBox.setChecked(bean.getIsCollect() == 1);
        videoView.more.setChecked(bean.getIsCollect() == 1);
        mTitleDesp.setText(bean.getName());
//        (bean.getType() == 1 ? "更新至" : "全") + bean.getChapter() + "集"
        mChapterDesp.setText((bean.getType() == 1 ? "更新至" : "全") + bean.getChapter() + "集");
        videoView.setmVideoName(bean.getName());

        getChapter();
    }

    /**
     * 获取作品章节
     */
    private void getChapter() {
        httpGet(Constants.URL_CHAPTER_LIST + resourceId + "&page=1&size=10000", new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    mChapterList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<ChapterBean>>() {
                    }.getType());
                    setSeriesView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean isTip = false;//是否已经提示过
    public void startPlay(final ChapterBean bean) {

        String network = HttpUtil.getNetworkType(context);
        if (!Preferences.getInstance(context).getPreference("netPlay", false)) {
            if (!network.equals("WIFI") && !isTip) {
                isTip = true;
                DialogUtils.Builder builder = new DialogUtils.Builder(this);
                builder.setMessage("是否允许在2G/3G/4G网络下播放").setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        start(bean);
                    }
                }).setNegativeButton("不允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            } else {
                start(bean);
            }
        } else {
            start(bean);
        }
        getBarrageResource(bean.getId());
    }

    private void getBarrageResource(String id) {
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("resourceId", resourceId);
//            obj.put("chapterId", bean.getId());
//            obj.put("page", 1);
//            obj.put("size", 100);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        httpGet(Constants.URL_GET_BARRAGE + "?resourceId=" + resourceId + "&chapterId=" + id, new HttpCallback() {
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
    
    public void start(ChapterBean bean) {
        if (bean != null) {
            mCurrintChapter = bean;
            startMyService(mCurrintChapter);
//            int time=saveSeacherHos(mCurrintChapter);
            ArrayList<ChapterBean> list = new ArrayList<ChapterBean>();
            for (ChapterBean bn : mChapterList) {
                bn.setCheck(bean.getId() == bn.getId() ? 3 : 0);
                list.add(bn);
            }

            seriesAdapter.setData(list);
            mChapterList = seriesAdapter.getData();
            mChapterView.setAdapter(seriesAdapter);
//            videoView.start(bean.getContentUrl());
            barrage_rescourceId = resourceId;
            barrage_charterId = bean.getId();
            if (Preferences.getInstance(getApplicationContext()).getPreference("ScreenFullPlay", false)) {
                if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                    setLandPort();
                }
            }
        }
    }
    public static class MyVideoReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if (action.equals("com.vodeo.play.notifition")){
                    int playtime=intent.getExtras().getInt("playtime");
                    String url=intent.getExtras().getString("url");
                if (playtime==0||playtime==-1){
                    videoView.start(url);
                }else {
                    notifiDialog(url,playtime);
                }

            }
        }
    }
    private static void notifiDialog(final String playUrl,final int times){
        int[] playTime = getMinuteAndSecond(times);

        String strTime=String.format("%02d:%02d", playTime[0], playTime[1]);
        new DialogUtils.Builder(context)
                .setMessage("您上次看到了"+strTime+"是否继续观看")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        videoView.start(playUrl,times);
                    }
                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                videoView.start(playUrl);
            }
        }).create().show();
    }
    private static int[] getMinuteAndSecond(int mils) {
        mils /= 1000;
        int[] time = new int[2];
        time[0] = mils / 60;
        time[1] = mils % 60;
        return time;
    }
    private void startMyService(ChapterBean bean){
        serviceIntent=new Intent(this, DBService.class);
        serviceIntent.putExtra("type","insert");
        serviceIntent.putExtra("insert",bean);
        startService(serviceIntent);
    }


//    private int saveSeacherHos(ChapterBean bean) {
//        try {
//            String rescoureId=bean.getId();
//            AnimLookCookieBean animLookCookieBean=new AnimLookCookieBean();
//            animLookCookieBean.setRescoureid(rescoureId);
//            animLookCookieBean.setAnimname(bean.getTitle());
//            int index=bean.getContentUrl().indexOf("?");
//            String playUrl=bean.getContentUrl().substring(0,index);
//            animLookCookieBean.setAnimUrl(playUrl);
//            int type=getAllLookHistory(rescoureId,playUrl);
//            if (type==-1){
////                WoDbUtils.initialize(getApplicationContext()).delete(AnimLookCookieBean.class,WhereBuilder.b("animUrl"," = ",playUrl));
//                WoDbUtils.initialize(getApplicationContext()).save(animLookCookieBean);
//            }else {
//                return type;
//            }
//
////         List<AnimLookCookieBean> beanList= WoDbUtils.initialize(getApplicationContext()).findAll(Selector.from(AnimLookCookieBean.class).where("animUrl", "=", playUrl));
//         } catch (DbException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//    private int getAllLookHistory(String rescoureId,String url) {
//        try {
//            List<AnimLookCookieBean> beanList= WoDbUtils.initialize(getApplicationContext()).
//                    findAll(Selector.from(AnimLookCookieBean.class).where("animUrl", "=", url).and("rescoureid","=",rescoureId));
//            if (beanList.size()!=0){
//                return beanList.get(0).getLookTime();
//            }
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
//        return -1;
//
//    }



    @Override
    public void deleteBiaoQing() {
        biaoqingtools.delete(mInput);

    }

    @Override
    public void insertBiaoQing(SpannableString character) {
        biaoqingtools.insert(character, mInput);
    }

    @Override
    public void refrensh(String content,int color,int position) {
        super.refrensh(content,color,position);
        if (position==1){
            danmuControler.setDanmakuView(mDanmakuView_top);
        }else if (position==2){
            danmuControler.setDanmakuView(mDanmakuView_middle);
        }else if (position==3){
            danmuControler.setDanmakuView(mDanmakuView_bottom);
        }else {
            danmuControler.setDanmakuView(mDanmakuView_top);
        }
        danmuControler.addBuilt(content,color);

//        getBarrageResource(barrage_charterId);

    }

    @Override
    public void setTime(String playUrl,int time,int totalTime) {
        try {
            AnimLookCookieBean animLookCookieBean1=new AnimLookCookieBean();
            int index=playUrl.indexOf("?");
            String insertUrl=playUrl.substring(0,index);
            if (time<totalTime){
                animLookCookieBean1.setTotalTime(totalTime);
                animLookCookieBean1.setLookTime(time);
                WoDbUtils.initialize(getApplicationContext()).update(animLookCookieBean1, WhereBuilder.b("animUrl","=",insertUrl),"totalTime","lookTime");
            }else if (time==totalTime){
                animLookCookieBean1.setPlayState(1);
                WoDbUtils.initialize(getApplicationContext()).update(animLookCookieBean1, WhereBuilder.b("animUrl","=",insertUrl),"playState");
            }
            } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void networkChangeListener() {
        if (isTip || !videoView.isPlaying()) return;
        videoView.touchPlayOrPause();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否允许在2G/3G/4G网络下播放").setPositiveButton("允许", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                videoView.touchPlayOrPause();
            }
        }).setNegativeButton("不允许", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();

    }

    private  void registerReceiver(){
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        networkChangeReceive=new NetworkChangeReceive();
        networkChangeReceive.setNetworkChangeListener(this);
        this.registerReceiver(networkChangeReceive, filter);
    }

    private  void unregisterReceiver(){
        this.unregisterReceiver(myvideoReceiver);
        this.unregisterReceiver(networkChangeReceive);
    }
}