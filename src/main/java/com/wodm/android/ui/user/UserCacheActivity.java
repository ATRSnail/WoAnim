package com.wodm.android.ui.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.DbException;
import com.wodm.R;
import com.wodm.android.adapter.CacheAdapter;
import com.wodm.android.adapter.TabPagerAdapter;
import com.wodm.android.bean.CacheBean;
import com.wodm.android.db.dowms.CacheListDao;
import com.wodm.android.db.dowms.DowmListDao;
import com.wodm.android.service.DownLoadServices;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.utils.FileUtils;
import com.wodm.android.view.NoScrollViewPager;

import org.eteclab.base.annotation.InflateView;
import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.track.annotation.TrackClick;
import org.eteclab.ui.widget.adapter.HolderAdapter;
import org.eteclab.ui.widget.pulltorefresh.PullCallbackImpl;
import org.eteclab.ui.widget.pulltorefresh.PullToLoadView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

/**
 * Created by moon1 on 2016/5/10.
 */
@Layout(R.layout.activity_record)
public class UserCacheActivity extends AppActivity {
    private static final String TITLE = "缓存记录";

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
    private CacheListDao listDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitleRight("全部清空");
        listDao = new CacheListDao(this, CacheBean.class);
        setCustomTitle(TITLE);
        pagerViews = new ArrayList<>();

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

    private PullToLoadView initpage(View pageOne, final int type) {
        final PullToLoadView pullToLoadView = (PullToLoadView) pageOne.findViewById(R.id.pull_lists);
        pullToLoadView.setLoadingColor(R.color.colorPrimary);
        pullToLoadView.setPullCallback(new PullCallbackImpl(pullToLoadView, new GridLayoutManager(this, 3)) {
            @Override
            protected void requestData(int i, boolean b) {
                try {
                    List<CacheBean> alls = listDao.findByWhere("resourceType", "=", type + "");
                    handleData(i, new ArrayList(), CacheAdapter.class, b);
                    CacheAdapter adapter = new CacheAdapter(UserCacheActivity.this, alls == null ? new ArrayList<CacheBean>() : alls);
                    pullToLoadView.getRecyclerView().setAdapter(adapter);

                    if (adapter != null)
                        adapter.setOnItemClickListener(new HolderAdapter.OnItemClickListener<CacheBean>() {
                            @Override
                            public void onItemClick(View view, CacheBean o, int i) {
                                startActivity(new Intent(UserCacheActivity.this, UserCacheListActivity.class)
                                        .putExtra("paths", o.getPath())
                                        .putExtra("title", o.getTitle()));
                            }
                        });
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
        pullToLoadView.initLoad();
        return pullToLoadView;
    }

    @TrackClick(value = R.id.tooltitle_right, location = "记录", eventName = "清空")
    private void clickClean(View v) {
        if (mTabType.getSelectedTabPosition() == 0) {
            deleteAll("1");
        } else {
            deleteAll("2");
        }

    }

    public void deleteAll(final String type) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    List<CacheBean> alls = listDao.findByWhere("resourceType", "=", type);
                    if (alls != null) {
                        for (CacheBean index : alls) {
                            FileUtils.deleteFile(new File(index.getPath()));
                            listDao.delete(index);
                            DowmListDao.getIntence(UserCacheActivity.this).deleteByWhere("path", "LIKE", index.getPath() + "%");
                        }
                        handler.obtainMessage(Integer.valueOf(type)).sendToTarget();
                    }

                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initpage(pageOne, 1);
            initpage(pageTwo, 2);

            try {
                ArrayList lists = (ArrayList) DowmListDao.getIntence(getApplicationContext()).findByWhere("resourceType", "=", msg.what + "");
                Intent intent = new Intent(getApplicationContext(), DownLoadServices.class);
                intent.setAction("com.unicom.deleteHandler");
                intent.putExtra("data", lists);
                startService(intent);
            } catch (DbException e) {
                e.printStackTrace();
            }

        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        initpage(pageOne, 1);
        initpage(pageTwo, 2);
    }
}