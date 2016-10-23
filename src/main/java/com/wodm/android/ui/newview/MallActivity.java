package com.wodm.android.ui.newview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
public class MallActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter, View.OnClickListener, AdapterView.OnItemClickListener {

    @ViewIn(R.id.ll_buy_mall)
    LinearLayout ll_buy_mall;
    @ViewIn(R.id.new_grid_mall)
    MyGridView new_grid_mall;
    @ViewIn(R.id.renqi_grid_mall)
    MyGridView renqi_grid_mall;
    @ViewIn(R.id.back_mall)
    AtyTopLayout back_mall;
    @ViewIn(R.id.go_btn)
    ImageButton go_btn;

    String[] buyName = new String[]{"会员", "头像框", "挂件", "周边"};
    String[] name = new String[]{"钻石头像框", "狗熊头像框", "小黄鸡头像框"};
    String[] score = new String[]{"930积分", "930积分", "930积分"};
    int[] icon = new int[]{R.mipmap.diamond, R.mipmap.bear, R.mipmap.ji};
    int[] icon2 = new int[]{R.mipmap.rabbit, R.mipmap.wukong, R.mipmap.hat};
    int[] color = new int[]{R.drawable.buy_rectangle_mall1, R.drawable.buy_rectangle_mall, R.drawable.buy_rectangle_mall3, R.drawable.buy_rectangle_mall4};
    int[] colors = new int[]{R.mipmap.member_mall, R.mipmap.touxiang_mall, R.mipmap.guajian_mall, R.mipmap.zhoubian_mall};
    MallAdapter adapter;
    List<Map<String, Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        renqi_grid_mall.setSelector(new ColorDrawable(Color.TRANSPARENT));
        initLinearLayout();
        initList();
        back_mall.setOnTopbarClickListenter(this);
        back_mall.setRightIsVisible(false);
        back_mall.setLeftImageMargin(30);
        back_mall.setRightImageMargin(30);

        go_btn.setOnClickListener(this);
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
        adapter = new MallAdapter(this, list, 0);
        new_grid_mall.setAdapter(adapter);
        new_grid_mall.setOnItemClickListener(this);
        adapter = new MallAdapter(this, list, 1);
        renqi_grid_mall.setAdapter(adapter);
        renqi_grid_mall.setOnItemClickListener(this);
    }

    private void initLinearLayout() {
        for (int i = 0; i < buyName.length; i++) {
            LinearLayout view = (LinearLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.buy_item_mall, null, false);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
            view.setLayoutParams(params);
            ImageView buy_colcor_mall;
            TextView buy_font_mall;
            buy_colcor_mall = (ImageView) view.findViewById(R.id.buy_colcor_mall);
            buy_font_mall = (TextView) view.findViewById(R.id.buy_font_mall);
            buy_font_mall.setText(buyName[i]);
            buy_colcor_mall.setBackgroundResource(color[i]);
            view.setClickable(true);
            view.setFocusable(true);
            String name = buyName[i];
            if (name.equals("周边")) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(MallActivity.this)
                                .setMessage("正在建设中...")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create().show();
                    }
                });
            } else {
                Class atyClass = null;
                if (name.equals("会员")) {
                    atyClass = VipOpenActivity.class;
                } else {
                    atyClass = HeaderGuaJianActivity.class;
                }
                final Class finalAtyClass = atyClass;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (finalAtyClass == null)
                            return;
                        Intent intent = new Intent(MallActivity.this, finalAtyClass);
                        startActivity(intent);
                    }
                });
            }
            buy_colcor_mall.setBackgroundResource(colors[i]);
            view.setId(i);
            ll_buy_mall.addView(view);
        }
    }


    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }

    private void initDialog() {
        new AlertDialog.Builder(MallActivity.this)
                .setTitle("用户您好：")
                .setMessage("您的积分不足！")
                .create().show();
    }

    @Override
    public void onClick(View v) {
//        Toast.makeText(MallActivity.this,"lf,gfllfdfdb,lfbld,fd",Toast.LENGTH_LONG);
        switch (v.getId()) {
            case 0:
                break;
            case 1:
//                startActivity(new Intent(this, HeaderGuaJianActivity.class));
                break;
            case 2:
//                startActivity(new Intent(this, HeaderGuaJianActivity.class));
                break;
            case 3:
                new AlertDialog.Builder(this)
                        .setMessage("正在建设中...")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
                break;
            case R.id.go_btn:
                new AlertDialog.Builder(this)
                        .setMessage("您的积分不足")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(MallActivity.this, HeaderGuaJianActivity.class);
        String nameClick = name[position];
        intent.putExtra("iconClick", nameClick);
        startActivity(intent);

    }
}