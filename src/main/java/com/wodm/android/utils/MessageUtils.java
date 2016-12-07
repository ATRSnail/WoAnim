package com.wodm.android.utils;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.AtWoAdapter;
import com.wodm.android.adapter.newadapter.DianZanAdapter;
import com.wodm.android.adapter.newadapter.SystemInformAdapter;
import com.wodm.android.bean.AtWoBean;
import com.wodm.android.bean.DianZanBean;
import com.wodm.android.bean.SysMessBean;
import com.wodm.android.bean.SysMessBean.DataBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.newview.DianZanActivity;
import com.wodm.android.ui.newview.SystemInformActivity;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.http.HttpCallback;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ATRSnail on 2016/12/5.
 */

public class MessageUtils {
    BaseAdapter adapter;
    LinearLayout choice_bottom;
    ListView gridView_SystemInfo;
    AtyTopLayout set_topbar;
    Context mcontext;
    boolean dianzan=false;
    List<SysMessBean.DataBean> list=new ArrayList<>();
    List<AtWoBean.DataBean> list2=new ArrayList<>();
    List<DianZanBean.DataBean> list3=new ArrayList<>();
    MessageUtils   messageUtils;
    private static final ThreadLocal re = new ThreadLocal(); //自行补上泛型Object
    public MessageUtils() {

    }

    public void setDianzan(boolean dianzan) {
        this.dianzan = dianzan;
    }

    public MessageUtils(BaseAdapter adapter, LinearLayout choice_bottom, ListView gridView_SystemInfo, AtyTopLayout set_topbar, Context mcontext) {
        this.adapter = adapter;
        this.choice_bottom = choice_bottom;
        this.gridView_SystemInfo = gridView_SystemInfo;
        this.set_topbar = set_topbar;
        this.mcontext = mcontext;
       messageUtils = new MessageUtils();
    }

    AppActivity appActivity = new AppActivity();
    public  void updateData(String string,boolean flag,int visible){
        choice_bottom.setVisibility(visible);
        set_topbar.setTvRight(string);
        if(adapter instanceof SystemInformAdapter){
            SystemInformAdapter mAdapter1 = new SystemInformAdapter(mcontext, flag);
            choice_bottom.setVisibility(visible);
            set_topbar.setTvRight(string);
            mAdapter1.setUtils(messageUtils);
            mAdapter1.setSet_topbar(set_topbar);
            gridView_SystemInfo.setAdapter(mAdapter1);
        }else if(adapter instanceof DianZanAdapter){
            DianZanAdapter mAdapter2 = new DianZanAdapter(mcontext, flag);
            mAdapter2.setDianZan(dianzan);
            choice_bottom.setVisibility(visible);
            set_topbar.setTvRight(string);
            mAdapter2.setSet_topbar(set_topbar);
            mAdapter2.setUtils(messageUtils);
            gridView_SystemInfo.setAdapter(mAdapter2);
        }else if(adapter instanceof AtWoAdapter){
            AtWoAdapter mAdapter3 = new AtWoAdapter(mcontext, flag);
            choice_bottom.setVisibility(visible);
            set_topbar.setTvRight(string);
            mAdapter3.setSet_topbar(set_topbar);
            mAdapter3.setUtils(messageUtils);
            gridView_SystemInfo.setAdapter(mAdapter3);
        }


       
    }



    public void deleteAllMessage(int type) {
        String url = Constants.DELETEALLMESSAGE+Constants.CURRENT_USER.getData().getAccount().getId()+"&type="+type;
        getData(url);
    }

    private void getData(String url) {
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

    public void deleteMessage(List<Long> ids) {
        String url = Constants.DELETEMESSAGE+Constants.CURRENT_USER.getData().getAccount().getId()+"&ids="+ids;
        getData(url);
    }

    public void dianZan(String url, long userId,long contentId,int type) {
        //保存用户点赞的、 用户取消点赞的、内容的方法
        JSONObject object = new JSONObject();
        try {
            object.put("userId",userId);
            object.put("contentId",contentId);
            object.put("type",type);
            postData(url, object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void postData(String url, JSONObject object) {
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
    }

    public  List<SysMessBean.DataBean> getSystemMessageList() {
        //获取所有的系统通知的消息内容;

        String url = Constants.SystemMessageList+Constants.CURRENT_USER.getData().getAccount().getId();
        appActivity.httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                SysMessBean  bean = new Gson().fromJson(obj.toString(),SysMessBean.class);
//                re.set(bean);
                list.clear();
                list.addAll(bean.getData());
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
//        if ((List<SysMessBean.DataBean>) re.get()==null){
//            Log.e("AA","*******************(List<SysMessBean.DataBean>) re.get()为空");
//        }else {
//            Log.e("AA","*******************list"+((List<SysMessBean.DataBean>) re.get()).size());
//        }
        return list;
//        return (List<SysMessBean.DataBean>) re.get();
    }

    public  List<AtWoBean.DataBean> getReplyMessageList () {
        //获取所有的 有人@我的消息
        String url = Constants.ReplyeMessageList+Constants.CURRENT_USER.getData().getAccount().getId();
        appActivity.httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                AtWoBean bean =  new Gson().fromJson(obj.toString(),AtWoBean.class);
                list2.clear();
                list2.addAll(bean.getData());
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
        return list2;
    }
    public List<DianZanBean.DataBean> getLikeMessageList () {
        //获取所有的点赞的详情的列表
        String url = Constants.LikeMessageList+Constants.CURRENT_USER.getData().getAccount().getId();
        appActivity.httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                DianZanBean bean =  new Gson().fromJson(obj.toString(),DianZanBean.class);
                list3.clear();
                list3.addAll(bean.getData());
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
        return list3;
    }
    public void readMessage  (int messageId) {
        //读取新消息
        String url = Constants.READMESSAGELIST+Constants.CURRENT_USER.getData().getAccount().getId()+"&messageId="+messageId;
        getData(url);
    }

}
