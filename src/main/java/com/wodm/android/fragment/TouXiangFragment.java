package com.wodm.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wodm.android.adapter.newadapter.FragmentMyPager;

/**
 * @author xch
 * @version 1.0
 * @create_date 16/11/1
 */
public class TouXiangFragment extends FragmentMyPager{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setmNum(1);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
