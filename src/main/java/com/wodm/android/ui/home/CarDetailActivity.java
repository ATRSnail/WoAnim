package com.wodm.android.ui.home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.CommentAdapter;
import com.wodm.android.adapter.SeriesAdapter;
import com.wodm.android.bean.ChapterBean;
import com.wodm.android.bean.CommentBean;
import com.wodm.android.bean.ObjectBean;
import com.wodm.android.dialog.DownDialog;
import com.wodm.android.dialog.ShareDialog;
import com.wodm.android.qq.KeyboardLayout;
import com.wodm.android.tools.BiaoqingTools;
import com.wodm.android.tools.JianpanTools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.utils.UpdataUserInfo;
import com.wodm.android.view.DividerLine;
import com.wodm.android.view.biaoqing.FaceRelativeLayout;

import org.eteclab.base.annotation.InflateView;
import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.base.utils.CommonUtil;
import org.eteclab.track.Tracker;
import org.eteclab.track.annotation.TrackClick;
import org.eteclab.ui.widget.CircularImage;
import org.eteclab.ui.widget.NoScrollGridView;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


@Layout(R.layout.activity_car_detail)
public class CarDetailActivity extends AppActivity implements FaceRelativeLayout.BiaoQingClickListener{
    @ViewIn(R.id.common_videoView)
    private ImageView videoView;
    private ArrayList<ChapterBean> mChapterList;
    private final String TITLE = "动画详情";

    private int resourceId = -1;
    private ObjectBean bean = null;

    @ViewIn(R.id.pull_list)
    private PullToLoadView pullToLoadView;
    @ViewIn(R.id.headView)
    private ScrollView headView;
    @InflateView(R.layout.layout_cartoon_detail)
    private View mHeaderView;
    private TextView dianji_num;
    @ViewIn(R.id.video_name)
    private TextView mCarTitle;
    @ViewIn(R.id.header)
    private CircularImage header;
    private NoScrollGridView mChapterView;

    private TextView mCarDesp;
    private TextView mChapterDesp;

    private TextView mTitleDesp;
    public  EditText mCommentView;
    private CheckBox isCollectBox;
    private FaceRelativeLayout ll_qq_biaoqing;
    private CircularImage img_xiaolian;
    private BiaoqingTools biaoqingtools;
    private ArrayList<CommentBean> commentBeanList;
    private ImageView danmu_kaiguan;
    private Dialog dialog=null;
    private boolean isLoadMore=false;
    private TextView anim_send_comment;
    //---------------------
    private KeyboardLayout mKeyboardLayout;
    private View mEmojiView;
    private CircularImage mEmojiBtn;
    private EditText mInput;
    private RelativeLayout ll_car_details;

    int mKeyboardHeight = 400; // 输入法默认高度为400
    private void initView(){

        // 起初的布局可自动调整大小
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mEmojiView.getVisibility()==View.VISIBLE){
            mEmojiView.setVisibility(View.GONE);
            return;
        }

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
        initView();
        biaoqingtools=BiaoqingTools.getInstance();
        resourceId = getIntent().getIntExtra("resourceId", -1);
        DividerLine line = new DividerLine();
        line.setSize(getResources().getDimensionPixelSize(R.dimen.px_1));
        line.setColor(Color.rgb(0xD8, 0xD8, 0xD8));
//        mCommentView = (EditText) findViewById(R.id.comment_text);
//        mCommentView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (JianpanTools.KeyBoard(mCommentView)){
//                    ll_qq_biaoqing.setVisibility(View.GONE);
//                }
//            }
//        });
        if (Constants.CURRENT_USER != null) {
            new AsyncImageLoader(this, R.mipmap.default_header, R.mipmap.default_header).display(header, Constants.CURRENT_USER.getData().getAccount().getPortrait());
        }
        anim_send_comment= (TextView) findViewById(R.id.anim_send_comment);
        anim_send_comment.setOnClickListener(onClickListener);
//        img_xiaolian = (CircularImage) findViewById(R.id.img_xiaolian);
//        img_xiaolian.setOnClickListener(onClickListener);
        //增加表情
        ll_qq_biaoqing = (FaceRelativeLayout) findViewById(R.id.ll_qq_biaoqing).findViewById(R.id.faceRelativeLayout);
        ll_qq_biaoqing.setOnCorpusSelectedListener(this);
        pullToLoadView.getRecyclerView().addItemDecoration(line);
        pullToLoadView.setLoadingColor(R.color.colorPrimary);
        commentBeanList=new ArrayList<>();

