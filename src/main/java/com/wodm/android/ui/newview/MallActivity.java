package com.wodm.android.ui.newview;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wodm.R;
import com.wodm.android.adapter.newadapter.MallAdapter;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;
import com.wodm.android.view.newview.MyGridView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Layout(R.layout.activity_mall)
public class MallActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter, View.OnClickListener {

    @ViewIn(R.id.ll_buy_mall)
    LinearLayout ll_buy_mall;
    @ViewIn(R.id.new_grid_mall)
    MyGridView new_grid_mall;
    @ViewIn(R.id.renqi_grid_mall)
    MyGridView renqi_grid_mall;
    @ViewIn(R.id.back_mall)
    AtyTopLayout back_mall;

    String[] buyName = new String[]{"会员", "头像框", "挂件", "周边"};
    String[] name = new String[]{"钻石头像框", "狗熊头像框", "小黄鸡头像框"};
    String[] score = new String[]{"930积分", "930积分", "930积分"};
    int[] icon = new int[]{R.mipmap.diamond, R.mipmap.bear, R.mipmap.ji};
    int[] color = new int[]{R.drawable.buy_rectangle_mall1, R.drawable.buy_rectangle_mall, R.drawable.buy_rectangle_mall3, R.drawable.buy_rectangle_mall4};
    MallAdapter adapter;
    List<Map<String, Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLinearLayout();
        initList();
        back_mall.setOnTopbarClickListenter(this);
    }

    private void initList() {
        list = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", name[i]);
            map.put("score", score[i]);
            map.put("icon", icon[i]);
            list.add(map);
        }
        adapter = new MallAdapter(getApplicationContext(), list);
        new_grid_mall.setAdapter(adapter);
        renqi_grid_mall.setAdapter(adapter);
    }

    private void initLinearLayout() {
        for (int i = 0; i < buyName.length; i++) {
            LinearLayout view = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.buy_item_mall, null, false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            view.setLayoutParams(params);
            TextView buy_colcor_mall;
            TextView buy_font_mall;
            buy_colcor_mall = (TextView) view.findViewById(R.id.buy_colcor_mall);
            buy_font_mall = (TextView) view.findViewById(R.id.buy_font_mall);
            buy_font_mall.setText(buyName[i]);
            buy_colcor_mall.setBackgroundResource(color[i]);
//            view.setClickable(true);
//            view.setFocusable(true);
//            view.setOnClickListener(this);
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                    builder.setTitle("用户您好：");
//                    builder.setMessage("您的积分不足！");
//                    builder.create().show();
//                }
//            });
            ll_buy_mall.addView(view);
        }


    }


    @Override
    public void leftClick() {
        back_mall.setLeftImageResource(R.mipmap.back_mall_white);
        finish();
    }

    @Override
    public void rightClick() {

    }

    @Override
    public void onClick(View v) {

    }
}
