package com.wodm.android.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wodm.R;
import com.wodm.android.view.biaoqing.FaceRelativeLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by songchenyu on 16/5/19.
 */
public class QQBiaoQingFra extends Fragment {
    private ViewPager mViewPager;
    private LinearLayout mDotsLayout;
    private int columns = 6;
    private int rows = 4;
    private List<View> views = new ArrayList<View>();
    private List<String> staticFacesList;
    private LinearLayout chat_face_container;
    private FaceRelativeLayout faceRelativeLayout;
    private FaceRelativeLayout.BiaoQingClickListener biaoQingClickListener;

    public void setBiaoQingClickListener(FaceRelativeLayout.BiaoQingClickListener listener){
        this.biaoQingClickListener=listener;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.qq_biaoqing);
//
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.qq_biaoqing,null);
        faceRelativeLayout= (FaceRelativeLayout) view.findViewById(R.id.faceRelativeLayout);
        faceRelativeLayout.setOnCorpusSelectedListener(new FaceRelativeLayout.BiaoQingClickListener() {
            @Override
            public void deleteBiaoQing() {
                biaoQingClickListener.deleteBiaoQing();
            }

            @Override
            public void insertBiaoQing(SpannableString character) {
                biaoQingClickListener.insertBiaoQing(character);
            }
        });
        return view;
    }



}
