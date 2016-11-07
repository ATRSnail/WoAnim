package com.unicom.dm.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.wodm.android.login.Wx;
import com.wodm.android.ui.Main2Activity;

import org.eteclab.Constants;
import org.eteclab.share.call.ShareResultCall;
/**
 * 微信回调类 该类包名package 为"packeage(程序包名)"+"wxapi"； 或者为“【applicationId】”+"wxapi".
 */

/**
 * 微信回调类
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());

        api = WXAPIFactory.createWXAPI(this, Constants.WX_APPKEY, false);
        api.registerApp(Constants.WX_APPKEY);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    private void handleIntent(Intent intent) {
        SendAuth.Resp resp = new SendAuth.Resp(intent.getExtras());

        if (ShareResultCall.call == null && resp.errCode == BaseResp.ErrCode.ERR_OK) {
            //用户同意
            Wx.init(getApplicationContext()).sendHttp(resp);
//            Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Main2Activity.class));
//            Toast.makeText(getApplicationContext(), "分享成功", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(getApplicationContext(), "授权失败", Toast.LENGTH_SHORT).show();
        }

        finish();
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
        finish();
    }
}