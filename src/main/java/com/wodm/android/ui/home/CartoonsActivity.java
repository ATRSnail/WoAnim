package com.wodm.android.ui.home;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.ComicAdapter;
import com.wodm.android.bean.ObjectBean;
import com.wodm.android.ui.AppActivity;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.ui.widget.adapter.HolderAdapter;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 首页分类集合
 */
@Layout(R.layout.activity_cartoons)
public class CartoonsActivity extends AppActivity {

    @ViewIn(R.id.pull_list)
    private PullToLoadView mPullView;
    private int columnId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        columnId = getIntent().getIntExtra("columnId", 0);
        setCustomTitle(getIntent().getStringExtra("title"));
        mPullView.setLoadingColor(R.color.colorPrimary);
        mPullView.setPullCallback(new PullCallbackImpl(mPullView, new GridLayoutManager(this, 3)) {
            @Override
            protected void requestData(final int i, final boolean b) {

                httpGet(Constants.URL_HOME_TYPE_LIST + "?columnId=" + columnId + "&page=" + i, new HttpCallback() {

                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        loadOK();
                        Log.e("AAAAAAAAAA", "" + obj.toString());
                        try {
                            List<ObjectBean> list = new Gson().fromJson(obj.getString("data"), new TypeToken<List<ObjectBean>>() {
                            }.getType());
                            HolderAdapter ad = handleData(i, list, ComicAdapter.class, b);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthFailure(result, obj);
                        loadOK();
                    }
                });
            }
        });

        mPullView.initLoad();
    }
}
