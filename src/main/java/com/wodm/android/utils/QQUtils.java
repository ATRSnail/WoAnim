package com.wodm.android.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.wodm.android.ui.newview.LgoinActivity;

import org.json.JSONObject;

/**
 * Created by ATRSnail on 2016/11/3.
 */

public class QQUtils {
  static   IUiListener loginListener; //授权登录监听器
    IUiListener userInfoListener; //获取用户信息监听器
    String scope; //获取信息的范围参数
    UserInfo userInfo; //qq用户信息
    Tencent tencent;//qq主操作对象
    LgoinActivity l;

    public QQUtils(Tencent tencent, LgoinActivity l) {
        this.tencent = tencent;
        this.l = l;
    }

    public  void login(){
        initData();
        if (!tencent.isSessionValid())
        {
            //如果session无效，就开始登录
            if (!tencent.isSessionValid()) {
                //开始qq授权登录
                tencent.login(l, "all", loginListener);
            }
        }
        userInfo = new UserInfo(l, tencent.getQQToken());
        userInfo.getUserInfo(userInfoListener);

    }
    public void initData() {
        //初始化qq主操作对象
        tencent = Tencent.createInstance("222222", l);
        //要所有权限，不然会再次申请增量权限，这里不要设置成get_user_info,add_t
        scope = "all";

        loginListener = new IUiListener() {
            @Override
            public void onComplete(Object value) {
                if (value == null) {
                    return;
                }

                try {
                    JSONObject jo = (JSONObject) value;

                    int ret = jo.getInt("ret");

                    if (ret == 0) {
                        Toast.makeText(l, "登录成功",
                                Toast.LENGTH_LONG).show();

                        String openID = jo.getString("openid");
                        String accessToken = jo.getString("access_token");
                        String expires = jo.getString("expires_in");
                        tencent.setOpenId(openID);
                        tencent.setAccessToken(accessToken, expires);

                    }

                } catch (Exception e) {
                }
            }

            @Override
            public void onError(UiError uiError) {
                tencent.logout(l);
            }

            @Override
            public void onCancel() {
                tencent.logout(l);
            }
        };

        userInfoListener = new IUiListener() {
            @Override
            public void onComplete(Object arg0) {
                if(arg0 == null){
                    return;
                }
                try {
                    JSONObject jo = (JSONObject) arg0;
                    String nickName = jo.getString("nickname");
                    String gender = jo.getString("gender");


                  Log.e("","------------------"+arg0.toString());

                } catch (Exception e) {

                }
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        };
    }

}