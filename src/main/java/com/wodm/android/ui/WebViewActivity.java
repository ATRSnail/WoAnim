package com.wodm.android.ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.tools.WebViewJsInterface;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

/**
 * Created by songchenyu on 16/9/20.
 */
@Layout(R.layout.webview)
public class WebViewActivity extends AppActivity implements WebViewJsInterface.webViewCallBackListener {
    @ViewIn(R.id.webview)
    private WebView webView;
    private String adsUrl;
    private WebViewJsInterface webViewJsInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("活动");
        Bundle bundle=getIntent().getExtras();
        adsUrl=bundle.getString("adsUrl");
        if (adsUrl.equals("")){
            finish();
        }
        init();
    }

    @SuppressLint("JavascriptInterface")
    public void init() {
        webViewJsInterface=new WebViewJsInterface(this);
        webViewJsInterface.setwebViewCallBackListener(this);
        // 设置WebView属性，能够执行Javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        // 加载需要显示的网页
        webView.loadUrl(adsUrl);
        // 设置Web视图
        webView.addJavascriptInterface(webViewJsInterface,"AndroidWebView");
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        //添加客户端支持
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setBuiltInZoomControls(true); // 显示放大缩小 controler
        webView.getSettings().setSupportZoom(true); // 可以缩放
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                String isLogin="";
                if (Constants.CURRENT_USER!=null){
                    isLogin=Constants.CURRENT_USER.getData().getAccount().getId()+"";
                }
                webView.loadUrl("javascript:webViewWeatherLogon('" + isLogin + "')");
                sendInfoToJs(isLogin,5);
            }
        });
        int mDensity = metrics.densityDpi;
        if (mDensity == 120) {
            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }else if (mDensity == 160) {
            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }else if (mDensity == 240) {
            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }
//        webView.loadUrl("file:///android_asset/index.html");
//        handler.sendEmptyMessageAtTime(3,200);
        webView.loadUrl(adsUrl);
    }

    @Override
    // 设置回退
    // 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    //在java中调用js代码

    /**
     * type=1 登录和注册成功失败
     * type=2 webViewWeatherCanReceiver 是否可以分享和抽奖
     * type=3 向js上传是否登录
     * @param info
     * @param type
     */
    public void sendInfoToJs(Object info,int type) {
//        String msg = ((EditText) findViewById(R.id.input_et)).getText().toString();
        //调用js中的函数：showInfoFromJava(msg)
        Message msg=new Message();
        msg.obj=info;
        msg.what=type;
        handler.sendMessage(msg);
    }
    /*1. 是否登录成功的返回状态
    2.是否注册成功的返回状态
    3.是否分享成功的返回状态
    4.修改密码是否成功的返回状态
    5.传递给js用户id

 **/
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                webView.loadUrl("javascript:showInfoFromJava('" + msg.obj +"')");
            }else if (msg.what==2){
                webView.loadUrl("javascript:showWeatherResiger('" + msg.obj + "')");
            }else if (msg.what==3){
                webView.loadUrl("javascript:showWeatherShare('" + msg.obj + "')");
            }else if (msg.what==4){
                webView.loadUrl("javascript:showWeatherPassWord('" + msg.obj + "')");
            }else if (msg.what==5){
                webView.loadUrl("javascript:setLoginUserId('" + msg.obj + "')");
            }
        }
    };

    @Override
    public void setJsInfo(Object info,int type) {
        sendInfoToJs(info,type);
    }

    // Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
