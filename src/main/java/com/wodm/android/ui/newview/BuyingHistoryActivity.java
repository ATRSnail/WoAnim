package com.wodm.android.ui.newview;

import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.BuyingHistoryAdapter;
import com.wodm.android.bean.BuyingHistoryBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;
import com.wodm.android.view.newview.NoScrollListView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songchenyu on 16/11/15.
 */
@Layout(R.layout.buying_history)
public class BuyingHistoryActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter{
    @ViewIn(R.id.lv_noscroll)
    private NoScrollListView lv_noscroll;
    @ViewIn(R.id.scrollView)
    private ScrollView scrollView;
    @ViewIn(R.id.set_topbar)
    private AtyTopLayout set_topbar;
    private BuyingHistoryAdapter adapter;
    private List<BuyingHistoryBean.DataBean> dataBeanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (scrollView != null) {
            scrollView.scrollTo(0, 0);
            scrollView.setFocusable(true);
            scrollView.setFocusableInTouchMode(true);
            scrollView.requestFocus();
        }
        initList();
        set_topbar.setOnTopbarClickListenter(this);
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }

    private void initList() {
        dataBeanList = new ArrayList<>();
        String url = null;
        if (Constants.CURRENT_USER != null) {
            url = Constants.BUYHISTORY +"?userId="+ Constants.CURRENT_USER.getData().getAccount().getId();
        }
        HttpUtil.httpGet(this, url, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                BuyingHistoryBean buyingHistoryBean =new Gson().fromJson(obj.toString(), BuyingHistoryBean.class);
                dataBeanList =buyingHistoryBean.getData();
                adapter=new BuyingHistoryAdapter(BuyingHistoryActivity.this,dataBeanList);
                lv_noscroll.setAdapter(adapter);
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
                dataBeanList = null;
            }

            @Override
            public void doRequestFailure(Exception exception, String msg) {
                super.doRequestFailure(exception, msg);
                dataBeanList = null;
            }
        });
        Log.e("AA","----------------------------"+dataBeanList.size());
        if(dataBeanList!=null&&dataBeanList.size()>0){
            }


    }


}
