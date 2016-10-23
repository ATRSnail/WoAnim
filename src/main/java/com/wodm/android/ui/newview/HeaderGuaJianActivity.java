package com.wodm.android.ui.newview;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.adapter.newadapter.FragmentMyPager;
import com.wodm.android.adapter.newadapter.GuaJianAdapter;
import com.wodm.android.bean.UserInfoBean;
import com.wodm.android.view.newview.AtyTopLayout;
import com.wodm.android.view.newview.MyGridView;

import org.eteclab.base.utils.AsyncImageLoader;
import org.eteclab.ui.widget.CircularImage;

import java.util.ArrayList;

import static com.wodm.android.Constants.CURRENT_USER;


/**
 * Created by songchenyu on 16/10/21.
 */
public class HeaderGuaJianActivity extends FragmentActivity implements FragmentMyPager.addClickIconListener,AtyTopLayout.myTopbarClicklistenter,View.OnClickListener {
/**
 * Created by songchenyu on 16/10/21.
 */
    private MyGridView guajian_free;
    private GuaJianAdapter guaJianAdapter;
    private TabHost mTabHost;
    private ViewPager mViewPager;
    private TabsAdapter mTabsAdapter;
    private TextView tabTv1,tabTv2;
    private FragmentMyPager frag;
    private AtyTopLayout set_topbar;
    private CircularImage user_head_imgs;
    private Button btn_buy_now;
    private ScrollView scrollView;
    private RelativeLayout ll_open_vip;
    private Button btn_open_vip;
    private View tabLine1,tabLine2,view1,view2;
    private String clickImage="";
    private String string[]={"花心眼睛","波特的眼睛","蓝色太阳镜","普通款式","蛤蟆镜","熊仔头像框","悟空头像框","绿帽头像框","鱼缸头像框","钻石头像框","企鹅头像框","白兔子头像框","海贼头像框","龙虾头像框","潜水艇头像框","小黄鸡头像框"};
    private int icon[]={R.mipmap.guajian_huaxin, R.mipmap.guajian_bote,R.mipmap.guajian_lanse,R.mipmap.guajian_putong,R.mipmap.guajian_hama,R.mipmap.touxiang_xiong, R.mipmap.touxiang_wukong,R.mipmap.touxiang_lvmaozi,R.mipmap.touxiang_yugang,R.mipmap.touxiang_zhuanshi,
            R.mipmap.touxiang_qie,R.mipmap.touxiang_baiduzi,R.mipmap.touxiang_haizei,R.mipmap.touxiang_longxia,R.mipmap.touxiang_qianshuiting,R.mipmap.touxiang_xiaohuangji};
    private int clckIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_headerguajian);
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            clickImage=bundle.getString("iconClick");
        }
        initClickImage(clickImage);
        initView();
    }
    private void initClickImage(String name){
        if (name==null||name.equals(""))
            return;

        for (int i = 0; i <string.length ; i++) {
            if (name.equals(string[i])){
                clckIcon=icon[i];
            }
        }

    }
    private void initView(){
        btn_open_vip= (Button) findViewById(R.id.btn_open_vip);
        btn_open_vip.setOnClickListener(this);
        btn_buy_now= (Button) findViewById(R.id.btn_buy_now);
        btn_buy_now.setOnClickListener(this);
        guajian_free= (MyGridView) findViewById(R.id.guajian_free);
        guajian_free.setSelector(new ColorDrawable(Color.TRANSPARENT));
        guaJianAdapter=new GuaJianAdapter(guajian_free,this,clickImage);
        guaJianAdapter.setAddClickIconListener(this);
        guajian_free.setAdapter(guaJianAdapter);
        mTabHost=(TabHost)findViewById(R.id.mypage_tabhost);
        mTabHost.setup();
        scrollView= (ScrollView) findViewById(R.id.scrollView);
        mViewPager=(ViewPager)findViewById(R.id.mypage_pager);
        mTabsAdapter=new TabsAdapter(this,mTabHost,mViewPager);
        ll_open_vip= (RelativeLayout) findViewById(R.id.ll_open_vip);
        view1=(View) LayoutInflater.from(this).inflate(R.layout.tabwidget_layout,null);
        tabTv1=(TextView)view1.findViewById(R.id.tabwidget_tv);
        tabLine1=(View)view1.findViewById(R.id.tabwidget_line);
        user_head_imgs= (CircularImage) findViewById(R.id.user_head_imgs);
        new AsyncImageLoader(this, R.mipmap.touxiang_moren, R.mipmap.default_header).display(user_head_imgs, CURRENT_USER.getData().getAccount().getPortrait());
        view2=(View) LayoutInflater.from(this).inflate(R.layout.tabwidget_layout,null);
        tabTv2=(TextView)view2.findViewById(R.id.tabwidget_tv);
        tabLine2=(View)view2.findViewById(R.id.tabwidget_line);
        set_topbar= (AtyTopLayout) findViewById(R.id.set_topbar);
        set_topbar.setOnTopbarClickListenter(this);
        tabTv1.setText("挂件");
        tabTv1.setTextColor(getResources().getColor(R.color.color_333333));
        tabLine1.setBackgroundColor(getResources().getColor(R.color.color_dd2e5c));

        tabTv2.setText("头像框");

        mTabsAdapter.addTab(mTabHost.newTabSpec("simple").setIndicator(view1),
                FragmentMyPager.class,null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("contacts").setIndicator(view2),
                FragmentMyPager.class,null);
        frag=FragmentMyPager.newInstance(0,clickImage);
        frag.setAddClickIconListener(this);
        if (scrollView != null) {
            scrollView.scrollTo(0, 0);
            scrollView.setFocusable(true);
            scrollView.setFocusableInTouchMode(true);
            scrollView.requestFocus();
        }
        if (CURRENT_USER!=null){
            UserInfoBean.DataBean dataBean = CURRENT_USER.getData();
            if (dataBean.getAccount().getVip() != 0) {
                ll_open_vip.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void addImage(String title,int imageRescoure) {
        user_head_imgs.setBackgroundResource(imageRescoure);
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
        switch (v.getId()){
            case R.id.btn_open_vip:
                Intent intent=new Intent(HeaderGuaJianActivity.this,VipOpenActivity.class);
                startActivity(intent);
                break;
            default:
                new AlertDialog.Builder(HeaderGuaJianActivity.this)
                        .setTitle("用户您好：")
                        .setMessage("您的积分不足！")
                        .create().show();
                break;
        }

    }
    private class TabInfo
    {
        private String tag;
        private Class<?> clss;
        private Bundle args;

        TabInfo(String _tag,Class<?> _class,Bundle _args)
        {
            tag=_tag;
            clss=_class;
            args=_args;
        }
    }
    private class DummyTabFactory implements TabHost.TabContentFactory
    {
        private Context mContext;

        public DummyTabFactory(Context context)
        {
            mContext=context;
        }

        @Override
        public View createTabContent(String tag)
        {
            View v=new View(mContext);
            v.setMinimumHeight(0);
            v.setMinimumWidth(0);
            return v;
        }
    }
    private class TabsAdapter extends FragmentPagerAdapter
            implements TabHost.OnTabChangeListener,ViewPager.OnPageChangeListener
    {
        private Context mContext;
        private TabHost mTabHost;
        private ViewPager mViewPager;
        private ArrayList<TabInfo> mTabs=new ArrayList<TabInfo>();

        public TabsAdapter(FragmentActivity activity,TabHost tabHost,ViewPager pager)
        {
            super(activity.getSupportFragmentManager());
            mContext=activity;
            mTabHost=tabHost;
            mViewPager=pager;
            mTabHost.setOnTabChangedListener(this);
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);
        }

        public void addTab(TabHost.TabSpec tabSpec,Class<?> clss,Bundle args)
        {
            tabSpec.setContent(new DummyTabFactory(mContext));
            String tag=tabSpec.getTag();

            TabInfo info=new TabInfo(tag,clss,args);
            mTabs.add(info);
            mTabHost.addTab(tabSpec);
            notifyDataSetChanged();
        }

        @Override
        public int getCount()
        {
            return mTabs.size();
        }

        @Override
        public Fragment getItem(int position)
        {
            TabInfo info=mTabs.get(position);
            return Fragment.instantiate(mContext,info.clss.getName(),info.args);
        }

        @Override
        public void onTabChanged(String tabId)
        {
            int position=mTabHost.getCurrentTab();
            mViewPager.setCurrentItem(position);
            if(position==0)
            {
//                tabTv1.setText("头像框");
//                tabTv1.setTextColor(getResources().getColor(R.color.color_333333));
                tabLine1.setBackgroundColor(getResources().getColor(R.color.color_dd2e5c));

//                tabTv2.setText("挂件");
//                tabTv2.setTextColor(getResources().getColor(R.color.colorAccent));
                tabLine2.setBackgroundColor(Color.TRANSPARENT);
            }else if(position==1)
            {
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
        public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels)
        {
        }

        @Override
        public void onPageSelected(int position)
        {
            TabWidget widget=mTabHost.getTabWidget();
            int oldFocusability=widget.getDescendantFocusability();
            widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            mTabHost.setCurrentTab(position);
            frag.setCurrentTabNum(position);
            frag.setAddClickIconListener(HeaderGuaJianActivity.this);
            widget.setDescendantFocusability(oldFocusability);
        }
        @Override
        public void onPageScrollStateChanged(int state)
        {

        }
    }

}
