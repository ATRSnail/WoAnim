package com.wodm.android.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.lidroid.xutils.http.ResponseInfo;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.umeng.analytics.MobclickAgent;
import com.wodm.android.Constants;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.login.Wx;
import com.wodm.android.ui.AppActivity;

import org.eteclab.OnkeyShare;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.share.call.ShareResultCall;
import org.eteclab.share.ui.share.ShareWX;
import org.json.JSONException;
import org.json.JSONObject;

import static com.wodm.android.Constants.CURRENT_USER;

/**
 * Created by songchenyu on 16/10/26.
 */

public class WebViewJsInterface implements IWXAPIEventHandler {
    private Context mContext;
    private  webViewCallBackListener listener;
    public WebViewJsInterface(Context context){
        this.mContext=context;
    }
    private String title="";
    private String description="";
    private int userId;
    private adapterReceiver adapterReceiver;
//    @JavascriptInterface
//    public void webViewWeatherLogon(){
//        if (Constants.CURRENT_USER==null){
//            listener.setJsInfo(false);
//        }
//        listener.setJsInfo(true);
//    }
//    @JavascriptInterface
//    public void webViewUserId(){
//        if (Constants.CURRENT_USER!=null){
//            listener.setJsInfo(Constants.CURRENT_USER.getData().getAccount().getId());
//        }
//    }
    public void setwebViewCallBackListener(webViewCallBackListener mListener){
        this.listener=mListener;
    }
    @JavascriptInterface
    public void webViewWeatherCanReceiver(String userId){
        String url = Constants.APP_GET_WEATHRE_ISRECEIVER + "" +userId;
        ((AppActivity)mContext).httpGet(url, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                listener.setJsInfo(true,2);
            }
        });
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
                listener.setJsInfo(true,3);
                try {
                    JSONObject jsonObject=new JSONObject();
                    long userId = CURRENT_USER.getData().getAccount().getId();
                    jsonObject.put("userId",userId);
                    jsonObject.put("stype",2);
                    jsonObject.put("sname",title);
                    jsonObject.put("tochannel",1);
                    jsonObject.put("description",description);
                    ((AppActivity)mContext).httpPost(Constants.APP_GET_SHARE,jsonObject, new HttpCallback() {
                        @Override
                        public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ShareResultCall.call.onShareSucess();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "取消分享";
                listener.setJsInfo(false,3);
                ShareResultCall.call.onShareCancel();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "分享失败";
                listener.setJsInfo(false,3);
                ((AppActivity)mContext).httpGet(Constants.APP_GET_SHARE, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {

                    }
                });
                ShareResultCall.call.onShareFailure(resp.errStr, resp.errCode);
                break;
            default:
                listener.setJsInfo(false,3);
                result = "分享错误" + resp.errStr;
                ShareResultCall.call.onShareError(resp.errStr, resp.errCode);
                break;
        }
    }
    public void destoryReceiver(){
        if (adapterReceiver!=null){
            mContext.unregisterReceiver(adapterReceiver);
        }
    }

    public interface webViewCallBackListener{
        public void setJsInfo(Object info,int type);
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
                    try {
                        UserInfoBean bean = new UserInfoBean();
                        UserInfoBean.DataBean dataBean = new UserInfoBean.DataBean();
                        UserInfoBean.DataBean.AccountBean accountBean = new UserInfoBean.DataBean.AccountBean();
                        dataBean.setToken(obj.getString("token"));
                        accountBean.setId(obj.getInt("userId"));
                        accountBean.setType(obj.getInt("type"));
                        dataBean.setAccount(accountBean);
                        bean.setData(dataBean);
                        int id=obj.getInt("userId");
                        userId=id;
//                        listener.setJsInfo(userId,1);
                        listener.setJsInfo(userId,5);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    try {
                        listener.setJsInfo(false,1);
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
    public void webViewLoginWX(){
        Wx.init(mContext).sendAuthRequest();
        if (!TextUtils.isEmpty(getUserId())) {
            MobclickAgent.onProfileSignIn("WX", getUserId());//统计微信登录
        }
        adapterReceiver=new adapterReceiver();
        IntentFilter intentFilter = new IntentFilter("com.adapter.intent.SHARE_NOTIFICATION");
        mContext.registerReceiver(adapterReceiver, intentFilter);
    }
    private String LOGIN_ACTION="com.intent.startWebViewWXlogin";
    public class adapterReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(LOGIN_ACTION)){
                String userID = String.valueOf(Constants.CURRENT_USER.getData().getAccount().getId());
//                listener.setJsInfo(userId,1);
                listener.setJsInfo(userId,5);
            }
        }
    }
    private String getUserId() {
        if (Constants.CURRENT_USER == null)
            return null;
        String userID = String.valueOf(Constants.CURRENT_USER.getData().getAccount().getId());
        return userID;
    }
    @JavascriptInterface
    public void webViewShareWX(String webpageUrl,String title,String description,String thumUrl){
        listener.setJsInfo(true,3);
        try {
            JSONObject jsonObject=new JSONObject();
            if (CURRENT_USER!=null){
                userId = CURRENT_USER.getData().getAccount().getId();
            }
            jsonObject.put("userId",userId);
            jsonObject.put("stype",2);
            jsonObject.put("sname",title);
            jsonObject.put("tochannel",1);
            jsonObject.put("description",description);
            Log.e("SCY"," - -  -- -  "+userId);
            ((AppActivity)mContext).httpPost(Constants.APP_GET_SHARE,jsonObject, new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                    Log.e("SCY"," - - - -"+obj.toString());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
                        listener.setJsInfo(true,4);
                        Toast.makeText(mContext, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void doRequestFailure(Exception exception, String msg) {
                    super.doRequestFailure(exception, msg);
                    listener.setJsInfo(false,4);
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
                        listener.setJsInfo(true,2);
                        Toast.makeText(mContext, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                    try {
                        listener.setJsInfo(false,2);
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
