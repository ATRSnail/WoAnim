package com.wodm.android.ui;

import android.annotation.SuppressLint;
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
        int mDensity = metrics.densityDpi;
        if (mDensity == 120) {
            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        }else if (mDensity == 160) {
            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }else if (mDensity == 240) {
            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        }
        webView.loadUrl("file:///android_asset/index.html");
//        webView.loadUrl(adsUrl);
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
    public void sendInfoToJs(Object info) {
//        String msg = ((EditText) findViewById(R.id.input_et)).getText().toString();
        //调用js中的函数：showInfoFromJava(msg)
        Message msg=new Message();
        msg.obj=info;
        handler.sendMessage(msg);
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            webView.loadUrl("javascript:showInfoFromJava('" + msg.obj + "')");
        }
    };

    @Override
    public void setJsInfo(Object info) {
        sendInfoToJs(info);
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
