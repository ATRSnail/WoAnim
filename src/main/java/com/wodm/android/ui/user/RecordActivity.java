package com.wodm.android.ui.user;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.ComicAdapter;
import com.wodm.android.adapter.TabPagerAdapter;
import com.wodm.android.bean.ObjectBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.NoScrollViewPager;

import org.eteclab.base.annotation.InflateView;
import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.eteclab.track.annotation.TrackClick;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.activity_record)
public class RecordActivity extends AppActivity {
    @ViewIn(R.id.tab_type)
    private TabLayout mTabType;
    @ViewIn(R.id.type_pager)
    private NoScrollViewPager mTypePager;

    @InflateView(R.layout.layout_record_edit)
    private View pageOne;
    @InflateView(R.layout.layout_record_edit)
    private View pageTwo;
    private List<View> pagerViews;
    private TabPagerAdapter pagerAdapter;

    private int tid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tid = getIntent().getIntExtra("tid", 0);
        int title = getIntent().getIntExtra("title", 0);
        setTitleRight("全部清空");
        setCustomTitle(getString(title));

        pagerViews = new ArrayList<>();
        initpage(pageOne, 1);
        initpage(pageTwo, 2);
        pagerViews.add(pageOne);
        pagerViews.add(pageTwo);
        pagerAdapter = new TabPagerAdapter(pagerViews);
        mTypePager.setNoScroll(false);
        mTypePager.setAdapter(pagerAdapter);
        mTabType.setupWithViewPager(mTypePager);
        mTabType.setTabTextColors(Color.BLACK, getResources().getColor(R.color.colorPrimary));
        mTabType.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorPrimary));
        mTabType.getTabAt(0).setText("动画");
        mTabType.getTabAt(1).setText("漫画");

    }

    private void initpage(final View view, final int type) {
        PullToLoadView page = (PullToLoadView) view.findViewById(R.id.pull_lists);
        final TextView edit = (TextView) view.findViewById(R.id.edit_query);
        page.setLoadingColor(R.color.colorPrimary);
        page.setPullCallback(new PullCallbackImpl(page, new GridLayoutManager(this, 3)) {
            @Override
            protected void requestData(final int page, final boolean b) {
                String url = "";
                if (Constants.CURRENT_USER == null) {
                    return;
                }
                if (tid == R.id.ordering_records) {
                    // 订购记录

                } else if (tid == R.id.watch_records) {
                    //观看记录
                    deleteUrl = Constants.HOST + "user/deleteWatchRecord?userId=" + Constants.CURRENT_USER.getData().getAccount().getId();
                    url = Constants.HOST + "user/watchRecordByType?userId=" + Constants.CURRENT_USER.getData().getAccount().getId();
                } else if (tid == R.id.my_collcet) {
                    //我的收藏
                    deleteUrl = Constants.HOST + "user/deleteCollection?userId=" + Constants.CURRENT_USER.getData().getAccount().getId();
                    url = Constants.HOST + "user/collectByType?userId=" + Constants.CURRENT_USER.getData().getAccount().getId();
                }

                httpGet(url + "&type=" + type + "&page=" + page, new HttpCallback() {

                    @Override
                    public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthSuccess(result, obj);
                        try {
                            List<ObjectBean> beanList = new Gson().fromJson(obj.getString("data"), new TypeToken<List<ObjectBean>>() {
                            }.getType());

                            final ComicAdapter adapter = (ComicAdapter) handleData(page, beanList, ComicAdapter.class, b);
                            edit.setVisibility(adapter.getItemCount() > 0 ? View.VISIBLE : View.GONE);
                            edit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (edit.getTag().toString().equals("0")) {
                                        edit.setText("完成");
                                        edit.setTag("1");
                                        adapter.setOnItemDeleteListener(new ComicAdapter.OnItemDeleteListener() {
                                            @Override
                                            public void onItemDelete(int position, ObjectBean bean) {
                                                delete(type, adapter, position, bean);
                                            }
                                        });
                                    } else {
                                        edit.setText("编辑");
                                        edit.setTag("0");
                                        adapter.setOnItemDeleteListener(null);
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                        super.doAuthFailure(result, obj);
                        edit.setVisibility(View.GONE);
                    }
                });
            }
        });
        page.initLoad();

    }

    private String deleteUrl = "";

    private void delete(int type, final ComicAdapter adapter, final int position, ObjectBean bean) {
        httpGet(deleteUrl + "&type=" + type + "&ids=" + bean.getId(), new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                if (adapter != null) {
                    adapter.removeItem(position);
                    adapter.notifyDataSetChanged();
                } else {
                    initpage(pageOne, 1);
                    initpage(pageTwo, 2);
                }
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }
        });

    }

    private void deleteAll(final ComicAdapter adapter) {
        String url = deleteUrl;
        for (ObjectBean bean : adapter.getData()) {
            url += "&ids=" + bean.getId();
        }

        httpGet(url, new HttpCallback() {
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                adapter.setListData(new ArrayList<ObjectBean>());
                adapter.notifyDataSetChanged();
                mTypePager.getChildAt(mTypePager.getCurrentItem()).findViewById(R.id.edit_query).setVisibility(View.GONE);
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }
        });
    }

    @TrackClick(value = R.id.tooltitle_right, location = "记录", eventName = "清空")
    private void clickClean(View v) {
        PullToLoadView pullToLoadView = (PullToLoadView) mTypePager.getChildAt(mTypePager.getCurrentItem()).findViewById(R.id.pull_lists);
        ComicAdapter adapter = (ComicAdapter) pullToLoadView.getRecyclerView().getAdapter();
        if (adapter != null && adapter.getItemCount() >= 0) deleteAll(adapter);
    }

}
