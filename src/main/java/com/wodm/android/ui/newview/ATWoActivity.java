package com.wodm.android.ui.newview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
public class ATWoActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter,View.OnClickListener {
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
        adapter.setSet_topbar(set_topbar);
        listView_atwo.setAdapter(adapter);
        choice_bottom.setOnClickListener(this);
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {
        String text = set_topbar.getTv_right().getText().toString();
        if("完成".equals(text)){
            updateData("勾选",false,View.GONE);
        }else   if("勾选".equals(text)){
            updateData("完成",true,View.VISIBLE);
        }else   if("删除".equals(text)){
            updateData("删除",true,View.VISIBLE);
        }
    }

    public  void updateData(String string,boolean flag,int visible){
        choice_bottom.setVisibility(visible);
        set_topbar.setTvRight(string);
        adapter = new AtWoAdapter(this, flag);
        adapter.setSet_topbar(set_topbar);
        listView_atwo.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choice_bottom:
                updateData("勾选",false,View.GONE);
                break;
        }
    }
}
