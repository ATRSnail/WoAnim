package com.wodm.android.ui.user;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.TabPagerAdapter;
import com.wodm.android.bean.UserBean;
import com.wodm.android.dialog.ResetPwdDialog;
import com.wodm.android.login.Wx;
import com.wodm.android.tools.Tools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.utils.DeviceUtils;
import com.wodm.android.utils.Preferences;
import com.wodm.android.utils.UpdataUserInfo;

import org.eteclab.base.annotation.InflateView;
import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.utils.CommonUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


@Layout(R.layout.activity_login_regist)
public class LoginRegistActivity extends AppActivity {

    @ViewIn(R.id.login_regist_tab)
    private TabLayout mLoginRegistTab;
    @ViewIn(R.id.login_pager)
    private ViewPager mLoginRegistPager;

    @InflateView(R.layout.layout_login_pager)
    private View mLoginPager;
    @InflateView(R.layout.layout_register_pager)
    private View mRegisterPager;
    private ArrayList<View> pagerViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagerViews = new ArrayList<>();
        pagerViews.add(mLoginPager);
        pagerViews.add(mRegisterPager);
        initPageOne();
        initPageTwo();
        mLoginRegistPager.setAdapter(new TabPagerAdapter(pagerViews));
        mLoginRegistPager.setCurrentItem(0);
        mLoginRegistTab.setSelectedTabIndicatorColor(Color.argb(0xff, 0xff, 0xff, 0xff));
        mLoginRegistTab.setTabTextColors(Color.argb(0x80, 0xff, 0xff, 0xff), Color.argb(0xff, 0xff, 0xff, 0xff));
        mLoginRegistTab.setupWithViewPager(mLoginRegistPager);
        mLoginRegistTab.getTabAt(0).setText("登录");
        mLoginRegistTab.getTabAt(1).setText("注册");
    }
    private void login( String phone, String password){
        try {
            if (!CommonUtil.isMobileNO(phone)) {
                showFial();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            JSONObject obj = new JSONObject();
            obj.put("accountName", phone);
            obj.put("password", password);
            httpPost(Constants.USER_LOGIN, obj, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    try {
                        Toast.makeText(LoginRegistActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                        UserBean bean = new UserBean();
                        bean.setToken(obj.getString("token"));
                        bean.setUserId(obj.getLong("userId"));
                        bean.setType(obj.getString("type"));
                        Constants.CURRENT_USER = bean;
                        Preferences.getInstance(getApplicationContext()).setPreference("userId", bean.getUserId());
                        Preferences.getInstance(getApplicationContext()).setPreference("token", bean.getToken());
                        infos.getUserInfo(LoginRegistActivity.this, bean.getUserId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    try {
                        Toast.makeText(LoginRegistActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void initPageOne() {
        final EditText mPhone = (EditText) mLoginPager.findViewById(R.id.phone_num);
        final EditText mPassword = (EditText) mLoginPager.findViewById(R.id.password);
        mLoginPager.findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mPhone.getText().toString();
                String password = mPassword.getText().toString();
               login(phone,password);
            }
        });
        TextView mLose =
                (TextView) mLoginPager.findViewById(R.id.look_pass);
        mLose.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mLoginPager.findViewById(R.id.look_pass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPwdDialog dialog = new ResetPwdDialog(LoginRegistActivity.this);
                dialog.show();
            }
        });
        mLoginPager.findViewById(R.id.weChat_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wx.init(LoginRegistActivity.this).sendAuthRequest();
            }
        });
    }

    private void initPageTwo() {
        final TextView mGetCode = (TextView) mRegisterPager.findViewById(R.id.get_code);
        final EditText mCode = (EditText) mRegisterPager.findViewById(R.id.code);
        final EditText mPhone = (EditText) mRegisterPager.findViewById(R.id.phone_num);
        final EditText mPassword = (EditText) mRegisterPager.findViewById(R.id.password);

        mGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum = mPhone.getText().toString();
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
                                    mGetCode.setText(R.string.get_code);
                                    mGetCode.setEnabled(true);
                                    mGetCode.setBackgroundResource(R.drawable.shape_angle_rectangle_solid);
                                    timer.cancel();
                                } else {
                                    mGetCode.setText(i-- + "秒后重试");
                                }
                            }
                        });
                    }
                }, 0, 1000);
                mGetCode.setEnabled(false);
                mGetCode.setBackgroundResource(R.drawable.shape_angle_rectangle);
            }
        });
        mRegisterPager.findViewById(R.id.btn_regist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final String phone = mPhone.getText().toString();
                    String code = mCode.getText().toString();
                    final String password = mPassword.getText().toString();
                    if (TextUtils.isEmpty(code)) {
                        Toast.makeText(getApplicationContext(), "验证码不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mPassword.length() < 6 || mPassword.length() > 18) {
                        Toast.makeText(getApplicationContext(), "密码长度为6到18位", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JSONObject obj = new JSONObject();
                    obj.put("accountName", phone);
                    obj.put("authCode", code);
                    obj.put("password", password);
                    obj.put("osName", "android");

                    httpPost(Constants.USER_REGIST, obj, new HttpCallback() {
                        @Override
                        public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                            try {
                                Toast.makeText(LoginRegistActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
//                                mLoginRegistPager.setCurrentItem(0);
                                login(phone,password);
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
                                Toast.makeText(LoginRegistActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    UpdataUserInfo infos = new UpdataUserInfo() {
        @Override
        public void getUserInfo(UserBean bean) {
            Constants.CURRENT_USER = bean;
            LoginRegistActivity.this.finish();
        }
    };


    private void showFial() {
        View view = getLayoutInflater().inflate(R.layout.layout_popupwindow, null);
        TextView mPopText = (TextView) view.findViewById(R.id.popup_text);
        TextView mPopTextTitle = (TextView) view.findViewById(R.id.popup_text_title);
        Button mPopBtnOne = (Button) view.findViewById(R.id.popup_btn_one);
        Button mPopBtnTwo = (Button) view.findViewById(R.id.popup_btn_two);
        mPopBtnTwo.setVisibility(View.GONE);
        mPopTextTitle.setText("提示");
        mPopText.setText("请输入正确手机号？");
        mPopBtnOne.setText(getResources().getText(R.string.user_sure));
        // mPopBtnTwo.setText(getResources().getText(R.string.user_fail));
        final Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.setContentView(view);
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });
        mPopBtnOne.setTextColor(getResources().getColor(R.color.colorPrimary));
        mPopBtnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
            }
        });
        /*mPopBtnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.cancel();
            }
        });*/
        Window window = builder.getWindow();
        // window.setWindowAnimations(R.style.dialogAnim);
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        lp.width = (int) (DeviceUtils.getScreenWH(this)[0] * 0.9); // 宽度
        lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        builder.getWindow().setGravity(Gravity.CENTER);

        builder.show();
        builder.setCanceledOnTouchOutside(false);
    }

    public void startLogin(String openid, String unionid, String nickname, int sex, String headimgurl) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("openId", openid);
            obj.put("unionId", unionid);
            obj.put("nickName", nickname);
            obj.put("sex", sex);
            obj.put("portrait", headimgurl);
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "json.txt");
            try {
                FileWriter fileWriter = new FileWriter(f);
                fileWriter.write(obj.toString());
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            httpPost(Constants.USER_LOGIN_WX, obj, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthSuccess(result, obj);
                    try {
                        Preferences.getInstance(getApplicationContext()).setPreference("userId", Long.valueOf(obj.getString("userId")));
                        Preferences.getInstance(getApplicationContext()).setPreference("token", obj.getString("token"));
                        infos.getUserInfo(LoginRegistActivity.this, Long.valueOf(obj.getString("userId")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthFailure(result, obj);
                    Toast.makeText(getApplicationContext(), obj.toString() + "", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}