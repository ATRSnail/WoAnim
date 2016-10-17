package com.wodm.android.utils;

import com.google.gson.Gson;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.android.Constants;
import com.wodm.android.bean.MedalInfoBean;

import org.eteclab.base.http.HttpCallback;
import org.json.JSONObject;

import static org.eteclab.base.http.HttpUtil.httpGet;


/**
 * Created by ATRSnail on 2016/10/17.
 */

public class UpdateMedal {

    public void getMedalInfo() {
        String url = Constants.APP_GET_MEDALLIST + Constants.CURRENT_USER.getData().getAccount().getId();

    }
}
