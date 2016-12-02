package com.wodm.android.ui.newview;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wodm.R;
import com.wodm.android.adapter.newadapter.AtWoAdapter;
import com.wodm.android.adapter.newadapter.DianZanAdapter;
import com.wodm.android.bean.DianZanBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.activity_comment2)
public class CommentActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter,
        View.OnClickListener {
    @ViewIn(R.id.set_topbar)
    AtyTopLayout set_topbar;
    @ViewIn(R.id.choice_bottom)
    LinearLayout choice_bottom;
    @ViewIn(R.id.listView_atwo)
    ListView listView_atwo;

    private List<DianZanBean> list = new ArrayList<>();
    private DianZanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {
        adapter.setToDel(!adapter.isToDel());
        choice_bottom.setVisibility(adapter.isToDel()?View.VISIBLE:View.GONE);
        set_topbar.setTvRight(adapter.isToDel()?"删除":"编辑");
        if (!adapter.isToDel()){
            adapter.delSome();
        }
    }

    private void initViews(){
        set_topbar.setTvTitle("@我的");
        set_topbar.setOnTopbarClickListenter(this);
        adapter = new DianZanAdapter(this,list);
        listView_atwo.setAdapter(adapter);
        choice_bottom.setOnClickListener(this);
    }

    private void initDatas(){
        for (int i = 0;i<5;i++){
            list.add(new DianZanBean("小明"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choice_bottom:
                list.clear();
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