        pullToLoadView.setPullCallback(new PullCallbackImpl(pullToLoadView) {
            @Override
            protected void requestData(final int pager, final boolean b) {
                if (commentBeanList.size()%10==0||isLoadMore){
                    String url;
                    if (Constants.CURRENT_USER==null){
                        url=Constants.CommentList + resourceId + "&page=" + pager+"&type="+1;
                    }else {
                        url=Constants.CommentList + resourceId + "&page=" + pager+"&type="+1+"&userId="+Constants.CURRENT_USER.getData().getAccount().getId();
                    }
//                    httpGet(Constants.URL_GET_COMMENTS + resourceId + "&page=" + pager, new HttpCallback() {
                      httpGet(url, new HttpCallback() {

                        @Override
                        public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                            super.doAuthSuccess(result, obj);
                            try {
                                ArrayList<CommentBean> beanList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<CommentBean>>() {
                                }.getType());
                                commentBeanList=beanList;
                                handleData(pager, beanList, CommentAdapter.class, b, mHeaderView);
                                headView.removeAllViews();
                                if (beanList!=null&&beanList.size()==0){
                                    //若无评论，添加上方简介等信息
                                    headView.addView(mHeaderView);
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
                }else {
                    isLoadMore=false;
                    pullToLoadView.setComplete();
                }

            }
        });
        httpGet(Constants.APP_UPDATERESOURCECOUNT+resourceId, new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }
        });
        httpGet(Constants.APP_GETATERESOURCECOUNT+resourceId, new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    JSONObject jsonObject=new JSONObject(obj.optString("data"));
                    int playcount=jsonObject.optInt("playCount");
                    dianji_num.setText("点击量:"+playcount+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }
        });

