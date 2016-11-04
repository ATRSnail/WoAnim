package com.wodm.android.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.CommentAdapter;
import com.wodm.android.adapter.SeriesAdapter;
import com.wodm.android.bean.BarrageBean;
import com.wodm.android.bean.ChapterBean;
import com.wodm.android.bean.CommentBean;
import com.wodm.android.bean.ObjectBean;
import com.wodm.android.dialog.ChapterDialog;
import com.wodm.android.dialog.DownDialog;
import com.wodm.android.dialog.ShareDialog;
import com.wodm.android.receiver.NetworkChangeListener;
import com.wodm.android.receiver.NetworkChangeReceive;
import com.wodm.android.tools.BiaoqingTools;
import com.wodm.android.tools.DanmuControler;
import com.wodm.android.tools.JianpanTools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.utils.Preferences;
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
public class AnimDetailActivity extends AppActivity implements FaceRelativeLayout.BiaoQingClickListener,NetworkChangeListener {
    @ViewIn(R.id.common_videoView)
    private CommonVideoView videoView;
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
    @ViewIn(R.id.danmaku_view)
    private master.flame.danmaku.ui.widget.DanmakuView mDanmakuView;
    @ViewIn(R.id.ll_bottom)
    private LinearLayout ll_bottom;
    private DanmuControler danmuControler;
    @ViewIn(R.id.header)
    private CircularImage header;
    private ImageView danmu_kaiguan;
    private boolean isOpen = true;
    private Dialog dialog = null;
    private boolean isLandscape;
    private boolean isClickFullScreenButton;
    private boolean isSennor=true;

    private NetworkChangeReceive networkChangeReceive;

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
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeaderViews();
        biaoqingtools = BiaoqingTools.getInstance();
        resourceId = getIntent().getIntExtra("resourceId", -1);
        DividerLine line = new DividerLine();
        line.setSize(getResources().getDimensionPixelSize(R.dimen.px_1));
        line.setColor(Color.rgb(0xD8, 0xD8, 0xD8));

        mCommentView = (EditText) findViewById(R.id.comment_text);
        mCommentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (JianpanTools.KeyBoard(mCommentView)) {
                    ll_qq_biaoqing.setVisibility(View.GONE);
                }
            }
        });
        if (Constants.CURRENT_USER != null) {
            new AsyncImageLoader(this, R.mipmap.default_header, R.mipmap.default_header).display(header, Constants.CURRENT_USER.getData().getAccount().getPortrait());
        }
        findViewById(R.id.anim_send_comment).setOnClickListener(onClickListener);
        img_xiaolian = (CircularImage) findViewById(R.id.img_xiaolian);
        img_xiaolian.setOnClickListener(onClickListener);
        //增加表情
        ll_qq_biaoqing = (FaceRelativeLayout) findViewById(R.id.ll_qq_biaoqing);
        ll_qq_biaoqing.setOnCorpusSelectedListener(this);
