package com.wodm.android.ui.newview;

import android.content.Context;
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
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.wodm.R;
import com.wodm.android.adapter.newadapter.FragmentMyPager;
import com.wodm.android.adapter.newadapter.GuaJianAdapter;
import com.wodm.android.view.newview.MyGridView;

import java.util.ArrayList;


/**
 * Created by songchenyu on 16/10/21.
 */
public class HeaderGuaJianActivity extends FragmentActivity implements FragmentMyPager.addClickIconListener{
    private MyGridView guajian_free;
    private GuaJianAdapter guaJianAdapter;
    private TabHost mTabHost;
    private ViewPager mViewPager;
    private TabsAdapter mTabsAdapter;
    private TextView tabTv1,tabTv2;
    private View tabLine1,tabLine2,view1,view2;
    private  FragmentMyPager frag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_headerguajian);
        initView();
    }
    private void initView(){
        guajian_free= (MyGridView) findViewById(R.id.guajian_free);
        guajian_free.setSelector(new ColorDrawable(Color.TRANSPARENT));
        guaJianAdapter=new GuaJianAdapter(guajian_free,this);
        guajian_free.setAdapter(guaJianAdapter);
        mTabHost=(TabHost)findViewById(R.id.mypage_tabhost);
        mTabHost.setup();

        mViewPager=(ViewPager)findViewById(R.id.mypage_pager);
        mTabsAdapter=new TabsAdapter(this,mTabHost,mViewPager);

        view1=(View) LayoutInflater.from(this).inflate(R.layout.tabwidget_layout,null);
        tabTv1=(TextView)view1.findViewById(R.id.tabwidget_tv);
        tabLine1=(View)view1.findViewById(R.id.tabwidget_line);

        view2=(View) LayoutInflater.from(this).inflate(R.layout.tabwidget_layout,null);
        tabTv2=(TextView)view2.findViewById(R.id.tabwidget_tv);
        tabLine2=(View)view2.findViewById(R.id.tabwidget_line);

        tabTv1.setText("头像框");
        tabTv1.setTextColor(getResources().getColor(R.color.color_333333));
        tabLine1.setBackgroundColor(getResources().getColor(R.color.color_dd2e5c));

        tabTv2.setText("挂件");

        mTabsAdapter.addTab(mTabHost.newTabSpec("simple").setIndicator(view1),
                FragmentMyPager.class,null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("contacts").setIndicator(view2),
                FragmentMyPager.class,null);
        frag=FragmentMyPager.newInstance(0);
        frag.setAddClickIconListener(this);
    }

    @Override
    public void addImage(String title,int imageRescoure) {

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
            widget.setDescendantFocusability(oldFocusability);
        }
        @Override
        public void onPageScrollStateChanged(int state)
        {

        }
    }

}
