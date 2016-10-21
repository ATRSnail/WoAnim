package com.wodm.android.adapter.newadapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wodm.R;

/**
 * Created by songchenyu on 16/10/21.
 */


public class FragmentMyPager extends Fragment
{
    int mNum;

    public static FragmentMyPager newInstance(int num)
    {
        FragmentMyPager f=new FragmentMyPager();

        Bundle args=new Bundle();
        args.putInt("num",num);
        f.setArguments(args);
        return f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mNum=getArguments()!=null?getArguments().getInt("num"):1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.fragment_mypage,container,false);
        View tv=v.findViewById(R.id.text);
        ((TextView)tv).setText("Fragment # "+mNum);
        ((TextView)tv).setTextColor(getResources().getColor(R.color.colorAccent));
        return v;
    }
}