        if (Constants.CURRENT_USER != null) {
            String url = Constants.USER_ADD_WATCH_RECORD + "?userId=" + Constants.CURRENT_USER.getData().getAccount().getId() + "&resourceId=" + resourceId+"&taskType=1&taskValue=2";
            httpGet(url, new HttpCallback());
        }
        getCarToon();
    }

    SeriesAdapter seriesAdapter;

    //
    private void setSeriesView(final List<ChapterBean> mChapterList) {


//        ChapterBean bean = new ChapterBean();
//        bean.setId("更多");
//
//        List<ChapterBean> list = new ArrayList();
//        if (mChapterList.size() > 2) {
//            list = mChapterList.subList(0, 2);
//            list.add(bean);
//        } else {
//            list.addAll(mChapterList);
//        }
//        Toast.makeText(getApplicationContext(),""+list.size(),0).show();
        seriesAdapter = new SeriesAdapter(this, mChapterList, 8);

        mChapterView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (((TextView) view.findViewById(R.id.button)).getText().equals("更多")) {
                    seriesAdapter.setShowAll();
                } else {
                    startRead(position);
                }
            }
        });

        mChapterView.setAdapter(seriesAdapter);
        pullToLoadView.initLoad();
    }


    @TrackClick(value = R.id.exit_screen, location = TITLE, eventName = "退出界面")
    private void clickFinish(View view) {
        finish();
    }

    @TrackClick(value = R.id.full, location = TITLE, eventName = "跳转阅读漫画界面")
    private void clickFull(View view) {
        startRead(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }

    private void startRead(int index) {
        if (!(mChapterList != null && index < mChapterList.size())) {
            return;
        }
        ArrayList<ChapterBean> list = new ArrayList<ChapterBean>();
        ChapterBean bns = mChapterList.get(index);
        for (ChapterBean bn : mChapterList) {
            if (bns.getId() == bn.getId())
                bn.setCheck(3);
            else {
                bn.setCheck(0);
            }
            list.add(bn);
        }
        seriesAdapter.setData(list);
        mChapterView.setAdapter(seriesAdapter);
        mChapterList = (ArrayList<ChapterBean>) seriesAdapter.getData();
        startActivityForResult(new Intent(this, CartoonReadActivity.class).putExtra("ChapterList", mChapterList)
                .putExtra("bean", bean).putExtra("index", index).putExtra("commentList",commentBeanList),0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0&&resultCode==RESULT_OK){
            bean = (ObjectBean) data.getSerializableExtra("bean");
            if(bean!=null){
                if(data.getIntExtra("key",0)==1)
                {
//                  isCollectBox.setChecked(1==data.getIntExtra("bean",0));
                    isCollectBox.setChecked(bean.getIsCollect() == 1);
                }
              else   if(data.getIntExtra("key",0)==0)
                {
//                    isCollectBox.setChecked(1==data.getIntExtra("bean",0));}
                    isCollectBox.setChecked(bean.getIsCollect() == 1);
            }

    }

        }}

    private void showDowmData() {
        final DownDialog downDialog = new DownDialog(this, 2) {
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
        downDialog.setListViews(mChapterList);
        downDialog.show(false);
    }

    private void showShare() {
        dialog=new ShareDialog(this,bean.getName(),bean.getDesp(),Constants.SHARE_CARREAD_URL + resourceId,bean.getShowImage());
        dialog.show();
//        OnkeyShare share = new OnkeyShare(this);
//        share.setPlatform();
//        share.setTitle(bean.getName());
//        share.setDescription(bean.getDesp());
//        share.setImageUrl(bean.getShowImage());
//        share.setTargUrl(Constants.SHARE_URL + resourceId);
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
//        ShareSDK.initSDK(this);
//        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
//        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        oks.setTitle("我的分享测试标题");
//        oks.setText("我是分享文本标题");
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        // oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
//        // url仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://www.hao123.com");
//        // 启动分享GUI
//        oks.show(this);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        JianpanTools.HideKeyboard(mInput);
        return super.onTouchEvent(event);
    }
    /**
     * 获取作品详情
     */
    private void getCarToon() {
        Log.e("AA","******************");
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

    private void initHeaderViews() {
        if (mHeaderView==null){
            Log.e("AA","--------------空");
        }else {
            Log.e("AA","--------------非空");
        }

        mCommentView = (EditText) mHeaderView.findViewById(R.id.comment_text);
        mTitleDesp = (TextView) mHeaderView.findViewById(R.id.car_title);
        mCarDesp = (TextView) mHeaderView.findViewById(R.id.desc_op_tv);
        mChapterDesp = (TextView) mHeaderView.findViewById(R.id.chapter_desp);
        mChapterView = (NoScrollGridView) mHeaderView.findViewById(R.id.grid_new);
        dianji_num= (TextView) mHeaderView.findViewById(R.id.dianji_num);
        isCollectBox = (CheckBox) mHeaderView.findViewById(R.id.anim_collect2);
        danmu_kaiguan= (ImageView) mHeaderView.findViewById(R.id.danmu_kaiguan);
        danmu_kaiguan.setVisibility(View.GONE);
        mHeaderView.findViewById(R.id.anim_dowm1).setOnClickListener(onClickListener);
        mHeaderView.findViewById(R.id.anim_share3).setOnClickListener(onClickListener);
        isCollectBox.setOnClickListener(onClickListener);
//        mHeaderView.findViewById(R.id.send_comment).setOnClickListener(onClickListener);
//        mHeaderView.findViewById(R.id.send_comment).setEnabled(Constants.CURRENT_USER != null);
    }

    private void setViews(ObjectBean bean) {
        Log.e("AA","******************"+bean.getDesp());
        mCarDesp.setText(bean.getDesp() + "");
        mTitleDesp.setText(bean.getName());
        isCollectBox.setChecked(bean.getIsCollect() == 1);
        mChapterDesp.setText((bean.getType() == 1 ? "更新至" : "全") + bean.getChapter() + "集");
        mCarTitle.setText(bean.getName());
        new AsyncImageLoader(this, R.mipmap.loading, R.mipmap.loading).display(videoView, bean.getShowImage());
        getChapter();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            String eventName = "";
            switch (v.getId()) {
//                case R.id.img_xiaolian:
//                    int visibility=ll_qq_biaoqing.getVisibility();
//                    if (visibility==View.GONE){
//                        JianpanTools.HideKeyboard(mCommentView);
//                        ll_qq_biaoqing.setVisibility(View.VISIBLE);
//                    }else {
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
                case R.id.anim_collect2:
                    eventName = "收藏/取消收藏 操作";
                    if (!UpdataUserInfo.isLogIn(CarDetailActivity.this,true,null)) {
                        isCollectBox.setChecked(!isCollectBox.isChecked());
                        Toast.makeText(getApplicationContext(), "未登录，请先登录", Toast.LENGTH_SHORT).show();
                        return;
                    }
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
                                    CheckBox box = (CheckBox) v;
                                    box.setChecked(!box.isChecked());
                                }
                                isCollectBox.setChecked(bean.getIsCollect() == 1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    break;
                case R.id.anim_send_comment:
                    anim_send_comment.setEnabled(false);
                    eventName = "发布评论操作";
                    if (!UpdataUserInfo.isLogIn(CarDetailActivity.this,true,null)) {
//            未登录
                        Toast.makeText(getApplicationContext(), "未登录，请先登录", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String text=mInput.getText().toString();
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
                        /**新加的---表示的是评论的模块 1:表示动漫画的评论 2:表示的是新闻资讯的评论3:表示的是帖子的评论*/
                        obj.put("type", 1);
//                        httpPost(Constants.URL_COMMENTS, obj, new HttpCallback() {
                          httpPost(Constants.SAVEComment, obj, new HttpCallback() {
                            @Override
                            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                                super.doAuthSuccess(result, obj);
                                try {
                                    if (obj.getString("code").equals("1000")) {
                                        isLoadMore=true;
                                        anim_send_comment.setEnabled(true);
                                        Toast.makeText(getApplicationContext(), "评论成功", Toast.LENGTH_SHORT
                                        ).show();
                                        mInput.setText("");
                                        ll_qq_biaoqing.setVisibility(View.GONE);
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
                                anim_send_comment.setEnabled(true);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        anim_send_comment.setEnabled(true);
                    }
                    break;
            }
            Tracker.getInstance(getApplicationContext()).trackMethodInvoke(TITLE, eventName);
        }
    };



    private void CommentData(){
        String url;
        if (Constants.CURRENT_USER==null){
            url=Constants.CommentList + resourceId + "&page=" + 1+"&type="+1;
        }else {
            url=Constants.CommentList + resourceId + "&page=" + 1+"&type="+1+"&userId="+Constants.CURRENT_USER.getData().getAccount().getId();
        }
//        httpGet(Constants.URL_GET_COMMENTS + resourceId + "&page=" + 1, new HttpCallback() {
          httpGet(url, new HttpCallback() {
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
                    setSeriesView(mChapterList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void deleteBiaoQing() {

        biaoqingtools.delete(mInput);

    }

    @Override
    public void insertBiaoQing(SpannableString character) {
        biaoqingtools.insert(character,mInput);
    }
}
