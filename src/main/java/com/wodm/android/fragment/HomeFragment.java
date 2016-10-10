package com.wodm.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.HomeAdapter;
import com.wodm.android.bean.BannerBean;
import com.wodm.android.bean.ObjectDataBean;
import com.wodm.android.ui.WebViewActivity;
import com.wodm.android.ui.home.AnimDetailActivity;
import com.wodm.android.ui.home.CarDetailActivity;

import org.eteclab.base.annotation.InflateView;
import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.track.fragment.TrackFragment;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;
import org.eteclab.ui.widget.viewpager.BannerView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


@Layout(R.layout.fragment_home)
public class HomeFragment extends TrackFragment {

    @InflateView(R.layout.layout_home_header)
    private View mHeaderView;
    @ViewIn(R.id.pull_list)
    private PullToLoadView pullToLoadView;

    private BannerView mBannerView;
    public int IndexTabId = R.id.enetic_cartoon;


    @Override
    protected void setDatas(Bundle bundle) {
        mBannerView = (BannerView) mHeaderView.findViewById(R.id.banner);
        pullToLoadView.setLoadingColor(R.color.colorPrimary);
        pullToLoadView.setPullCallback(new PullCallbackImpl(pullToLoadView) {
            @Override
            protected void requestData(final int pager, final boolean follow) {
                HttpUtil.httpGet(getActivity(), Constants.URL_HOME_TYPE + "?page=" + pager + "&resourceType=" + (IndexTabId == R.id.enetic_cartoon ? 2 : 1), new HttpCallback() {
                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthFailure(result, obj);
                        loadOK();
                    }

                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        if (obj != null) {
                            try {
                                List<ObjectDataBean> beanList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<ObjectDataBean>>() {
                                }.getType());
                                handleData(pager, beanList, HomeAdapter.class, follow, mHeaderView);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });
        initTapPageView();
    }

    private void initTapPageView() {
        HttpUtil.httpGet(getActivity(), Constants.URL_HOME_TOP_LIST + "&type=" + (IndexTabId == R.id.enetic_cartoon ? 2 : 1), new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    ArrayList<BannerBean> list = new Gson().fromJson(obj.getString("data"), new TypeToken<ArrayList<BannerBean>>() {
                    }.getType());

                    List<View> bannerViews = new ArrayList<View>();
                    for (int i = 0; i < (list.size() > 5 ? 5 : list.size()); i++) {
                        ImageView v = new ImageView(getActivity());
                        v.setScaleType(ImageView.ScaleType.FIT_XY);
                        final BannerBean bean = list.get(i);
                        new AsyncImageLoader(getActivity(), R.mipmap.loading, R.mipmap.loading).display(v, bean.getImage());
                        final int pos = i;
                        v.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // 点击事件
                                try {
                                    Intent intent = new Intent();
                                    intent.putExtra("resourceId", Integer.valueOf(bean.getResourceId()).intValue());
                                    if (bean.getType().equals("1")) {
                                        intent.setClass(getActivity(), AnimDetailActivity.class);
                                    } else if (bean.getType().equals("2")) {
                                        intent.setClass(getActivity(), CarDetailActivity.class);
                                    } else if (bean.getType().equals("2")) {
                                        intent.setClass(getActivity(), CarDetailActivity.class);
                                    }else {
                                        intent.setClass(getActivity(), WebViewActivity.class);
                                    }
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        bannerViews.add(v);
                    }
                    mBannerView.setViewPagerViews(bannerViews);
                    pullToLoadView.initLoad();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void clickType(View v) {
        if (IndexTabId == v.getId()) {
            return;
        }
        IndexTabId = v.getId();
        initTapPageView();
    }

    @Override
    public void onStart() {
        mBannerView.start();
        super.onStart();
    }

    @Override
    public void onStop() {
        mBannerView.shutdown();
        super.onStop();
    }
}