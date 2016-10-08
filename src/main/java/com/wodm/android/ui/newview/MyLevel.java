package com.wodm.android.ui.newview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

import com.wodm.R;
import com.wodm.android.view.newview.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Layout(R.layout.activity_my_level)
public class MyLevel extends AppCompatActivity {
    @ViewIn(R.id.gv_level)
    private MyGridView gridView;
    private SimpleAdapter adapter;
    private List<Map<String, Object>> list;
    private String[] actions = new String[]{"签到", "完善个人资料", "分享漫画", "签到", "完善个人资料", "分享漫画"};
    private int[] experience = new int[]{2, 4, 3, 2, 4, 3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gridView.setAdapter(adapter);

        list = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
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
    }
}