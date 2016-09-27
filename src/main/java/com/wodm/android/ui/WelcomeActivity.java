package com.wodm.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.UserBean;
import com.wodm.android.utils.Preferences;
import com.wodm.android.utils.UpdataUserInfo;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.http.HttpCallback;
import org.json.JSONObject;

@Layout(R.layout.activity_welcome)
public class WelcomeActivity extends AppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Preferences.getInstance(getApplicationContext()).getPreference("is_first", false)) {
            String token = Preferences.getInstance(getApplicationContext()).getPreference("token", "");
            if (!TextUtils.isEmpty(token)) {
                httpGet(Constants.USER_TOKEN + token, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
//                        try {
                            long userId = Preferences.getInstance(getApplicationContext()).getPreference("userId", -1L);
//                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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
                    startActivity(new Intent(WelcomeActivity.this, LeaderActivity.class));
                    finish();
                    break;
            }

        }
    };

    UpdataUserInfo userInfo = new UpdataUserInfo() {
        @Override
        public void getUserInfo(UserBean bean) {
            Constants.CURRENT_USER = bean;
            handler.sendEmptyMessageDelayed(1, 0);
        }
    };
}