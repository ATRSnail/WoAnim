package com.wodm.android.ui.newview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleAdapter;

import com.wodm.R;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;
import com.wodm.android.view.newview.MyGridView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Layout(R.layout.activity_my_level)
public class MyLevelActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter {
    @ViewIn(R.id.gv_level)
    private MyGridView gridView;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> list;
    private String[] actions = new String[]{"签到", "完善个人资料", "分享漫画", "签到", "完善个人资料", "分享漫画"};
    private int[] experience = new int[]{2, 4, 3, 2, 4, 3};
    @ViewIn(R.id.back_level)
    private AtyTopLayout topLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        for (int i = 0; i < actions.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("actionItem", actions[i]);
            map.put("experience", "可得" + experience[i] + "经验值");
            list.add(map);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("actionItem", "+");
        map.put("experience", "更多内容");
        list.add(map);
        adapter = new SimpleAdapter(this, list, R.layout.items_level, new String[]{"actionItem", "experience"}, new int[]{R.id.action_item, R.id.experience_item});
        gridView.setAdapter(adapter);
        topLayout.setOnTopbarClickListenter(this);
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }
}
