package com.wodm.android.ui.newview;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.wodm.R;
import com.wodm.android.adapter.TabFragmentAdapter;
import com.wodm.android.adapter.newadapter.MallAdapter;
import com.wodm.android.bean.MallGuaJianBean;
import com.wodm.android.fragment.newfragment.AllGuaJianFragment;
import com.wodm.android.fragment.newfragment.AllTouXiangFragment;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.track.TrackApplication;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songchenyu on 16/11/16.
 */

public class GuaJianHeaderImageActivity extends FragmentActivity implements AtyTopLayout.myTopbarClicklistenter{
    private View tabLine1, tabLine2, view1, view2;
    private TabLayout tabLayout;
    private AtyTopLayout set_topbar;
    private ViewPager mViewPager;
    private MallAdapter adapter;
    private List<MallGuaJianBean> newsbeanList;
    private List<MallGuaJianBean> mansbeanList;
    private AtyTopLayout atyTopLayout;
    private String text;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent()!=null){
            text=getIntent().getStringExtra("text");
            index=getIntent().getIntExtra("index",4);
        }
        setContentView(R.layout.aty_guajianheaderimage);
        initView();
    }
    private Fragment guanJianFrag;
    private Fragment touXiangFrg;
    private List<String> mTitles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private TabFragmentAdapter tabFragmentAdapter;
    private LinearLayout mall_more1,mall_more;

    private void initView() {
        set_topbar = (AtyTopLayout) findViewById(R.id.set_topbar);
        set_topbar.setOnTopbarClickListenter(this);
        set_topbar.setTvTitle("商品");
        //Constants.CURRENT_USER.getData().getAccount().getId()
        guanJianFrag = new AllGuaJianFragment();
        touXiangFrg = new AllTouXiangFragment();
        fragments.add(touXiangFrg);
        fragments.add(guanJianFrag);

        mTitles.add("头像框");
        mTitles.add("挂件");


        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        mViewPager = (ViewPager) findViewById(R.id.mypage_pager);
        tabFragmentAdapter = new TabFragmentAdapter(fragments, mTitles, getSupportFragmentManager(), getApplicationContext());
        mViewPager.setAdapter(tabFragmentAdapter);
        mViewPager.setOffscreenPageLimit(mTitles.size());

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabsFromPagerAdapter(tabFragmentAdapter);
//        mViewPager.addOnPageChangeListener(new MyPageChangeListener());
//        frag = FragmentMyPager.newInstance(0, clickImage);
//        frag.setAddClickIconListener(this);
           if (index==4){mViewPager.setCurrentItem(0);}
           if (index==5){mViewPager.setCurrentItem(1);}
    }
    public void httpGet(String url, final HttpCallback callback) {

        try {
            TrackApplication.REQUEST_HEADER.put("Content-Type", "application/json");
            HttpUtil.httpGet(this, url, TrackApplication.REQUEST_HEADER, callback);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }

    private class TabInfo {
        private String tag;
        private Class<?> clss;
        private Bundle args;

        TabInfo(String _tag, Class<?> _class, Bundle _args) {
            tag = _tag;
            clss = _class;
            args = _args;
        }
    }

    private class DummyTabFactory implements TabHost.TabContentFactory {
        private Context mContext;

        public DummyTabFactory(Context context) {
            mContext = context;
        }

        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumHeight(0);
            v.setMinimumWidth(0);
            return v;
        }
    }

    private class TabsAdapter extends FragmentPagerAdapter
            implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
        private Context mContext;
        private TabHost mTabHost;
        private ViewPager mViewPager;
        private ArrayList<TabInfo> mTabs = new ArrayList<>();
        private List<Fragment> fragments = new ArrayList<>();

        public TabsAdapter(FragmentActivity activity, TabHost tabHost, ViewPager pager) {
            super(activity.getSupportFragmentManager());
            mContext = activity;
            mTabHost = tabHost;
            mViewPager = pager;
            mTabHost.setOnTabChangedListener(this);
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
            tabSpec.setContent(new DummyTabFactory(mContext));
            String tag = tabSpec.getTag();

            TabInfo info = new TabInfo(tag, clss, args);
            mTabs.add(info);
            mTabHost.addTab(tabSpec);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public Fragment getItem(int position) {
            TabInfo info = mTabs.get(position);
            return Fragment.instantiate(mContext, info.clss.getName(), info.args);
        }

        @Override
        public void onTabChanged(String tabId) {
            int position = mTabHost.getCurrentTab();
            mViewPager.setCurrentItem(position);
            if (position == 0) {
//                tabTv1.setText("头像框");
//                tabTv1.setTextColor(getResources().getColor(R.color.color_333333));
                tabLine1.setBackgroundColor(getResources().getColor(R.color.color_dd2e5c));

//                tabTv2.setText("挂件");
//                tabTv2.setTextColor(getResources().getColor(R.color.colorAccent));
                tabLine2.setBackgroundColor(Color.TRANSPARENT);
            } else if (position == 1) {
//                tabTv2.setText("头像框");
//                tabTv2.setTextColor(getResources().getColor(R.color.color_333333));
                tabLine2.setBackgroundColor(getResources().getColor(R.color.color_dd2e5c));

//                tabTv1.setText("挂件");
//                tabTv1.setTextColor(getResources().getColor(R.color.colorAccent));
                tabLine1.setBackgroundColor(Color.TRANSPARENT);
            }
//            String tmp=mTabHost.getCurrentTabTag();
//            mTabHost.getCurrentTabView().set
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            TabWidget widget = mTabHost.getTabWidget();
            int oldFocusability = widget.getDescendantFocusability();
            widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            mTabHost.setCurrentTab(position);
//            frag.setCurrentTabNum(position);
//            frag.setAddClickIconListener(HeaderGuaJianActivity.this);
            widget.setDescendantFocusability(oldFocusability);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
