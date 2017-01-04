package com.wodm.android.ui;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.http.ResponseInfo;
import com.umeng.analytics.MobclickAgent;
import com.wodm.R;
import com.wodm.android.CartoonApplication;
import com.wodm.android.Constants;
import com.wodm.android.bean.AnimLookCookieBean;
import com.wodm.android.db.WoDbUtils;
import com.wodm.android.ui.braageview.BulletSendDialog;
import com.wodm.android.utils.DialogUtils;
import com.wodm.android.utils.Preferences;
import com.wodm.android.utils.Untils;
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

public class AppActivity extends MaterialActivity implements CommonVideoView.SendBulletListener, BulletSendDialog.SendBarrage {

    @ViewIn(R.id.toolbar)
    protected Toolbar mToolbar;
    @ViewIn(R.id.toolbar_title)
    protected TextView mToolbarTitle;
    @ViewIn(R.id.tooltitle_right)
    protected Button mTitleRight;
    @ViewIn(R.id.check_button)
    protected LinearLayout mCheckButton;
    protected int barrage_rescourceId;
    protected String barrage_charterId;
    protected int  CartoonReadPosition=-1;
    protected SlideBackUtil mSlideBackUtil;
    private int ScreenWidth, ScreenHight;
    private DialogFragment bulletDialog;
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
        WindowManager wm = this.getWindowManager();
        ScreenWidth = wm.getDefaultDisplay().getWidth();
        ScreenHight = wm.getDefaultDisplay().getHeight();
        setTitle("");
    }



    public void setCustomTitle(String title) {
        mToolbarTitle.setText(title);
    }
    public void setCustomTitleColor(int customTitleColor) {
        //新加的修改字体的颜色
        mToolbarTitle.setTextColor(customTitleColor);
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
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(AppActivity.this);
        MobclickAgent.onPause(this);
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
            TrackApplication.REQUEST_HEADER.put("Content-Type", "application/json");
//            getHeaders(this);
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
            TrackApplication.REQUEST_HEADER.put("Content-Type", "application/json");
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
        showEditDialog();
    }


    public void showEditDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        bulletDialog = BulletSendDialog.newInstance(this);
        bulletDialog.show(ft, "dialog");

    }
    @Override
    public void addBullet(final String content,final String color,final int position,final TextView send){
        addBullet(content,color,position,send,true);
    }

    public void addBullet(final String content,final String color,final int position,final TextView send,final boolean isShow) {
        if (!UpdataUserInfo.isLogIn(this, true,null)) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        long sendId = Constants.CURRENT_USER.getData().getAccount().getId();
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
            obj.put("sendId", Constants.CURRENT_USER.getData().getAccount().getId());
            obj.put("content", content);
            final int bulletColor= Color.parseColor(color);
            obj.put("color", color);
            Preferences.getInstance(this).setPreference("bulletbackgroundcolor",bulletColor);
            obj.put("location", position);
            final int playTime=getAllLookHistory();
            if (CartoonReadPosition==-1){
                obj.put("playTime",CartoonReadPosition);

            }else {
                obj.put("playTime",playTime);
            }
            httpPost(Constants.URL_GET_ADD_BARRAGE, obj, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthSuccess(result, obj);
                    try {
                        if (obj.getString("code").equals("1000")) {
                            if (isShow){
                                Untils.showToast(getApplicationContext(),"弹幕添加成功");
                            }
                            if (send!=null){
                                send.setEnabled(true);
                            }
                            if (bulletDialog != null) {
                                bulletDialog.dismiss();
                            }
                            refrensh(content,bulletColor,position,playTime);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthFailure(result, obj);
                    send.setEnabled(true);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            send.setEnabled(true);
        }
    }
    private int getAllLookHistory() {
        try {
            List<AnimLookCookieBean> beanList= WoDbUtils.initialize(getApplicationContext()).
                    findAll(Selector.from(AnimLookCookieBean.class).where("rescoureid","=",barrage_charterId));
            if (beanList!=null&&beanList.size()!=0){
                return beanList.get(0).getLookTime()/1000;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return 0;

    }
    public void refrensh(String content,int color,int position,int playtime) {

    }


    public void showFial() {
        new DialogUtils.Builder(this).setCancelable(false).setTitle("提示")
                .setMessage("请输入正确手机号!").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                     dialog.cancel();
            }
        }).create().show();

//        View view = getLayoutInflater().inflate(R.layout.layout_popupwindow, null);
//        TextView mPopText = (TextView) view.findViewById(R.id.popup_text);
//        TextView mPopTextTitle = (TextView) view.findViewById(R.id.popup_text_title);
//        Button mPopBtnOne = (Button) view.findViewById(R.id.popup_btn_one);
//        Button mPopBtnTwo = (Button) view.findViewById(R.id.popup_btn_two);
//        mPopBtnTwo.setVisibility(View.GONE);
//        mPopTextTitle.setText("提示");
//        mPopText.setText("请输入正确手机号!");
//        mPopBtnOne.setText(getResources().getText(R.string.user_sure));
//        // mPopBtnTwo.setText(getResources().getText(R.string.user_fail));
//        final Dialog builder = new Dialog(this);
//        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        builder.setContentView(view);
//        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                return true;
//            }
//        });
//        mPopBtnOne.setTextColor(getResources().getColor(R.color.colorPrimary));
//        mPopBtnOne.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                builder.cancel();
//            }
//        });
//        /*mPopBtnTwo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                builder.cancel();
//            }
//        });*/
//        Window window = builder.getWindow();
//        // window.setWindowAnimations(R.style.dialogAnim);
//        android.view.WindowManager.LayoutParams lp = window.getAttributes();
//        window.setGravity(Gravity.BOTTOM);
//        lp.width = (int) (DeviceUtils.getScreenWH(this)[0] * 0.9); // 宽度
//        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(lp);
//        builder.getWindow().setGravity(Gravity.CENTER);
//
//        builder.show();
//        builder.setCanceledOnTouchOutside(false);
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Toast.makeText(this, " onRestart ", Toast.LENGTH_SHORT).show();
//    }
//
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch (level) {
            case TRIM_MEMORY_UI_HIDDEN:
                break;
        }
    }
}