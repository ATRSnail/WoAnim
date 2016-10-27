package com.wodm.android.tools;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.android.Constants;
import com.wodm.android.ui.AppActivity;

import org.eteclab.OnkeyShare;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.share.call.ShareResultCall;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by songchenyu on 16/10/26.
 */

public class WebViewJsInterface {
    private Context mContext;
    private  webViewCallBackListener listener;
    public WebViewJsInterface(Context context){
        this.mContext=context;
    }
    public boolean webViewWeatherLogon(){
        if (Constants.CURRENT_USER==null){
            return false;
        }
        return true;
    }
    public void setwebViewCallBackListener(webViewCallBackListener mListener){
        this.listener=mListener;
    }
    public interface webViewCallBackListener{
        public void setJsInfo(Object info);
    }
    @JavascriptInterface
    public void webViewLogin(String userName,String userPassword){
        try {
            JSONObject obj = new JSONObject();
            obj.put("accountName", userName);
            obj.put("password", userPassword);
            ((AppActivity)mContext).httpPost(Constants.USER_LOGIN, obj, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    listener.setJsInfo(true);
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    try {
                        listener.setJsInfo(false);
                        Toast.makeText(mContext, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @JavascriptInterface
    public void webViewYZM(String phoneNum){
        String url = Constants.USER_GET_CODE + "?m=" + phoneNum;
        ((AppActivity)mContext).httpGet(url, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {

            }
        });

}
    @JavascriptInterface
    private void webViewshowShare() {
        OnkeyShare share = new OnkeyShare(mContext);
//        share.setTitle(bean.getName());
//        share.setDescription(bean.getDesp());
//        share.setTargUrl(Constants.SHARE_URL + resourceId);
//        share.setImageUrl(bean.getShowImage());
        share.addShareCall(new ShareResultCall() {
            @Override
            public void onShareSucess() {
                super.onShareSucess();
                Toast.makeText(mContext, "分享成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onShareCancel() {
                super.onShareCancel();
                Toast.makeText(mContext, "用户取消分享", Toast.LENGTH_SHORT).show();
            }
        });
        share.setShareType(OnkeyShare.SHARE_TYPE.SHARE_WEB);
        share.show();
    }
    @JavascriptInterface
    public void webViewResiger(String phone,String password,String yzm){

        try {
            JSONObject obj = new JSONObject();
            obj.put("accountName", phone);
            obj.put("authCode", yzm);
            obj.put("password", password);
            obj.put("osName", "android");
            obj.put("platformType",1);
            try {
                ApplicationInfo  appInfo = mContext.getPackageManager()
                        .getApplicationInfo(mContext.getPackageName(),
                                PackageManager.GET_META_DATA);
                int msg=appInfo.metaData.getInt("UMENG_CHANNEL",0);
                obj.put("channelId",msg);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            obj.put("productName","联通动漫");
            ((AppActivity)mContext).httpPost(Constants.USER_REGIST, obj, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    try {
                        listener.setJsInfo(true);
                        Toast.makeText(mContext, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    try {
                        listener.setJsInfo(false);
                        Toast.makeText(mContext, obj.getString("message"), Toast.LENGTH_SHORT).show();
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
