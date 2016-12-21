package com.wodm.android.fragment.newfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wodm.R;

/**
 * Created by songchenyu on 16/11/15.
 */

public class FragmentVipPager extends Fragment {
    private int position=0;
    private TextView tv_speed,tv_follow_heart;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_vip, container, false);
        tv_speed= (TextView) v.findViewById(R.id.tv_speed);
        tv_follow_heart= (TextView) v.findViewById(R.id.tv_follow_heart);
        if (position==0){
            tv_speed.setText(getActivity().getResources().getText(R.string.growth_accelerate_info));
            tv_follow_heart.setText(getActivity().getResources().getText(R.string.follow_heart_info));
        }else {
            tv_speed.setText(getActivity().getResources().getText(R.string.growth_accelerate_info_vvip));
            tv_follow_heart.setText(getActivity().getResources().getText(R.string.follow_heart_info_vvip));
        }
        return v;
    }
    public void setPosition(int position){
        this.position=position;
    }




}
