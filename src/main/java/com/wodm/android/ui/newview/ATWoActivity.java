package com.wodm.android.ui.newview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wodm.R;
import com.wodm.android.adapter.newadapter.AtWoAdapter;
import com.wodm.android.adapter.newadapter.SystemInformAdapter;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

@Layout(R.layout.activity_atwo)
public class ATWoActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter {
    @ViewIn(R.id.set_topbar)
    AtyTopLayout set_topbar;
    @ViewIn(R.id.choice_bottom)
    LinearLayout choice_bottom;
    @ViewIn(R.id.listView_atwo)
    ListView listView_atwo;
    AtWoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_topbar.setTvTitle("@我的");
        set_topbar.setOnTopbarClickListenter(this);
        adapter = new AtWoAdapter(this, false);
        listView_atwo.setAdapter(adapter);
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {
        choice_bottom.setVisibility(View.VISIBLE);
        set_topbar.setTvRight("完成");
        adapter = new AtWoAdapter(this, true);
        adapter.setSet_topbar(set_topbar);
        adapter.notifyDataSetChanged();
        listView_atwo.setAdapter(adapter);
    }
}
