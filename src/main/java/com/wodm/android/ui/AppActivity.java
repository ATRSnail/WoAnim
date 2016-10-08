package com.wodm.android.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.CartoonApplication;
import com.wodm.android.Constants;
import com.wodm.android.ui.braageview.BulletSendDialog;
import com.wodm.android.utils.DeviceUtils;
import com.wodm.android.utils.UpdataUserInfo;
import com.wodm.android.view.CommonVideoView;

import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.track.TrackApplication;
import org.eteclab.track.Tracker;
import org.eteclab.ui.activity.MaterialActivity;
import org.eteclab.ui.widget.SlideBackUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class AppActivity extends MaterialActivity implements CommonVideoView.SendBulletListener,BulletSendDialog.SendBarrage {

    @ViewIn(R.id.toolbar)
    protected Toolbar mToolbar;
    @ViewIn(R.id.toolbar_title)
    protected TextView mToolbarTitle;
    @ViewIn(R.id.tooltitle_right)
    protected Button mTitleRight;
    @ViewIn(R.id.check_button)
    protected LinearLayout mCheckButton;
    protected int barrage_rescourceId;
    protected String  barrage_charterId;

    protected SlideBackUtil mSlideBackUtil;

    public AppActivity() {
        mSlideBackUtil = new SlideBackUtil(false, false, false, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSlideBackUtil.onCreate(this);
        super.onCreate(savedInstanceState);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        setTitle("");
    }

    public void setCustomTitle(String title) {
        mToolbarTitle.setText(title);
    }

    public void setTitleRight(String title) {
        mTitleRight.setText(title);
    }

    public void showOrGoneCheckButton(int vis) {
        mCheckButton.setVisibility(vis);
        mTitleRight.setVisibility(View.GONE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Tracker.getInstance(this).onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Tracker.getInstance(this).onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void httpGet(String url, final HttpCallback callback) {

        try {
            TrackApplication. REQUEST_HEADER.put("Content-Type", "application/json");
            HttpUtil.httpGet(this, url, TrackApplication.REQUEST_HEADER, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void httpDownload(String url, String savePath, final HttpCallback callback) {
        HttpUtil.httpDownload(this, url, savePath, callback);
    }

    public void httpUpload(String url, JSONObject body, File file, final HttpCallback callback) {
        List<File> files = new ArrayList<>();
        files.add(file);
        httpUpload(url, body, files, callback);
    }

    public void httpUpload(String url, JSONObject body, List<File> fileList, final HttpCallback callback) {
        IdentityHashMap<String, File> files = new IdentityHashMap<>();
        for (File file : fileList) {
            files.put(new String("file"), file);
        }
        HttpUtil.httpUpload(this, url, body, files, callback);
    }

    public void httpPost(String url, JSONObject body, final HttpCallback callback) {
        try {
            TrackApplication. REQUEST_HEADER.put("Content-Type", "application/json");
            HttpUtil.httpPost(this, url, body, CartoonApplication.REQUEST_HEADER, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public View getContentView() {
        return mContentView;
    }


    @Override
    public void sendBullet() {
        showEditDialog( );
    }


    public void showEditDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment bulletDialog = BulletSendDialog.newInstance(this);
        bulletDialog.show(ft,"dialog");
    }

    @Override
    public void addBullet(String content) {
        if(!UpdataUserInfo.isLogIn(this,true)){
            Toast.makeText(this,"请先登录",Toast.LENGTH_SHORT).show();
            return;
        }
        long sendId= Constants.CURRENT_USER.getUserId();
//       JSONObject jsonObject=new JSONObject();
//        try {
//            jsonObject.put("resourceId",barrage_rescourceId);
//            jsonObject.put("charterId",barrage_charterId);
//            jsonObject.put("sendId",sendId);
//            jsonObject.put("content",content);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//       httpPost(Constants.URL_GET_ADD_BARRAGE,jsonObject, new HttpCallback() {
//            @Override
//            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
//                super.doAuthSuccess(result, obj);
//                try {
//                    if (obj.getString("code").equals("1000")) {
//                        Toast.makeText(getApplicationContext(), "评论成功", Toast.LENGTH_SHORT).show();
//                    }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
//                    super.doAuthFailure(result, obj);
//                }
//            });
        JSONObject obj = new JSONObject();
        try {
            obj.put("resourceId", barrage_rescourceId);
            obj.put("chapterId", barrage_charterId);
            obj.put("sendId", Constants.CURRENT_USER.getUserId());
//                      obj.put("sendId", 1);
            obj.put("content", content);
            httpPost(Constants.URL_GET_ADD_BARRAGE, obj, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthSuccess(result, obj);
                    try {
                        if (obj.getString("code").equals("1000")) {
                            Toast.makeText(getApplicationContext(), "弹幕添加成功", Toast.LENGTH_SHORT
                            ).show();
                            refrensh();
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
    public void refrensh(){

    }

    public void showFial() {
        View view = getLayoutInflater().inflate(R.layout.layout_popupwindow, null);
        TextView mPopText = (TextView) view.findViewById(R.id.popup_text);
        TextView mPopTextTitle = (TextView) view.findViewById(R.id.popup_text_title);
        Button mPopBtnOne = (Button) view.findViewById(R.id.popup_btn_one);
        Button mPopBtnTwo = (Button) view.findViewById(R.id.popup_btn_two);
        mPopBtnTwo.setVisibility(View.GONE);
        mPopTextTitle.setText("提示");
        mPopText.setText("请输入正确手机号!");
        mPopBtnOne.setText(getResources().getText(R.string.user_sure));
        // mPopBtnTwo.setText(getResources().getText(R.string.user_fail));
        final Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setContentView(view);
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });
        mPopBtnOne.setTextColor(getResources().getColor(R.color.colorPrimary));
        mPopBtnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
            }
        });
        /*mPopBtnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
            }
        });*/
        Window window = builder.getWindow();
        // window.setWindowAnimations(R.style.dialogAnim);
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        lp.width = (int) (DeviceUtils.getScreenWH(this)[0] * 0.9); // 宽度
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        builder.getWindow().setGravity(Gravity.CENTER);

        builder.show();
        builder.setCanceledOnTouchOutside(false);
    }


}