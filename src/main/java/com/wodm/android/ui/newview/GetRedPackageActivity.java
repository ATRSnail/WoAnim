package com.wodm.android.ui.newview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;

import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.track.TrackApplication;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by songchenyu on 16/12/27.
 */

public class GetRedPackageActivity extends Activity {
    private String bg_Image;
    private String productCode;
    private ImageView img_gethot_package;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotpackage_dialog);
        Bundle bundle=getIntent().getExtras();
        if (bundle==null){
            finish();
        }
        img_gethot_package= (ImageView) findViewById(R.id.img_gethot_package);
        bg_Image=bundle.getString("image");
        productCode=bundle.getString("productCode");
        Glide.with(GetRedPackageActivity.this).load(bg_Image).into(img_gethot_package);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        findViewById(R.id.btn_get_red_package).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           httpGet(Constants.USERGETHOTPACKAGE  + Constants.CURRENT_USER.getData().getAccount().getId()+"&productCode="+productCode, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                Toast.makeText(getApplicationContext(), "礼物领取成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
                Toast.makeText(getApplicationContext(), obj.optString("message"), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
            }
        });

    }
    public void httpGet(String url, final HttpCallback callback) {

        try {
            TrackApplication.REQUEST_HEADER.put("Content-Type", "application/json");
            HttpUtil.httpGet(this, url, TrackApplication.REQUEST_HEADER, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
