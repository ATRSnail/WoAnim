package com.wodm.android.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by moon1 on 2016/2/17.
 */
public class TabPagerAdapter extends PagerAdapter {
    private List<View> pagerViews;

    public TabPagerAdapter(List<View> pagerViews) {
        this.pagerViews = pagerViews;
    }

    @Override
    public int getCount() {
        return pagerViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View frameLayout = pagerViews.get(position);
        container.addView(frameLayout);
        return frameLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(pagerViews.get(position));
    }
}
