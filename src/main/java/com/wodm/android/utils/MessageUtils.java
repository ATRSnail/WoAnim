package com.wodm.android.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.AtWoAdapter;
import com.wodm.android.adapter.newadapter.CommentAdapter2;
import com.wodm.android.adapter.newadapter.DianZanAdapter;
import com.wodm.android.adapter.newadapter.SystemInformAdapter;
import com.wodm.android.bean.AtWoBean;
import com.wodm.android.bean.CommentBean;
import com.wodm.android.bean.CommentBean2;
import com.wodm.android.bean.DianZanBean;
import com.wodm.android.bean.NewCommentBean;
import com.wodm.android.bean.SysMessBean;
import com.wodm.android.ui.AppActivity;
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
    MessageUtils   messageUtils;
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
            messageUtils.getSystemMessageList(mAdapter1);
            gridView_SystemInfo.setAdapter(mAdapter1);
        }else if(adapter instanceof DianZanAdapter){
            DianZanAdapter mAdapter2 = new DianZanAdapter(mcontext, flag);
            mAdapter2.setDianZan(dianzan);
            choice_bottom.setVisibility(visible);
            set_topbar.setTvRight(string);
            mAdapter2.setSet_topbar(set_topbar);
            mAdapter2.setUtils(messageUtils);
            messageUtils.getLikeMessageList(mAdapter2);
            gridView_SystemInfo.setAdapter(mAdapter2);
        }else if(adapter instanceof AtWoAdapter){
            AtWoAdapter mAdapter3 = new AtWoAdapter(mcontext, flag);
            choice_bottom.setVisibility(visible);
            set_topbar.setTvRight(string);
            mAdapter3.setSet_topbar(set_topbar);
            mAdapter3.setUtils(messageUtils);
            messageUtils.getReplyMessageList(mAdapter3);
            gridView_SystemInfo.setAdapter(mAdapter3);
        }else if(adapter instanceof CommentAdapter2){
            CommentAdapter2 mAdapter4 = new CommentAdapter2(mcontext, flag);
            choice_bottom.setVisibility(visible);
            set_topbar.setTvRight(string);
            mAdapter4.setSet_topbar(set_topbar);
            mAdapter4.setUtils(messageUtils);
            messageUtils.getCurrentMessageList(mAdapter4);
            gridView_SystemInfo.setAdapter(mAdapter4);
        }


       
    }




    public void deleteAllMessage(int type, final MessageUtils utils) {
        String url = Constants.DELETEALLMESSAGE+Constants.CURRENT_USER.getData().getAccount().getId()+"&type="+type;
        appActivity.httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                utils.updateData("勾选",false,View.GONE);
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



    public void deleteMessage(final MessageUtils utils, final List<Long> ids, final boolean delete) {
        String url = Constants.DELETEMESSAGE+Constants.CURRENT_USER.getData().getAccount().getId()+"&ids=";
        for (int i = 0; i <ids.size()-1 ; i++) {
            url=url+ids.get(i)+",";
        }
        url =url+ids.get(ids.size()-1);

        appActivity.httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                //删除之后立即更新
                ids.clear();//清楚删除列表
                if (delete)
                {
                    utils.updateData("勾选",false, View.GONE);
                }
                else {
                    utils.updateData("完成",true,View.VISIBLE);
                }

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

    public void dianZan(String url, long userId, long contentId, int type) {
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
                    NewCommentBean bean = new NewCommentBean();
                     bean.setZan(!bean.isZan());
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
    public void getSystemMessageList(final SystemInformAdapter adapter) {
        //获取所有的系统通知的消息内容;

        final String url = Constants.SystemMessageList+Constants.CURRENT_USER.getData().getAccount().getId();
        appActivity.httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                 SysMessBean  bean = new Gson().fromJson(obj.toString(),SysMessBean.class);
                 adapter.setList(bean.getData());
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

    public  void getReplyMessageList(final AtWoAdapter mAdapter3) {
        //获取所有的 有人@我的消息
        String url = Constants.ReplyeMessageList+Constants.CURRENT_USER.getData().getAccount().getId();
        appActivity.httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                AtWoBean bean =  new Gson().fromJson(obj.toString(),AtWoBean.class);
                mAdapter3.setList(bean.getData());
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
    public void getLikeMessageList(final DianZanAdapter mAdapter2) {
        //获取所有的点赞的详情的列表
        String url = Constants.LikeMessageList+Constants.CURRENT_USER.getData().getAccount().getId();
        appActivity.httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                DianZanBean bean =  new Gson().fromJson(obj.toString(),DianZanBean.class);
                mAdapter2.setList(bean.getData());
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
    public void getCurrentMessageList(final CommentAdapter2 mAdapter4) {
        //获取所有的评论列表（暂时的）
        String url = Constants.CURRENTComment+Constants.CURRENT_USER.getData().getAccount().getId();
        appActivity.httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                CommentBean2 bean =  new Gson().fromJson(obj.toString(),CommentBean2.class);
                mAdapter4.setList(bean.getData());
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
    public void readMessage  (int messageId) {
        //读取新消息
        String url = Constants.READMESSAGELIST+Constants.CURRENT_USER.getData().getAccount().getId()+"&messageId="+messageId;
        getData(url);
    }

}
