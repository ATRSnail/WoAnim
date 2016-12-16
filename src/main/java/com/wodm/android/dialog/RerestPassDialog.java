package com.wodm.android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.ui.AppActivity;

import org.eteclab.base.http.HttpCallback;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by json on 2016/5/6.
 */
public class RerestPassDialog extends Dialog {

    private EditText mOldView;
    private EditText mPassView;


    public RerestPassDialog(final Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_pass_rest);

        mOldView = (EditText) findViewById(R.id.old_password);
        mPassView = (EditText) findViewById(R.id.password);

        findViewById(R.id.btn_regist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject obj = new JSONObject();
//                accountName 用户账号 是
//                oldPassword 原始密码 是
//                newPassword 新密码 是
                if (mOldView.getText().length()==0){
                    Toast.makeText(getContext(), "请输入原始密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if (mPassView.getText().length()==0){
                    Toast.makeText(getContext(), "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if (mPassView.getText().length() < 6 || mPassView.getText().length() > 18) {
                    Toast.makeText(getContext(), "密码长度为6到18位", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    obj.put("newPassword", mPassView.getText().toString());
                    obj.put("oldPassword", mOldView.getText().toString());
                    obj.put("accountName", Constants.CURRENT_USER.getData().getAccount().getMobile());
                    ((AppActivity) context).httpPost(Constants.HOST + "user/password/modify", obj, new HttpCallback() {
                        @Override
                        public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                            super.doAuthSuccess(result, obj);
                            try {
                                Toast.makeText(getContext(), "" + obj.getString("message"), Toast.LENGTH_SHORT).show();
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

                        @Override
                        public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                            super.doAuthFailure(result, obj);
                            Toast.makeText(getContext(), "" + obj.optString("message"), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
