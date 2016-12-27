package com.wodm.android.ui.newview;

import android.content.DialogInterface;
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
import com.wodm.android.tools.Tools;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.utils.DialogUtils;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;


@Layout(R.layout.activity_bind_pho)
public class BindPhoActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter, View.OnClickListener {

    @ViewIn(R.id.back_bind)
    AtyTopLayout back_bind;
    @ViewIn(R.id.phone_bind)
    EditText phone_bind;
    @ViewIn(R.id.yzm_bind)
    EditText yzm_bind;
    @ViewIn(R.id.get_yzm_bind)
    Button get_yzm_bind;
    @ViewIn(R.id.submit_bind)
    Button submit_bind;
    private Intent intent;
    private String phone = null;
    private String yzm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        back_bind.setOnTopbarClickListenter(this);
        get_yzm_bind.setOnClickListener(this);
        submit_bind.setOnClickListener(this);
        initData();
    }

    private void initData() {
        intent = new Intent();
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
            case R.id.get_yzm_bind:
                getYzm();
                break;
            case R.id.submit_bind:
                sendSubmit();
                break;
        }
    }

    private void getYzm() {
        phone = Tools.getText(phone_bind);
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "输入的内容不能为空!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (phone.length() != 11) {
                Toast.makeText(this, "请输入正确的手机号!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Tools.isMobileNO(phone)) {
                new DialogUtils.Builder(this)
                        .setMessage("对不起,您不是联通用户" + "\n" + "无法进行绑定").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
                return;
            }
        }

        String url = Constants.USER_GET_CODE + "?m=" + phone;
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
                            get_yzm_bind.setText(R.string.get_code);
                            get_yzm_bind.setTextColor(getResources().getColor(R.color.color_ffffff));
                            get_yzm_bind.setEnabled(true);
                            get_yzm_bind.setBackgroundResource(R.drawable.bind_submit);
                            timer.cancel();
                        } else {
                            get_yzm_bind.setText("还有" + i-- + "s");
                        }
                    }
                });
            }
        }, 0, 1000);
        get_yzm_bind.setEnabled(false);
        get_yzm_bind.setTextColor(getResources().getColor(R.color.color_999999));
        get_yzm_bind.setBackgroundResource(R.drawable.pay_ran_getcode);
    }

    private void sendSubmit() {
        phone = Tools.getText(phone_bind);
        yzm = Tools.getText(yzm_bind);
        if (TextUtils.isEmpty(yzm) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "输入的内容不能为空!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (!Tools.isMobileNO(phone)) {
                new DialogUtils.Builder(this)
                        .setMessage("对不起,您不是联通用户" + "\n" + "无法进行绑定").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
                return;
            }
        }
        if (Constants.CURRENT_USER == null) {
            return;
        }

        //微信绑定手机号码
        String url = Constants.BIND_WEIXIN + "?userId=" + Constants.CURRENT_USER.getData().getAccount().getId() + "&mobile=" + phone+ "&code=" + yzm;
        httpGet(url, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                intent.putExtra("state", 1);
                intent.putExtra("phone", phone);
                BindPhoActivity.this.setResult(RESULT_OK, intent);
                try {
                    if(obj.getString("code").equals("1000")){
                        new DialogUtils.Builder(BindPhoActivity.this)
                                .setMessage("恭喜你\n手机绑定成功啦").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NewVipActivity newVipActivity =new NewVipActivity();
                                newVipActivity.setPhone(phone);
                                finish();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void doRequestFailure(Exception exception, String msg) {
                super.doRequestFailure(exception, msg);
                intent.putExtra("state", 0);
                intent.putExtra("phone", "");
                BindPhoActivity.this.setResult(RESULT_CANCELED, intent);
                new DialogUtils.Builder(BindPhoActivity.this)
                        .setMessage("哦哟\n手机绑定失败啦").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
                intent.putExtra("state", 0);
                intent.putExtra("phone", "");
                BindPhoActivity.this.setResult(RESULT_CANCELED, intent);
                try {
                    if(obj.getString("code").equals("1001")){
                        String s=obj.getString("message");
                        new DialogUtils.Builder(BindPhoActivity.this)
                                .setMessage("哦哟\n"+s).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                NewVipActivity newVipActivity =new NewVipActivity();
                                newVipActivity.setPhone(phone);
                                finish();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }
}
