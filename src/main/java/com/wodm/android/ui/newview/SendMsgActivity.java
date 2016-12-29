package com.wodm.android.ui.newview;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.NewCommentAdapter;
import com.wodm.android.adapter.newadapter.PingFenAdapter;
import com.wodm.android.bean.AtWoBean;
import com.wodm.android.bean.NewCommentBean;
import com.wodm.android.tools.BiaoqingTools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.home.AnimDetailActivity;
import com.wodm.android.utils.UpdataUserInfo;
import com.wodm.android.view.biaoqing.FaceRelativeLayout;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.base.utils.CommonUtil;
import org.eteclab.track.Tracker;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.activity_send_msg)
public class SendMsgActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter,
        View.OnClickListener {
    private final String TITLE = "动画详情";
    public static final String ATWOBEAN = "atwobean";
    @ViewIn(R.id.ed_comment)
    EditText mEditText;
    @ViewIn(R.id.set_topbar)
    AtyTopLayout set_topbar;
    @ViewIn(R.id.tv_num)
    TextView numTv;
    @ViewIn(R.id.faceRelativeLayout)
    FaceRelativeLayout ll_qq_biaoqing;
    @ViewIn(R.id.img_emoji)
    ImageView emojiBtn;
    @ViewIn(R.id.view_emoji)
    View mEmojiView;
    @ViewIn(R.id.fenshu)
    TextView textView;
    @ViewIn(R.id.pingfen)
    GridView gridView;
    @ViewIn(R.id.ll_pingfen)
    LinearLayout ll_pingfen;
    private BiaoqingTools biaoqingtools;
    private InputMethodManager mInputManager;//软键盘管理类
    private AtWoBean.DataBean atWoBean;
     int flag=1;//判断发送（1）|回复（2）评论的标志
    private String url="";//评论的链接
    public static  boolean update=false;//评论刷新的标志
    int resourceId=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        initDatas();
        initViews();

    }


    private void initDatas() {
        gridView.setAdapter(new PingFenAdapter(SendMsgActivity.this,textView));
        if (getIntent() != null)
        {atWoBean = (AtWoBean.DataBean) getIntent().getSerializableExtra(ATWOBEAN);
            flag=getIntent().getIntExtra("flag",1);
            resourceId=getIntent().getIntExtra("resourceId",-1);
        }
    }

    private void initViews() {
        if (flag==2){
            ll_pingfen.setVisibility(View.GONE);
        }
        biaoqingtools = BiaoqingTools.getInstance();
        mInputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        set_topbar.setOnTopbarClickListenter(this);
        emojiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmojiView.isShown()) {
                    hideEmotionLayout();//隐藏表情布局，显示软件盘
                } else {
                    showEmotionLayout();//两者都没显示，直接显示表情布局
                }
            }
        });
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                numTv.setText("剩余" + (150 - s.length()) + "字");
            }
        });

        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && mEmojiView.isShown()) {
                    hideEmotionLayout();//隐藏表情布局，显示软件盘
                    //软件盘显示后，释放内容高度
                }
                return false;
            }
        });

        ll_qq_biaoqing.setOnCorpusSelectedListener(new FaceRelativeLayout.BiaoQingClickListener() {
            @Override
            public void deleteBiaoQing() {
                biaoqingtools.delete(mEditText);
            }

            @Override
            public void insertBiaoQing(SpannableString character) {
                biaoqingtools.insert(character, mEditText);
            }
        });
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {
        String  eventName="";
        if (TextUtils.isEmpty(mEditText.getText().toString().trim())){
            Toast.makeText(this,"评论不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (flag==2){
            eventName = "回复评论操作";
            if (atWoBean == null) return;
            JSONObject obj = new JSONObject();
            try {
                obj.put("resourceId", atWoBean.getResourceId());
                obj.put("sendId", Constants.CURRENT_USER.getData().getAccount().getId());
                obj.put("receiveId", atWoBean.getSendId());
                obj.put("commentId", atWoBean.getCommentId());
                obj.put("content", mEditText.getText().toString());
                obj.put("type", 1);
                httpPost(Constants.REPLY, obj, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        Toast.makeText(SendMsgActivity.this,"回复成功", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthFailure(result, obj);
                        Toast.makeText(SendMsgActivity.this,"回复失败", Toast.LENGTH_SHORT).show();

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
             eventName = "发布评论操作";
            String text = mEditText.getText().toString();
            if (text.equals("")) {
                Toast.makeText(getApplicationContext(), "评论内容不能为空!", Toast.LENGTH_SHORT).show();
                return;
            }
            CommonUtil.hideKeyboard(getApplicationContext(), mEditText);
            JSONObject obj = new JSONObject();
            try {
                obj.put("resourceId", resourceId);
                obj.put("sendId", Constants.CURRENT_USER.getData().getAccount().getId());
                obj.put("content", text);
                obj.put("taskType", 1);
                obj.put("taskValue", 3);
                /**新加的---表示的是评论的模块 1:表示动漫画的评论 2:表示的是新闻资讯的评论3:表示的是帖子的评论*/
                obj.put("type", 1);
                httpPost(Constants.SAVEComment, obj, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        try {
                            if (obj.getString("code").equals("1000")) {
                                Toast.makeText(getApplicationContext(), "评论成功", Toast.LENGTH_SHORT
                                ).show();
                                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                                mEmojiView.setVisibility(View.GONE);
                                mEditText.setText("");
                                update=true;
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
        }
        Tracker.getInstance(getApplicationContext()).trackMethodInvoke(TITLE, eventName);
    }



    private void showEmotionLayout() {
        hideSoftInput();
        mEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayout.LayoutParams) mEmojiView.getLayoutParams()).height = 400;
                mEmojiView.setVisibility(View.VISIBLE);
            }
        }, 200L);

    }

    /**
     * 隐藏软件盘
     */
    private void hideSoftInput() {
        mInputManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 隐藏表情布局
     */
    private void hideEmotionLayout() {
        if (mEmojiView.isShown()) {
            mEmojiView.setVisibility(View.GONE);
        }
    }


    @Override
    public void onBackPressed() {
        if (mEmojiView.isShown()) {
            hideEmotionLayout();
            return;
        }
        super.onBackPressed();
    }


}
