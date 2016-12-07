package com.wodm.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.tools.GetPhoneState;
import com.wodm.android.tools.TimeTools;
import com.wodm.android.utils.Preferences;
import com.wodm.android.utils.UpdataMedalInfo;
import com.wodm.android.utils.UpdataUserInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.http.HttpCallback;
import org.json.JSONException;
import org.json.JSONObject;

@Layout(R.layout.activity_welcome)
public class WelcomeActivity extends AppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getUserBehaviour();
//        getUseraaaBehaviour();
        if (Preferences.getInstance(getApplicationContext()).getPreference("is_first", false)) {
            String token = Preferences.getInstance(getApplicationContext()).getPreference("token", "");
            if (!TextUtils.isEmpty(token)) {
                httpGet(Constants.USER_TOKEN + token, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
//                        try {

                        Integer userId = Preferences.getInstance(getApplicationContext()).getPreference("userId", -1);
                        if(userId!=null)
                        UpdataMedalInfo.getMedalInfo(getApplicationContext(), userId);
                        userInfo.getUserInfo(getApplicationContext(), userId);

//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            handler.sendEmptyMessageDelayed(1, 0);
//                        }
                    }

                    @Override
                    public void doRequestFailure(Exception exception, String msg) {
                        super.doRequestFailure(exception, msg);
                        handler.sendEmptyMessageDelayed(1, 0);
                    }

                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthFailure(result, obj);
                        handler.sendEmptyMessageDelayed(1, 0);
                    }
                });
            } else {
                handler.sendEmptyMessageDelayed(1, 1000);
            }

        } else {
            handler.sendEmptyMessageDelayed(2, 1000);

        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    startActivity(new Intent(WelcomeActivity.this, Main2Activity.class));
                    finish();
                    break;
                case 2:
                    //去掉引导页
//                    startActivity(new Intent(WelcomeActivity.this, LeaderActivity.class));
                    startActivity(new Intent(WelcomeActivity.this, Main2Activity.class));
                    Preferences.getInstance(getApplicationContext()).setPreference("is_first", true);
                    finish();
                    break;
            }

        }
    };
//    private void getUseraaaBehaviour(){
////        final ArrayList<UserBehavierInfo> allList = getAllTimeLong();
////        JSONObject js=changeJsonObject();
//        JSONObject js=new JSONObject();
////            js.put("userBehaviourInfo",allList.toString());
//        try {
//            js.put("userBehaviourInfo","aaaaaaaa");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        httpPost(Constants.APP_GET_MALL_OF_SAVEUSERBEHAVIOURINFO,js, new HttpCallback() {
//
//            @Override
//            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
//                super.doAuthSuccess(result, obj);
//            }
//
//            @Override
//            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
//                super.doAuthFailure(result, obj);
//            }
//        });
//
//    }
    private void getUserBehaviour(){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("deviceId", GetPhoneState.readTelephoneSerialNum(this));
            jsonObject.put("devicePlatform",1);
            jsonObject.put("deviceBrand",GetPhoneState.getBrand());
            jsonObject.put("deviceModel",GetPhoneState.getModel());
            jsonObject.put("systemVersion",GetPhoneState.getSysRelease());
            jsonObject.put("appVersion",GetPhoneState.getAppVersionName(this));
            jsonObject.put("times", TimeTools.getNianTime());
            jsonObject.put("timeLong", 0);
            httpPost(Constants.APP_GET_MALL_OF_SAVEUSERBEHAVIOUR,jsonObject, new HttpCallback() {

                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthSuccess(result, obj);
                    Preferences.getInstance(getApplicationContext()).setPreference("userBehavier", obj.optInt("data",0));
                    Preferences.getInstance(getApplicationContext()).setPreference("initStartTime", System.currentTimeMillis());
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthFailure(result, obj);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    UpdataUserInfo userInfo = new UpdataUserInfo() {
        @Override
        public void getUserInfo(UserInfoBean bean) {
            Constants.CURRENT_USER = bean;
            handler.sendEmptyMessageDelayed(1, 0);
        }
    };
}