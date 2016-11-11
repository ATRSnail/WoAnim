package com.wodm.android.ui.newview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.ResponseInfo;
import com.tencent.tauth.Tencent;
import com.umeng.analytics.MobclickAgent;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.login.Wx;
import com.wodm.android.tools.Tools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.utils.QQUtils;
import com.wodm.android.utils.Preferences;
import com.wodm.android.utils.UpdataUserInfo;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by songchenyu on 16/9/26.
 */
@Layout(R.layout.new_aty_login)
public class LgoinActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter, View.OnClickListener {
    @ViewIn(R.id.set_topbar)
    private AtyTopLayout atyTopLayout;
    @ViewIn(R.id.et_password)
    private EditText et_password;
    @ViewIn(R.id.et_resigter)
    private EditText et_resigter;
    @ViewIn(R.id.btn_login)
    private Button btn_login;
    @ViewIn(R.id.img_we_chat)
    private ImageView img_we_chat;
    @ViewIn(R.id.img_qq)
    private ImageView img_qq;
    @ViewIn(R.id.forget_pass_login)
    private TextView forget_pass_login;
    boolean loginFlag = true;
    private Intent intentclass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras()!=null){
            intentclass=getIntent().getParcelableExtra("intentclass");
        }
        atyTopLayout.setOnTopbarClickListenter(this);
        btn_login.setOnClickListener(this);
        img_we_chat.setOnClickListener(this);
        img_qq.setOnClickListener(this);
        forget_pass_login.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(LgoinActivity.this, Main2Activity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
    }

    @Override
    public void leftClick() {
//        Intent intent = new Intent(LgoinActivity.this, Main2Activity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
        finish();
    }

    @Override
    public void rightClick() {
        Intent intent = new Intent(this, ResingeActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String password = et_password.getText().toString();
                String resigter = et_resigter.getText().toString();
                login(resigter, password);
                if (!TextUtils.isEmpty(getUserId())) {
                    MobclickAgent.onProfileSignIn(getUserId());//统计登录
                }
                break;
            case R.id.img_we_chat:
                Wx.init(LgoinActivity.this).sendAuthRequest();
                if (!TextUtils.isEmpty(getUserId())) {
                    MobclickAgent.onProfileSignIn("WX", getUserId());//统计微信登录
                }
                break;
            case R.id.img_qq:
//                Tencent mTencent = Tencent.createInstance("222222", this.getApplicationContext());
//                tencentLogin(mTencent);
//                if (!TextUtils.isEmpty(getUserId())) {
//                    MobclickAgent.onProfileSignIn("QQ", getUserId());//统计微信登录
//                }
                break;
            case R.id.forget_pass_login:
                startActivity(new Intent(this, ForgetPassActivity.class));
                break;
        }
    }

    private void tencentLogin(Tencent mTencent) {
            QQUtils qq = new QQUtils(mTencent,LgoinActivity.this);
        qq.login();
    }

    private String getUserId() {
        if (Constants.CURRENT_USER == null)
            return null;
        String userID = String.valueOf(Constants.CURRENT_USER.getData().getAccount().getId());
        return userID;
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
                        Toast.makeText(LgoinActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                        UserInfoBean bean = new UserInfoBean();
                        UserInfoBean.DataBean dataBean = new UserInfoBean.DataBean();
                        UserInfoBean.DataBean.AccountBean accountBean = new UserInfoBean.DataBean.AccountBean();
                        dataBean.setToken(obj.getString("token"));
                        accountBean.setId(obj.getInt("userId"));
                        accountBean.setType(obj.getInt("type"));
                        dataBean.setAccount(accountBean);
                        bean.setData(dataBean);
                        Constants.CURRENT_USER = bean;
                        Preferences.getInstance(getApplicationContext()).setPreference("userId", bean.getData().getAccount().getId());
                        Preferences.getInstance(getApplicationContext()).setPreference("token", bean.getData().getToken().toString());
                        infos.getUserInfo(LgoinActivity.this, bean.getData().getAccount().getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    try {
                        Toast.makeText(LgoinActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
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
            if (intentclass!=null){
                startActivity(intentclass);
                finish();
                return;
            }
            finish();
//            Intent intent = new Intent(LgoinActivity.this, Main2Activity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//            finish();
//            if (loginFlag) {
//                Intent intent = new Intent(LgoinActivity.this, Main2Activity.class);
//                startActivity(intent);
//            }
        }
    };

    public void startLogin(String openid, String unionid, String nickname, int sex, String headimgurl) {
        loginFlag = false;
        JSONObject obj = new JSONObject();
        // 自定义注册统计事件，需要在友盟注册事件ID,key 统计第三方注册
        Map<String, String> map = new HashMap<String, String>();
        map.put("WX", unionid);
        MobclickAgent.onEvent(LgoinActivity.this, "register", map);
        try {
            obj.put("openId", openid);
            obj.put("unionId", unionid);
            obj.put("nickName", nickname);
            obj.put("sex", sex);
            obj.put("portrait", headimgurl);
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "json.txt");
            try {
                //42268986033
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
                        Preferences.getInstance(getApplicationContext()).setPreference("userId", obj.optInt("userId"));
                        Preferences.getInstance(getApplicationContext()).setPreference("token", obj.getString("token"));
                        infos.getUserInfo(LgoinActivity.this, Long.valueOf(obj.getString("userId")));
//                        Intent intent = new Intent(LgoinActivity.this, Main2Activity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(intent);
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
