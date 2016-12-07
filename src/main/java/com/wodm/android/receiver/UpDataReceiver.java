package com.wodm.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.android.CartoonApplication;
import com.wodm.android.Constants;
import com.wodm.android.bean.UserBehavierInfo;
import com.wodm.android.db.WoDbUtils;

import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.track.TrackApplication;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by songchenyu on 16/11/25.
 */

public class UpDataReceiver extends BroadcastReceiver {
    private ArrayList<UserBehavierInfo> InfoList;
    private Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        mContext=context;
        String action=intent.getAction();
        if (action.equals("com.data.up")){
            InfoList= (ArrayList<UserBehavierInfo>) intent.getExtras().getSerializable("list");
//            getJsonArray();
            getUserBehaviour();
        }
    }
    private void getUserBehaviour(){
//        final ArrayList<UserBehavierInfo> allList = getAllTimeLong();
        JSONObject js=changeJsonObject();
//        JSONObject js=new JSONObject();
//            js.put("userBehaviourInfo",allList.toString());
        //            js.put("userBehaviourInfo","aaaaaaaa");
        httpPost(Constants.APP_GET_MALL_OF_SAVEUSERBEHAVIOURINFO,js, new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                deleteData(InfoList);
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }
        });

    }
    private JSONObject changeJsonObject(){
        JSONObject jsonObject=new JSONObject();
        ArrayList<UserBehavierInfo> allList = getAllTimeLong();
        JSONArray jsonArray=new JSONArray();
        try {
            for (int i = 0; i < allList.size(); i++) {
                UserBehavierInfo userInfo=allList.get(i);
                JSONObject object=new JSONObject();
                object.put("behaviourId",userInfo.getBehavier_id());
                long timeLong=userInfo.getTotalTime()/1000;
                object.put("theContentLong",timeLong);
                object.put("resourceId",userInfo.getResourceId());
                object.put("clickNum",userInfo.getClickNum());
                jsonArray.put(object);
            }
            jsonObject.put("list",jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
    private void deleteData(ArrayList<UserBehavierInfo> allList){
        for (UserBehavierInfo info:allList) {
            long id=info.getResourceId();
            if (info.getEnd_time()==0){
                return;
            }
            try {
                WoDbUtils.initialize(mContext).delete(UserBehavierInfo.class, WhereBuilder.b("resourceId"," = ",id));
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }
    public void httpPost(String url, JSONObject body, final HttpCallback callback) {
        try {
            TrackApplication.REQUEST_HEADER.put("Content-Type", "application/json");
            HttpUtil.httpPost(mContext, url, body, CartoonApplication.REQUEST_HEADER, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<UserBehavierInfo> getAllTimeLong(){
        ArrayList<UserBehavierInfo> allList=new ArrayList<>();
        //计算时长
        for (UserBehavierInfo info:InfoList) {
            long totalTime=0;
            long rescoureId=info.getResourceId();
            boolean isAdd=false;

            for (int k = 0; k <InfoList.size() ; k++) {
                UserBehavierInfo modelInfo=InfoList.get(k);
                if (rescoureId==modelInfo.getResourceId()){
                    totalTime=totalTime+(modelInfo.getEnd_time()-modelInfo.getStart_time());
                    UserBehavierInfo newUserInfo=new UserBehavierInfo();
                    newUserInfo.setResourceId(modelInfo.getResourceId());
                    newUserInfo.setBehavier_id(modelInfo.getBehavier_id());
                    newUserInfo.setTotalTime(totalTime);
                    newUserInfo.setClickNum(modelInfo.getClickNum());
                    for (UserBehavierInfo allinfo:allList) {
                        if (allinfo.getResourceId()==rescoureId){
                            isAdd=true;
                        }
                    }
                    if (!isAdd){
                        allList.add(newUserInfo);
                        isAdd=false;
                    }

                    break;
                }

            }
        }


        return allList;
    }

}
