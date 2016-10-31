package com.wodm.android.tools;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.lidroid.xutils.http.ResponseInfo;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.wodm.android.Constants;
import com.wodm.android.ui.AppActivity;

import org.eteclab.OnkeyShare;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.share.call.ShareResultCall;
import org.eteclab.share.ui.share.ShareWX;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by songchenyu on 16/10/26.
 */

public class WebViewJsInterface implements IWXAPIEventHandler {
    private Context mContext;
    private  webViewCallBackListener listener;
    public WebViewJsInterface(Context context){
        this.mContext=context;
    }
    @JavascriptInterface
    public boolean webViewWeatherLogon(){
        if (Constants.CURRENT_USER==null){
            return false;
        }
        return true;
    }
    public void setwebViewCallBackListener(webViewCallBackListener mListener){
        this.listener=mListener;
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        String result = "";

        if (ShareResultCall.call == null) {
            return;
        }
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "分享成功";
                ShareResultCall.call.onShareSucess();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "取消分享";
                ShareResultCall.call.onShareCancel();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "分享失败";
                ShareResultCall.call.onShareFailure(resp.errStr, resp.errCode);
                break;
            default:
                result = "分享错误" + resp.errStr;
                ShareResultCall.call.onShareError(resp.errStr, resp.errCode);
                break;
        }
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
    public void webViewShareWX(String webpageUrl,String title,String description,String thumUrl){
        ShareWX share = new ShareWX(mContext);
        share.setScene(0);
        share.shareWeb(webpageUrl,title,description,thumUrl);
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
    public void webViewForgetPassword(String phone,String password,String yzm){
        JSONObject obj = new JSONObject();
        try {
            obj.put("mobile",phone);
            obj.put("authCode", yzm);
            obj.put("password", password);
            ((AppActivity) mContext).httpPost(Constants.USER_RESET_PWD, obj, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    super.doAuthSuccess(result, obj);
                    try {
                        Toast.makeText(mContext, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void doRequestFailure(Exception exception, String msg) {
                    super.doRequestFailure(exception, msg);
                    Toast.makeText(mContext, "" + msg, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
