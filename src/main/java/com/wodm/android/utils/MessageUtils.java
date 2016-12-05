package com.wodm.android.utils;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.AtWoAdapter;
import com.wodm.android.adapter.newadapter.DianZanAdapter;
import com.wodm.android.adapter.newadapter.SystemInformAdapter;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.newview.SystemInformActivity;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.http.HttpCallback;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ATRSnail on 2016/12/5.
 */

public class MessageUtils {
    BaseAdapter adapter;
    LinearLayout choice_bottom;
    ListView gridView_SystemInfo;
    AtyTopLayout set_topbar;
    Context mcontext;

    public MessageUtils(BaseAdapter adapter, LinearLayout choice_bottom, ListView gridView_SystemInfo, AtyTopLayout set_topbar, Context mcontext) {
        this.adapter = adapter;
        this.choice_bottom = choice_bottom;
        this.gridView_SystemInfo = gridView_SystemInfo;
        this.set_topbar = set_topbar;
        this.mcontext = mcontext;
    }

    AppActivity appActivity = new AppActivity();
    public  void updateData(String string,boolean flag,int visible){
        choice_bottom.setVisibility(visible);
        set_topbar.setTvRight(string);
        if(adapter instanceof SystemInformAdapter){
            SystemInformAdapter mAdapter = new SystemInformAdapter(mcontext, flag);
            choice_bottom.setVisibility(visible);
            set_topbar.setTvRight(string);
            mAdapter.setSet_topbar(set_topbar);
            gridView_SystemInfo.setAdapter(mAdapter);
        }else if(adapter instanceof DianZanAdapter){
            DianZanAdapter mAdapter = new DianZanAdapter(mcontext, flag);
            choice_bottom.setVisibility(visible);
            set_topbar.setTvRight(string);
            mAdapter.setSet_topbar(set_topbar);
            gridView_SystemInfo.setAdapter(mAdapter);
        }else if(adapter instanceof AtWoAdapter){
            AtWoAdapter mAdapter = new AtWoAdapter(mcontext, flag);
            choice_bottom.setVisibility(visible);
            set_topbar.setTvRight(string);
            mAdapter.setSet_topbar(set_topbar);
            gridView_SystemInfo.setAdapter(mAdapter);
        }


       
    }



    public void deleteAllMessage(int type) {
        String url = Constants.DELETEALLMESSAGE+Constants.CURRENT_USER.getData().getAccount().getId()+"&type="+type;
        appActivity.httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }

            @Override
            public void doRequestFailure(Exception exception, String msg) {
                super.doRequestFailure(exception, msg);
            }
        });
    }

    public void deleteMessage() {
        String url = Constants.DELETEMESSAGE+Constants.CURRENT_USER.getData().getAccount().getId()+"&ids=";
        appActivity.httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }

            @Override
            public void doRequestFailure(Exception exception, String msg) {
                super.doRequestFailure(exception, msg);
            }
        });
    }

    public void dianZan(String url, long userId,long contentId,int type) {
        //保存用户点赞的、 用户取消点赞的、内容的方法
        JSONObject object = new JSONObject();
        try {
            object.put("userId",userId);
            object.put("contentId",contentId);
            object.put("type",type);
            appActivity.httpPost(url,object,new HttpCallback(){
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthSuccess(result, obj);
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthFailure(result, obj);
                }

                @Override
                public void doRequestFailure(Exception exception, String msg) {
                    super.doRequestFailure(exception, msg);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
