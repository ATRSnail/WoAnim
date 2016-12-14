package com.wodm.android.ui.user;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.ui.AppActivity;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.track.annotation.TrackClick;
import org.json.JSONException;
import org.json.JSONObject;

@Layout(R.layout.activity_feek_back)
public class FeekBackActivity extends AppActivity {
    private final static String TITLE = "意见反馈";
    @ViewIn(R.id.content)
    private EditText mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle(TITLE);
        setCustomTitleColor(Color.parseColor("#ffffff"));
        setTitleRight("提交");
        if(Constants.CURRENT_USER==null){
            Toast.makeText(this,"请先登录",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
    }

    @TrackClick(value = R.id.tooltitle_right, location = TITLE, eventName = "提交")
    public void btnSubmit(View view) {

        if (mContent.getText().toString().equals("")) {
            Toast.makeText(this, "提交信息为空", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = Constants.URL_FEEDBACK;
        JSONObject obj = new JSONObject();
        try {
            obj.put("userId",Constants.CURRENT_USER.getData().getAccount().getId());
            obj.put("content",mContent.getText().toString());
            obj.put("opinType","1");
            httpPost(url,obj,new HttpCallback(){
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    try {
                        Toast.makeText(FeekBackActivity.this,obj.getString("message"),Toast.LENGTH_SHORT).show();
                        mContent.setText("");
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
