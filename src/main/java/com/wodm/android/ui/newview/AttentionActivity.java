package com.wodm.android.ui.newview;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.FansAdapter;
import com.wodm.android.adapter.newadapter.FollowAdapter;
import com.wodm.android.bean.FansBean;
import com.wodm.android.bean.FollowBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.activity_attention)
public class AttentionActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter {

    @ViewIn(R.id.set_topbar)
    AtyTopLayout set_topbar;
    @ViewIn(R.id.tablayout)
    TabLayout tablayout;
    @ViewIn(R.id.viewPager)
    ViewPager viewPager;
    String[] titles;
    List<View> views;
    MyPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("AA","----------------"+getIntent().getIntExtra("fans",0));

        set_topbar.setOnTopbarClickListenter(this);
        titles = new String[]{"我的关注", "粉丝数"};
        views = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            getData((i+1));
        }

        adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
        if(getIntent().getIntExtra("fans",0)==1){
            viewPager.setCurrentItem(1);
        }
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//             if(position==0) {
//                 new FollowAdapter(0);}
//                else {
//                 new FansAdapter(1);
//             }
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

    }

    private void getData(final int type) {
        View view = LayoutInflater.from(AttentionActivity.this).inflate(R.layout.noscro, null);
        final ListView noscroll = (ListView) view.findViewById(R.id.noscroll);
        String url =Constants.GET_USER_ATTENTION+Constants.CURRENT_USER.getData().getAccount().getId()+"&type="+type;
        httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);

                if(type==1){
                    FollowBean bean = new Gson().fromJson(obj.toString(),FollowBean.class);
                    noscroll.setAdapter(new FollowAdapter(AttentionActivity.this,bean.getData(),type));
                } else {
                    FansBean bean = new Gson().fromJson(obj.toString(),FansBean.class);
                    noscroll.setAdapter(new FansAdapter(AttentionActivity.this,bean.getData(),type));
                }


            }

            @Override
            public void doRequestFailure(Exception exception, String msg) {
                super.doRequestFailure(exception, msg);
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }
        });
        views.add(view);
    }

    public class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View frameLayout = views.get(position);
            container.addView(frameLayout);
            return frameLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView( views.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }
}
