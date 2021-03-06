package com.wodm.android.utils;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.android.Constants;
import com.wodm.android.bean.MedalInfoBean;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.ui.newview.LgoinActivity;

import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.json.JSONObject;

/**
 * Created by json on 2016/5/3.
 */
public abstract class UpdataMedalInfo {

    public static void getMedalInfo(final Context ctx, long userId) {

        HttpUtil.httpGet(ctx, Constants.APP_GET_MEDALLIST + userId, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                MedalInfoBean bean = new Gson().fromJson(obj.toString(), MedalInfoBean.class);
                Constants.MEDALINFOBEAN = bean;
//                Constants.setMEDALINFOBEAN(bean);
            }

            @Override
            public void doRequestFailure(Exception exception, String msg) {
                super.doRequestFailure(exception, msg);
                Constants.setMEDALINFOBEAN(null);
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
                Constants.setMEDALINFOBEAN(null);
            }
        });
    }
}