//        findViewById(R.id.anim_send_comment).setEnabled(Constants.CURRENT_USER != null);
        registerReceiver();
        pullToLoadView.getRecyclerView().addItemDecoration(line);
        pullToLoadView.setLoadingColor(R.color.colorPrimary);
        commentBeanList = new ArrayList<>();
        pullToLoadView.setPullCallback(new PullCallbackImpl(pullToLoadView) {
            @Override
            protected void requestData(final int pager, final boolean b) {
                //解决分页重复请求只能请求到同一个数据BUG
                if (commentBeanList.size() % 10 == 0) {
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
    protected void onPause() {
        super.onPause();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        JianpanTools.HideKeyboard(mCommentView);
        return super.onTouchEvent(event);
    }

    private void initDanMu(final ArrayList<BarrageBean> barrageBeanList) {
        //弹幕
//        List<IDanmakuItem> list = initItems(commentbeanList);
//        Collections.shuffle(list);
//        mDanmakuView.addItem(list, true);
//        mDanmakuView.show();
        danmuControler = new DanmuControler(this, mDanmakuView);
        danmuControler.addData(barrageBeanList);

    }

    private List<IDanmakuItem> initItems(List<CommentBean> commentbeanList) {
        List<IDanmakuItem> list = new ArrayList<>();
        for (CommentBean iDanmakuItem : commentbeanList) {
            SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(this, iDanmakuItem.getContent());
            IDanmakuItem item = new DanmakuItem(this, spannableString, mDanmakuView.getWidth(), R.color.colorAccent, R.color.colorAccent, 0, 1.5f);
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
        unregisterReceiver();
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
                case R.id.img_xiaolian:
                    int visibility = ll_qq_biaoqing.getVisibility();
                    if (visibility == View.GONE) {
                        JianpanTools.HideKeyboard(mCommentView);
                        ll_qq_biaoqing.setVisibility(View.VISIBLE);
                    } else {
                        ll_qq_biaoqing.setVisibility(View.GONE);
                    }
                    break;
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
                        danmuControler.hide();
                        danmu_kaiguan.setImageResource(R.mipmap.danmu_open);
                        isOpen = false;
                    } else {
                        danmuControler.show();
                        danmu_kaiguan.setImageResource(R.mipmap.danmu_close);
                        isOpen = true;
                    }
                    break;
                case R.id.anim_collect2:
                    eventName = "收藏/取消收藏 操作";
                    if (Constants.CURRENT_USER == null) {
                        isCollectBox.setChecked(!isCollectBox.isChecked());
                        Toast.makeText(getApplicationContext(), "未登录，请先登录", Toast.LENGTH_SHORT).show();
                        return;
                    }else
                    { collction((CheckBox) v);}
                    break;
                case R.id.anim_send_comment:
                    eventName = "发布评论操作";

                    if (Constants.CURRENT_USER == null) {
//           未登录
                        Toast.makeText(getApplicationContext(), "未登录，请先登录", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String text = mCommentView.getText().toString();
                    if (text.equals("")) {
                        Toast.makeText(getApplicationContext(), "评论内容不能为空!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    CommonUtil.hideKeyboard(getApplicationContext(), mCommentView);
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("resourceId", resourceId);
                        obj.put("sendId", Constants.CURRENT_USER.getData().getAccount().getId());
//                      obj.put("sendId", 1);
                        obj.put("content", mCommentView.getText().toString());
                        obj.put("taskType", 1);
                        obj.put("taskValue", 3);

                        httpPost(Constants.URL_COMMENTS, obj, new HttpCallback() {
                            @Override
                            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                                super.doAuthSuccess(result, obj);
                                try {
                                    if (obj.getString("code").equals("1000")) {
                                        Toast.makeText(getApplicationContext(), "评论成功", Toast.LENGTH_SHORT
                                        ).show();
                                        ll_qq_biaoqing.setVisibility(View.GONE);
                                        mCommentView.setText("");
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
            videoView.orientationPORTRAIT();
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

        String network = HttpUtil.getNetworkType(this.getApplicationContext());
        if (!Preferences.getInstance(getApplicationContext()).getPreference("netPlay", false)) {
            if (!network.equals("WIFI") && !isTip) {
                isTip = true;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
            ArrayList<ChapterBean> list = new ArrayList<ChapterBean>();
            for (ChapterBean bn : mChapterList) {
                bn.setCheck(bean.getId() == bn.getId() ? 3 : 0);
                list.add(bn);
            }
            seriesAdapter.setData(list);
            mChapterList = seriesAdapter.getData();
            mChapterView.setAdapter(seriesAdapter);
            videoView.start(bean.getContentUrl());
            barrage_rescourceId = resourceId;
            barrage_charterId = bean.getId();
            if (Preferences.getInstance(getApplicationContext()).getPreference("ScreenFullPlay", false)) {
                if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
                    setLandPort();
                }
            }
        }
    }


    @Override
    public void deleteBiaoQing() {
        biaoqingtools.delete(mCommentView);

    }

    @Override
    public void insertBiaoQing(SpannableString character) {
        biaoqingtools.insert(character, mCommentView);
    }

    @Override
    public void refrensh(String content) {
        super.refrensh(content);
        danmuControler.addBuilt(content);
//        getBarrageResource(barrage_charterId);

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
        this.unregisterReceiver(networkChangeReceive);
    }

//    private Handler rotateHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 10001:
//                    if ((msg.arg1 > 45 && msg.arg1 <= 135) || (msg.arg1 > 225 && msg.arg1 <= 315)) {
//                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
//                    } else {
//                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                        }
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//    }
//
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//
//        float[] values = event.values;
//        int orientation = 0;
//        float X = -values[SensorManager.DATA_X];
//        float Y = -values[SensorManager.DATA_Y];
//        float Z = -values[SensorManager.DATA_Z];
//        float magnitude = X * X + Y * Y;
//        // Don't trust the angle if the magnitude is small compared to the y
//        // value
//        if (magnitude * 4 >= Z * Z) {
//            float OneEightyOverPi = 57.29577957855f;
//            float angle = (float) Math.atan2(-Y, X) * OneEightyOverPi;
//            orientation = 90 - (int) Math.round(angle);
//            // normalize to 0 - 359 range
//            if (orientation >= 360) {
//                orientation -= 360;
//            }
//            if (orientation < 0) {
//                orientation += 360;
//            }
//        }
//
//        Log.e("","-------------------------"+isClickFullScreenButton);
//        Log.e("","*************************"+isSennor);
//
//        if (isClickFullScreenButton) {
//            // 竖屏
//
//            if (isLandscape
//                    && (((orientation > 315 && orientation <= 360) || (orientation >= 0 && orientation <= 45)) || (orientation > 135 && orientation <= 225))) {
//                isLandscape = false;
//                isClickFullScreenButton = false;
//                isSennor = true;
//            }
//
//            // 横屏
//            if (!isLandscape
//                    && ((orientation > 45 && orientation <= 135) || (orientation > 225 && orientation <= 315))) {
//                isLandscape = true;
//                isClickFullScreenButton = false;
//                isSennor = true;
//            }
//        }
//        if (!isSennor) {// 判断是否要进行中断信息传递
//            return;
//        }
//        if (rotateHandler != null) {//发送消息
//            rotateHandler.obtainMessage(10001, orientation, 0).sendToTarget();
//        }
//    }
//
//    /** 点击屏幕切换按钮的时候 同时调用该方法 ： 中断Handler信息传递 */
//    public void setIsSennor() {
//        isSennor = false;
//    }
//
//    /** 点击屏幕切换按钮的时候 同时调用该方法 ： 确认此时屏幕的方向 */
//    public void setIsLandscape(boolean bool) {
//        isLandscape = bool;
//    }
//
//    /** 点击屏幕切换按钮的时候 同时调用该方法 ：设置按钮是否已被点击 */
//    public void setButtonFullScreenClicked() {
//        isClickFullScreenButton = true;
//    }

}