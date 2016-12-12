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
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.TabFragmentAdapter;
import com.wodm.android.adapter.newadapter.MallAdapter;
import com.wodm.android.bean.MallGuaJianBean;
import com.wodm.android.fragment.newfragment.GuaJianOfMineFragment;
import com.wodm.android.fragment.newfragment.TouXiangeOfMineFragment;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.http.HttpCallback;
import org.eteclab.base.http.HttpUtil;
import org.eteclab.track.TrackApplication;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songchenyu on 16/11/16.
 */

public class AllOfMineWallActivity extends FragmentActivity implements  AtyTopLayout.myTopbarClicklistenter,GuaJianOfMineFragment.addClickIconListener,TouXiangeOfMineFragment.addClickIconListener{
    private TabLayout tabLayout;
    private AtyTopLayout set_topbar;
    private ViewPager mViewPager;
    private MallAdapter adapter;
    private List<MallGuaJianBean> newsbeanList;
    private List<MallGuaJianBean> mansbeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_guajianheaderimage);
        initView();
    }

    private Fragment guanJianFrag;
    private Fragment touXiangFrg;
    private List<String> mTitles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private TabFragmentAdapter tabFragmentAdapter;
    private MallGuaJianBean clickBean;
    private int type;

    private void initView() {
        set_topbar = (AtyTopLayout) findViewById(R.id.set_topbar);
        set_topbar.setOnTopbarClickListenter(this);
        set_topbar.setTvTitle("我的饰品");
        set_topbar.setTvRight("保存");

        //Constants.CURRENT_USER.getData().getAccount().getId()
        guanJianFrag = new GuaJianOfMineFragment();
        TouXiangeOfMineFragment.setAddClickIconListener(this);
        GuaJianOfMineFragment.setAddClickIconListener(this);
        touXiangFrg = new TouXiangeOfMineFragment();
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
    public void addImage(MallGuaJianBean mallGuaJianBean, boolean isVip, int index) {
        clickBean=mallGuaJianBean;
        type=index;
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {
        if (clickBean==null){
            return;
        }
        Intent intent= new Intent();
        intent.putExtra("MallGuaJianBean",clickBean);
        setResult(RESULT_OK,intent);
        httpGet(Constants.APP_GET_MALL_OF_USER_PENADANT + Constants.CURRENT_USER.getData().getAccount().getId() + "&productType=" + type + "&productCode=" + clickBean.getProductCode(), new HttpCallback() {

            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                try {
                    Toast.makeText(AllOfMineWallActivity.this,obj.getString("message"),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
                try {
                    Toast.makeText(AllOfMineWallActivity.this,obj.getString("message"),Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        finish();
    }


}