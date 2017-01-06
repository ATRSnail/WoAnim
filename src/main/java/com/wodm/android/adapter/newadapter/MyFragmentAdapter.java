package com.wodm.android.adapter.newadapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

/**
 * Created by ATRSnail on 2016/12/30.
 * 动漫画详情页面 下方 目录、评论的适配
 */

public class MyFragmentAdapter extends FragmentPagerAdapter{

    private List<String> titles;
    private Context context;
    private List<Fragment> fragments;
    public MyFragmentAdapter(List<Fragment> fragments, List<String> titles, FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
        this.titles = titles;
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
