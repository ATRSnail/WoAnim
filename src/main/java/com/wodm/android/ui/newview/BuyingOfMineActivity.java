package com.wodm.android.ui.newview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.wodm.R;
import com.wodm.android.adapter.TabFragmentAdapter;
import com.wodm.android.fragment.newfragment.FragmentVipPager;
import com.wodm.android.fragment.newfragment.VipFragment;
import com.wodm.android.fragment.newfragment.VvipFragment;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.ui.widget.CircularImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songchenyu on 16/11/16.
 */

public class BuyingOfMineActivity extends FragmentActivity implements  AtyTopLayout.myTopbarClicklistenter, View.OnClickListener  {
    /**
     * Created by songchenyu on 16/10/21.
     */
    private ViewPager mViewPager;
    private AtyTopLayout set_topbar;
    private CircularImage user_head_imgs;
    private ScrollView scrollView;
    private View tabLine1, tabLine2;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_buying_of_mine);

        initView();
    }


    private FragmentVipPager vipPagerFragment;
    private FragmentVipPager vvipPagerFragment;
    private List<String> mTitles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private TabFragmentAdapter tabFragmentAdapter;

    private void initView() {
        set_topbar = (AtyTopLayout) findViewById(R.id.set_topbar);
        vipPagerFragment = new VipFragment();
        vvipPagerFragment = new VvipFragment();
        fragments.add(vvipPagerFragment);
        fragments.add(vipPagerFragment);
        mTitles.add("VIP用户");
        mTitles.add("VVIP用户");

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        user_head_imgs = (CircularImage) findViewById(R.id.img_user_header);
//        if(CURRENT_USER.getData().getAccount().getPortrait()!=null)
//            new AsyncImageLoader(this, R.mipmap.touxiang_moren, R.mipmap.default_header).display(user_head_imgs, CURRENT_USER.getData().getAccount().getPortrait());
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        mViewPager = (ViewPager) findViewById(R.id.mypage_pager);
        tabFragmentAdapter = new TabFragmentAdapter(fragments, mTitles, getSupportFragmentManager(), getApplicationContext());
        mViewPager.setAdapter(tabFragmentAdapter);
        mViewPager.setOffscreenPageLimit(mTitles.size());

        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabsFromPagerAdapter(tabFragmentAdapter);
        if (scrollView != null) {
            scrollView.scrollTo(0, 0);
            scrollView.setFocusable(true);
            scrollView.setFocusableInTouchMode(true);
            scrollView.requestFocus();
        }

    }


    @Override
    public void leftClick() {
        finish();

    }

    @Override
    public void rightClick() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open_vip:
                Intent intent = new Intent(BuyingOfMineActivity.this, NewVipActivity.class);
                startActivity(intent);
                break;
            default:
                new AlertDialog.Builder(BuyingOfMineActivity.this)
                        .setTitle("用户您好：")
                        .setMessage("您的积分不足！")
                        .create().show();
                break;
        }

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
