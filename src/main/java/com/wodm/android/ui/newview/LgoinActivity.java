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
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.tools.Tools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.utils.Preferences;
import com.wodm.android.utils.UpdataUserInfo;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by songchenyu on 16/9/26.
 */
@Layout(R.layout.new_aty_login)
public class LgoinActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter,View.OnClickListener{
    @ViewIn(R.id.set_topbar)
    private AtyTopLayout atyTopLayout;
    @ViewIn(R.id.et_password)
    private EditText et_password;
    @ViewIn(R.id.et_resigter)
    private EditText et_resigter;
    @ViewIn(R.id.btn_login)
    private Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        atyTopLayout.setOnTopbarClickListenter(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void leftClick() {
         finish();
    }

    @Override
    public void rightClick() {
         Intent intent=new Intent(this,ResingeActivity.class);
         startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                String password=et_password.getText().toString();
                String resigter=et_resigter.getText().toString();
                login(resigter,password);
                break;
        }
    }

    private void login(String phone, String password){
        try {
            if (!Tools.isMobileNO(phone)) {
                showFial();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length()<6||password.length()>18) {
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
                        UserInfoBean.DataBean dataBean=new UserInfoBean.DataBean();
                        UserInfoBean.DataBean.AccountBean accountBean=new UserInfoBean.DataBean.AccountBean();
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
            LgoinActivity.this.finish();
        }
    };
}
