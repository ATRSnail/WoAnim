package com.wodm.android.ui.newview;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.AtWoBean;
import com.wodm.android.tools.BiaoqingTools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.biaoqing.FaceRelativeLayout;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.json.JSONException;
import org.json.JSONObject;

@Layout(R.layout.activity_send_msg)
public class SendMsgActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter,
        View.OnClickListener {

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
    private BiaoqingTools biaoqingtools;
    private InputMethodManager mInputManager;//软键盘管理类
    private AtWoBean.DataBean atWoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();

    }

    private void initDatas() {
        if (getIntent() != null)
            atWoBean = (AtWoBean.DataBean) getIntent().getSerializableExtra(ATWOBEAN);
    }

    private void initViews() {
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
        if (atWoBean == null) return;
        if (TextUtils.isEmpty(mEditText.getText().toString().trim())){
            Toast.makeText(this,"评论不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject obj = new JSONObject();
        try {
            obj.put("resourceId", atWoBean.getResourceId()+"");
            obj.put("sendId", atWoBean.getSendId()+"");
            obj.put("receiveId", atWoBean.getResourceId()+"");
            obj.put("commentId", atWoBean.getCommentId()+"");
            obj.put("content", mEditText.getText().toString());
            obj.put("type", "1");
            httpPost(Constants.REPLY, obj, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthSuccess(result, obj);
                    Toast.makeText(SendMsgActivity.this,"发表成功", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthFailure(result, obj);
                    Toast.makeText(SendMsgActivity.this,"发表失败", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
