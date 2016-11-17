package com.wodm.android.ui.newview;


import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.tools.Tools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

@Layout(R.layout.activity_pay)
public class PayActivity extends AppActivity implements View.OnClickListener, AtyTopLayout.myTopbarClicklistenter {
    @ViewIn(R.id.back_pay)
    AtyTopLayout atyTopLayout;
    @ViewIn(R.id.yzm_pay)
    Button btn_yzm;
    @ViewIn(R.id.zhifu_pay)
    Button zhifu_pay;
    @ViewIn(R.id.yzm_edit_pay)
    EditText yzm_edit_pay;
    @ViewIn(R.id.phone1_pay)
    TextView phone1_pay;
    @ViewIn(R.id.phone2_pay)
    TextView phone2_pay;
    @ViewIn(R.id.phone3_pay)
    TextView phone3_pay;
    private String phone = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vpay);
        String vip = getIntent().getStringExtra("vip");

//        if("开通VIP".equals(vip)){
//            setContentView(R.layout.activity_vpay);
//        }else {
//            setContentView(R.layout.activity_vvpay);
//        }
        initPhone();
        atyTopLayout.setOnTopbarClickListenter(this);
        btn_yzm.setOnClickListener(this);
        zhifu_pay.setOnClickListener(this);
        yzm_edit_pay.addTextChangedListener(textWatcher);
    }

    private void initPhone() {
        phone = getIntent().getStringExtra("phone");
        if (phone != null&&phone.length()>0) {
            phone1_pay.setText(phone.substring(0, 3));
            phone2_pay.setText(phone.substring(3, 7));
            phone3_pay.setText(phone.substring(7));
        }

    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yzm_pay:
                getYzm();
                break;
            case R.id.zhifu_pay:
//                zhifuResult();

                break;
        }
    }

    private void zhifuResult() {
        String yzm = Tools.getText(yzm_edit_pay);
        if (TextUtils.isEmpty(yzm)) {
            Toast.makeText(PayActivity.this, "验证码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        //点击支付上传支付验证码
        if (yzm.length() == 6) {
            try {
                JSONObject obj = new JSONObject();
                obj.put("accountName", phone);
//                obj.put("password", password);
                obj.put("authCode", yzm);
                obj.put("osName", "android");
                obj.put("platformType", 1);
                ApplicationInfo appInfo = getPackageManager()
                        .getApplicationInfo(getPackageName(),
                                PackageManager.GET_META_DATA);
                int msg = appInfo.metaData.getInt("UMENG_CHANNEL", 0);
                obj.put("channelId", msg);
                obj.put("productName", "联通动漫");
                httpPost(Constants.USER_REGIST, obj, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        try {
                            Toast.makeText(PayActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        try {
                            Toast.makeText(PayActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private void getYzm() {
        String phoneNum = phone;
        String url = Constants.USER_GET_CODE + "?m=" + phoneNum;
        httpGet(url, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
            }
        });
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            private int i = 60;

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (i == 1) {
                            btn_yzm.setText(R.string.get_code);
                            btn_yzm.setTextColor(getResources().getColor(R.color.color_ffffff));
                            btn_yzm.setEnabled(true);
                            btn_yzm.setBackgroundResource(R.drawable.pay_circle_corner3);
                            timer.cancel();
                        } else {
                            btn_yzm.setText("还有" + i-- + "s");
                        }
                    }
                });
            }
        }, 0, 1000);
        btn_yzm.setEnabled(false);
        btn_yzm.setTextColor(getResources().getColor(R.color.color_999999));
        btn_yzm.setBackgroundResource(R.drawable.pay_ran_getcode);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        CharSequence string = null;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            string = s;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (string.length() == 6) {
                zhifu_pay.setTextColor(getResources().getColor(R.color.color_ffffff));
                zhifu_pay.setEnabled(true);
                zhifu_pay.setBackgroundResource(R.drawable.pay_circle_corner3);
            } else {
                zhifu_pay.setEnabled(false);
                zhifu_pay.setTextColor(getResources().getColor(R.color.color_999999));
                zhifu_pay.setBackgroundResource(R.drawable.pay_ran_getcode);
            }


        }
    };
}
