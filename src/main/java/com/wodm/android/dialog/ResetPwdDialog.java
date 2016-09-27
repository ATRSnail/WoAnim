package com.wodm.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.ui.AppActivity;

import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.utils.CommonUtil;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by json on 2016/5/6.
 */
public class ResetPwdDialog extends Dialog {

    private EditText mPhoneView;
    private EditText mPassView;
    private TextView mGetCode;
    private EditText mCode;


    public ResetPwdDialog(final Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_reset_pwd);

        mPhoneView = (EditText) findViewById(R.id.phone_num);
        mPassView = (EditText) findViewById(R.id.password);
        mGetCode= (TextView) findViewById(R.id.get_code);
        mCode= (EditText) findViewById(R.id.code);

        findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject obj = new JSONObject();
                try {
                    if (mPassView.getText().length() < 6 || mPassView.getText().length() > 18) {
                        Toast.makeText(getContext(), "密码长度为6到18位", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    obj.put("mobile", mPhoneView.getText().toString());
                    obj.put("authCode", mCode.getText().toString());
                    obj.put("password", mPassView.getText().toString());
                    ((AppActivity) context).httpPost(Constants.USER_RESET_PWD, obj, new HttpCallback() {
                        @Override
                        public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                            super.doAuthSuccess(result, obj);
                            try {
                                Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void doRequestFailure(Exception exception, String msg) {
                            super.doRequestFailure(exception, msg);
                            Toast.makeText(getContext(), "" + msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        mGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNum = mPhoneView.getText().toString();
                if ("".equals(phoneNum) || !CommonUtil.isMobileNO(phoneNum)) {
                    Toast.makeText(context,"请输入正确手机号",Toast.LENGTH_SHORT).show();
                    return;
                }
                String url = Constants.USER_GET_CODE + "?m=" + phoneNum;
                ((AppActivity)context).httpGet(url, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
//                        try {
//                            mCode.setText(obj.getString("authCode"));
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
                        ((AppActivity)context).runOnUiThread(new Runnable() {
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
    }
}