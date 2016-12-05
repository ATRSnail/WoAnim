package com.wodm.android.ui.newview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.wodm.R;
import com.wodm.android.tools.BiaoqingTools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.biaoqing.FaceRelativeLayout;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

@Layout(R.layout.activity_send_msg)
public class SendMsgActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter,
        View.OnClickListener {

    @ViewIn(R.id.ed_comment)
    EditText  mEditText;;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initDatas();
    }

    private void initDatas() {
       set_topbar.setOnTopbarClickListenter(this);
    }

    private void initViews() {
        biaoqingtools = BiaoqingTools.getInstance();
        mInputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

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
     * 编辑框获取焦点，并显示软件盘
     */
    private void showSoftInput() {
        mEditText.requestFocus();
        mEditText.post(new Runnable() {
            @Override
            public void run() {
                mInputManager.showSoftInput(mEditText, 0);
            }
        });
    }

    /**
     * 隐藏表情布局
     *
     */
    private void hideEmotionLayout() {
        if (mEmojiView.isShown()) {
            mEmojiView.setVisibility(View.GONE);
        }
    }


}
