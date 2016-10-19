package com.wodm.android.ui;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wodm.R;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

/**
 * Created by songchenyu on 16/9/20.
 */
@Layout(R.layout.webview)
public class WebViewActivity extends AppActivity {
    @ViewIn(R.id.webview)
    private WebView webView;
    private String adsUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        adsUrl=bundle.getString("adsUrl");
        if (adsUrl.equals("")){
            finish();
        }
        init();
    }

    public void init() {

        // 设置WebView属性，能够执行Javascript脚本

        webView.getSettings().setJavaScriptEnabled(true);
        // 加载需要显示的网页
        webView.loadUrl(adsUrl);
        // 设置Web视图
        webView.setWebViewClient(new HelloWebViewClient());
        webView.getSettings().setBuiltInZoomControls(true); // 显示放大缩小 controler
        webView.getSettings().setSupportZoom(true); // 可以缩放
        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);// 默认缩放模式
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
    // Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
