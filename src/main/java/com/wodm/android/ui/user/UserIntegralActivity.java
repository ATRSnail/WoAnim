package com.wodm.android.ui.user;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.TextView;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.ui.AppActivity;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by moon1 on 2016/4/27.
 */
@Layout(R.layout.acitivity_user_integral)
public class UserIntegralActivity extends AppActivity {

    private static final String TITLE="我的积分";
    @ViewIn(R.id.integral_num)
    private TextView integral_num;
    @ViewIn(R.id.integral_rule)
    private TextView integral_rule;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle(TITLE);
        integral_rule.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getScore();
    }

    private void  getScore(){

        httpGet(Constants.URL_SCORE  + "?userId="+Constants.CURRENT_USER.getData().getAccount().getId(),new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    integral_num.setText(""+obj.getString("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
