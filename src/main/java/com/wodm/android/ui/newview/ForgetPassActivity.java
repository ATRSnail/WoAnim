package com.wodm.android.ui.newview;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.UserBean;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.tools.Tools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.ui.Main2Activity;
import com.wodm.android.utils.Preferences;
import com.wodm.android.utils.UpdataUserInfo;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by songchenyu on 16/9/27.
 */
@Layout(R.layout.activity_forget_pass)
public class ForgetPassActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter, View.OnClickListener {
    @ViewIn(R.id.set_topbar)
    private AtyTopLayout atyTopLayout;
    @ViewIn(R.id.et_phone)
    private EditText et_phone;
    @ViewIn(R.id.et_password)
    private EditText et_password;
    @ViewIn(R.id.et_yzm)
    private EditText et_yzm;
    @ViewIn(R.id.btn_finish)
    private Button btn_finish;
    @ViewIn(R.id.btn_yzm)
    private Button btn_yzm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        atyTopLayout.setOnTopbarClickListenter(this);
        atyTopLayout.setRightIsVisible(false);
        btn_finish.setOnClickListener(this);
        btn_yzm.setOnClickListener(this);
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }

    private void sendResigter() {
        final String phone = Tools.getText(et_phone);
        final String password = Tools.getText(et_password);
        String yzm = Tools.getText(et_yzm);
        if (TextUtils.isEmpty(yzm) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "输入的内容不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            if (password.length() < 6 || password.length() > 18) {
                Toast.makeText(getApplicationContext(), "密码长度为6到18位", Toast.LENGTH_SHORT).show();
                return;
            }
            JSONObject obj = new JSONObject();
            obj.put("accountName", phone);
            obj.put("authCode", yzm);
            obj.put("password", password);
            obj.put("osName", "android");

            httpPost(Constants.USER_FORGET_PASSWORD, obj, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    try {
                        Toast.makeText(ForgetPassActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
//                                mLoginRegistPager.setCurrentItem(0);
                        login(phone, password);
                                /*bean.setUserId(obj.getLong("userId"));
                                bean.setToken(obj.getString("token"));*/
                        //info.getUserInfo(getApplicationContext(), bean.getUserId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    try {
                        Toast.makeText(ForgetPassActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void login(String phone, String password) {
        try {
            if (!Tools.isMobileNO(phone)) {
                showFial();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() < 6 || password.length() > 18) {
                Toast.makeText(getApplicationContext(), "密码长度在6--18位之间", Toast.LENGTH_SHORT).show();
                return;
            }

            JSONObject obj = new JSONObject();
            obj.put("accountName", phone);
            obj.put("password", password);
            httpPost(Constants.USER_LOGIN, obj, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    try {
                        Toast.makeText(ForgetPassActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                        UserBean bean = new UserBean();
                        bean.setToken(obj.getString("token"));
                        bean.setUserId(obj.getLong("userId"));
                        bean.setType(obj.getString("type"));
//                        Constants.CURRENT_USER = bean;
                        Preferences.getInstance(getApplicationContext()).setPreference("userId", bean.getUserId());
                        Preferences.getInstance(getApplicationContext()).setPreference("token", bean.getToken());
                        infos.getUserInfo(ForgetPassActivity.this, bean.getUserId());
                        Intent intent = new Intent(ForgetPassActivity.this, Main2Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    try {
                        Toast.makeText(ForgetPassActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    UpdataUserInfo infos = new UpdataUserInfo() {
        @Override
        public void getUserInfo(UserInfoBean bean) {
            Constants.CURRENT_USER = bean;
            ForgetPassActivity.this.finish();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_finish:
                sendResigter();
                break;
            case R.id.btn_yzm:
                getYzm();
                break;
        }
    }

    private void getYzm() {
        String phoneNum = Tools.getText(et_phone);
        if ("".equals(phoneNum) || !Tools.isMobileNO(phoneNum)) {
            showFial();
            return;
        }

        String url = Constants.USER_GET_CODE + "?m=" + phoneNum;
        httpGet(url, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
//                        try {
////                            mCode.setText(obj.getString("authCode"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
            }
        });
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            private int i = 59;

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (i == 0) {
                            btn_yzm.setText(R.string.get_code);
                            btn_yzm.setEnabled(true);
                            btn_yzm.setBackgroundResource(R.drawable.btn_yzm);
                            timer.cancel();
                        } else {
                            btn_yzm.setText(i-- + "秒后重试");
                        }
                    }
                });
            }
        }, 0, 1000);
        btn_yzm.setEnabled(false);
        btn_yzm.setTextColor(getResources().getColor(R.color.color_ffffff));
        btn_yzm.setBackgroundResource(R.drawable.shape_angle_rectangle_solid);
    }


}
