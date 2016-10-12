package com.wodm.android.ui.newview;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import com.wodm.R;
import com.wodm.android.adapter.newadapter.LevelAdapter;
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
    private List<Map<String, String>> list;
    private String[] actions = new String[]{"签到", "观看动漫", "发布评论", "每日登陆", "完善资料", "搜索动漫", "查看新闻", "+"};
    @ViewIn(R.id.back_level)
    private AtyTopLayout topLayout;
    LevelAdapter adapter;
    @ViewIn(R.id.activity_my_level)
    private ScrollView activity_my_level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        for (int i = 0; i < actions.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("actionItem", actions[i]);
            if (i == (actions.length - 1)) {
                map.put("experience", "添加更多");
            } else {
                map.put("experience", "可得2经验值");
            }

            list.add(map);
        }
        adapter = new LevelAdapter(this, list);

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

    @Override
    protected void onResume() {
        super.onResume();
        if (activity_my_level != null) {
            activity_my_level.scrollTo(0, 0);
            activity_my_level.setFocusable(true);
            activity_my_level.setFocusableInTouchMode(true);
            activity_my_level.requestFocus();
        }
    }

}
