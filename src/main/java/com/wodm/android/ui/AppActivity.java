package com.wodm.android.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.CartoonApplication;

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

public class AppActivity extends MaterialActivity {

    @ViewIn(R.id.toolbar)
    protected Toolbar mToolbar;
    @ViewIn(R.id.toolbar_title)
    protected TextView mToolbarTitle;
    @ViewIn(R.id.tooltitle_right)
    protected Button mTitleRight;
    @ViewIn(R.id.check_button)
    protected LinearLayout mCheckButton;

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



}