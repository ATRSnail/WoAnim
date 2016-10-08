package com.wodm.android.utils;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.android.Constants;
import com.wodm.android.bean.UserBean;
import com.wodm.android.ui.newview.LgoinActivity;

import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by json on 2016/5/3.
 */
public abstract class UpdataUserInfo {

    public void getUserInfo(final Context ctx, long userId) {
        HttpUtil.httpGet(ctx, Constants.URL_USERINFO + userId, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    UserBean bean = new Gson().fromJson(obj
                            .getString("data"), UserBean.class);
                    getUserInfo(bean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void doRequestFailure(Exception exception, String msg) {
                super.doRequestFailure(exception, msg);
                getUserInfo(null);
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
                getUserInfo(null);
            }
        });
    }

    public abstract void getUserInfo(UserBean bean);

    public static Boolean isLogIn(Context ctx, Boolean isLogin) {
        if (isLogin && Constants.CURRENT_USER == null) {
            ctx.startActivity(new Intent(ctx, LgoinActivity.class));
        }
        return Constants.CURRENT_USER != null;
    }
}