package com.wodm.android.ui.newview;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.TabFragmentAdapter;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.fragment.newfragment.FragmentVipPager;
import com.wodm.android.fragment.newfragment.VipFragment;
import com.wodm.android.fragment.newfragment.VvipFragment;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.track.TrackApplication;
import org.eteclab.ui.widget.CircularImage;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.wodm.android.Constants.CURRENT_USER;

/**
 * Created by songchenyu on 16/11/15.
 */

public class NewVipActivity extends FragmentActivity implements  AtyTopLayout.myTopbarClicklistenter, View.OnClickListener  {
    /**
     * Created by songchenyu on 16/10/21.
     */
    private ViewPager mViewPager;
    private AtyTopLayout set_topbar;
    private CircularImage user_head_imgs;
    private ScrollView scrollView;
    private View tabLine1, tabLine2;
    private TabLayout tabLayout;
    private LinearLayout ll_tiaokuan;
    private Button btn_open_vip;
    private boolean isOpenVip=true;
    private ImageView img_dagou;
    private ImageView img_vip_circle;
    private TextView tv_endof_vip_num;
    private TextView nick_value;
    private TextView nick_name;
    private LinearLayout ll_novip_hint,ll_vip_jiasu,ll_novip_open_vip,ll_vip_time;
    private TextView tv_score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_newvip);

        initView();
    }


    private FragmentVipPager vipPagerFragment;
    private FragmentVipPager vvipPagerFragment;
    private List<String> mTitles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private TabFragmentAdapter tabFragmentAdapter;
    private ImageView img_vip;

    private void initView() {
        tv_score= (TextView) findViewById(R.id.tv_score);
        img_vip= (ImageView) findViewById(R.id.img_vip);
        ll_novip_open_vip= (LinearLayout) findViewById(R.id.ll_novip_open_vip);
        ll_vip_time= (LinearLayout) findViewById(R.id.ll_vip_time);
        ll_novip_hint= (LinearLayout) findViewById(R.id.ll_novip_hint);
        ll_vip_jiasu= (LinearLayout) findViewById(R.id.ll_vip_jiasu);
        nick_value= (TextView) findViewById(R.id.nick_value);
        nick_name= (TextView) findViewById(R.id.nick_name);
        tv_endof_vip_num= (TextView) findViewById(R.id.tv_endof_vip_num);
        img_vip_circle= (ImageView) findViewById(R.id.img_vip_circle);
        set_topbar = (AtyTopLayout) findViewById(R.id.set_topbar);
        vipPagerFragment = new VipFragment();
        vvipPagerFragment = new VvipFragment();
        fragments.add(vvipPagerFragment);
        fragments.add(vipPagerFragment);
        mTitles.add("VIP用户");
        mTitles.add("VVIP用户");
        set_topbar.setOnTopbarClickListenter(this);
        ll_tiaokuan= (LinearLayout) findViewById(R.id.ll_tiaokuan);
        ll_tiaokuan.setOnClickListener(this);
        img_dagou= (ImageView) findViewById(R.id.img_dagou);
        btn_open_vip= (Button) findViewById(R.id.btn_open_vip);
        btn_open_vip.setOnClickListener(this);
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

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==0){
                    btn_open_vip.setText("开通VIP");
                }else {
                    btn_open_vip.setText("开通VVIP");
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setUserInfo();
    }

    private void setUserInfo(){
        httpGet(Constants.APP_GET_VIP_TIME+CURRENT_USER.getData().getAccount().getId(), new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                String day=obj.optString("data");
                tv_endof_vip_num.setText(day+"");
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }
        });
        UserInfoBean.DataBean.AccountBean accountBean=CURRENT_USER.getData().getAccount();
        int vip=accountBean.getVip();
        if (vip==0){
            ll_novip_open_vip.setVisibility(View.VISIBLE);
            ll_novip_hint.setVisibility(View.VISIBLE);
            ll_vip_time.setVisibility(View.GONE);
            ll_vip_jiasu.setVisibility(View.GONE);
        } else if (vip==1){
            ll_novip_open_vip.setVisibility(View.GONE);
            ll_novip_hint.setVisibility(View.GONE);
            ll_vip_time.setVisibility(View.VISIBLE);
            ll_vip_jiasu.setVisibility(View.VISIBLE);
            img_vip.setBackgroundResource(R.mipmap.img_vip_vip);
            img_vip_circle.setBackgroundResource(R.mipmap.vip_circle);
        }else if (vip==2){
            ll_novip_open_vip.setVisibility(View.GONE);
            ll_novip_hint.setVisibility(View.GONE);
            ll_vip_time.setVisibility(View.VISIBLE);
            ll_vip_jiasu.setVisibility(View.VISIBLE);
            img_vip.setBackgroundResource(R.mipmap.img_vip_vvip);
            img_vip_circle.setBackgroundResource(R.mipmap.vvip_circle);
        }
        tv_score.setText(accountBean.getScore()+"");
        nick_value.setText("LV."+accountBean.getGradeValue());
        nick_name.setText(accountBean.getNickName());
        new AsyncImageLoader(this, R.mipmap.default_header, R.mipmap.default_header).display(user_head_imgs, CURRENT_USER.getData().getAccount().getPortrait());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open_vip:
                    String phone = Constants.CURRENT_USER.getData().getAccount().getMobile();
                if (isOpenVip){
                    Intent intent = new Intent();
                     if(phone!=null)
                     {
                         intent.setClass(NewVipActivity.this, PayActivity.class);
                         intent.putExtra("phone",phone);
                         intent.putExtra("vip",btn_open_vip.getText());
                     }
                    else {
                         intent.setClass(NewVipActivity.this, BindPhoActivity.class);
                     }
                    startActivity(intent);
                }
            break;
            case R.id.ll_tiaokuan:
               if (isOpenVip){
                   img_dagou.setBackgroundResource(R.mipmap.vip_quxiaodagou);
                   btn_open_vip.setBackgroundResource(R.drawable.btn_open_vip);
                   isOpenVip=false;
               }else {
                   img_dagou.setBackgroundResource(R.mipmap.vip_dagou);
                   btn_open_vip.setBackgroundResource(R.drawable.btn_open_vip_click);
                   isOpenVip=true;
               }
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
