package com.wodm.android.ui;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.bean.NewsBean;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.json.JSONException;
import org.json.JSONObject;

import static com.wodm.android.Constants.CURRENT_USER;

@Layout(R.layout.activity_news_detail)
public class NewsDetailActivity extends AppActivity {
    private static final String TITLE = "资讯详情";
    @ViewIn(R.id.dynamic_info)
    private WebView mWebInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTitle(TITLE);
        if (CURRENT_USER!=null){
            httpGet(Constants.APP_GET_WATCHNEWS + CURRENT_USER.getData().getAccount().getId() + "&taskType=2&taskValue=7", new HttpCallback() {
                @Override
                public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                }
            });
        }
        String url = Constants.URL_NEWS_GET+"?id=" + getIntent().getStringExtra("bean");

        httpGet(url, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);

                try {
                    final NewsBean bean = new Gson().fromJson(obj.getString("data"), NewsBean.class);

                    WebSettings webSettings = mWebInfo.getSettings();
                    webSettings.setJavaScriptEnabled(true);
                    webSettings.setUseWideViewPort(true);
                    webSettings.setLoadWithOverviewMode(true);
                    mWebInfo.loadUrl("file:///android_asset/news_detail.html");
                    mWebInfo.setWebViewClient(new WebViewClient() {
                        @Override
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            view.loadUrl(url);
                            return true;
                        }

                        @Override
                        public void onPageFinished(WebView view, String u) {

                            super.onPageFinished(view, u);
                            String content = bean.getContent().replace("\r\n", "<br/>");
                            mWebInfo.loadUrl("javascript:setTitle(" + "'" + bean.getTitle() + "'" + ")");
                            mWebInfo.loadUrl("javascript:setBorder(true)");
                            mWebInfo.loadUrl("javascript:setContent(" + "'" + content + "'" + ")");
                            mWebInfo.loadUrl("javascript:setSource(" + "'来源：" + bean.getSource() + "'" + ")");
                            mWebInfo.loadUrl("javascript:setPuttime(" + "'作者：" + bean.getAuthor() + "'" + ")");
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);


            }
        });


    }
}
