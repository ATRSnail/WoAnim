package com.wodm.android.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.NewsAdapter;
import com.wodm.android.adapter.TabPagerAdapter;
import com.wodm.android.bean.NewsBean;
import com.wodm.android.bean.NewsColumBean;
import com.wodm.android.ui.NewsDetailActivity;
import com.wodm.android.view.NoScrollViewPager;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.track.fragment.TrackFragment;
import org.eteclab.ui.widget.adapter.HolderAdapter;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


@Layout(R.layout.fragment_recom)
public class RecomFragment extends TrackFragment {
    @ViewIn(R.id.tab_type)
    private TabLayout mTabType;
    @ViewIn(R.id.type_pager)
    private NoScrollViewPager mTypePager;

    private List<View> pagerViews;
    private TabPagerAdapter pagerAdapter;

    @Override
    protected void setDatas(Bundle bundle) {
        pagerViews = new ArrayList<>();
        HttpUtil.httpGet(getActivity(), Constants.GET_NEWS_CATEGORY + "?location=1", new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    List<NewsColumBean> lists = new Gson().fromJson(obj.getString("data"), new TypeToken<List<NewsColumBean>>() {
                    }.getType());
                    setTabType(lists);
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

    private void setTabType(List<NewsColumBean> lists) {
        pagerViews.clear();
        for (int index = 0; index < lists.size(); index++) {
            NewsColumBean bean = lists.get(index);
            PullToLoadView pageOne = new PullToLoadView(getActivity());
            initpage(pageOne, bean.getId());
            pagerViews.add(pageOne);
        }
        mTabType.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabType.setTabTextColors(Color.BLACK, getResources().getColor(R.color.colorPrimary));
        mTabType.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        mTypePager.setNoScroll(false);
        pagerAdapter = new TabPagerAdapter(pagerViews);
        mTypePager.setAdapter(pagerAdapter);
        mTabType.setupWithViewPager(mTypePager);
        for (int index = 0; index < lists.size(); index++) {
            NewsColumBean bean = lists.get(index);
            mTabType.getTabAt(index).setText(bean.getName());
        }
    }

    private void initpage(PullToLoadView pageOne, final int type) {
        pageOne.setLoadingColor(R.color.colorPrimary);
        pageOne.setPullCallback(new PullCallbackImpl(pageOne) {
            @Override
            protected void requestData(final int page, final boolean b) {
                HttpUtil.httpGet(getActivity(), Constants.URL_GUANGCHANG_LIST + type + "&page=" + page, new HttpCallback() {
                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        try {
                            List<NewsBean> newsBeanList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<NewsBean>>() {
                            }.getType());
                            NewsAdapter adapter = (NewsAdapter) handleData(page, newsBeanList, NewsAdapter.class, b);
                            adapter.setOnItemClickListener(new HolderAdapter.OnItemClickListener<NewsBean>() {
                                @Override
                                public void onItemClick(View view, NewsBean newsBean, int i) {
                                    startActivity(new Intent(getActivity(), NewsDetailActivity.class).putExtra("bean", newsBean.getId()+""));
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
        });
        pageOne.initLoad();
    }
}
