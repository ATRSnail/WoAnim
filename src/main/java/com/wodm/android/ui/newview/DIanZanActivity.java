package com.wodm.android.ui.newview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wodm.R;
import com.wodm.android.adapter.newadapter.DianZanAdapter;
import com.wodm.android.adapter.newadapter.SystemInformAdapter;
import com.wodm.android.bean.DianZanBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.utils.MessageUtils;
import com.wodm.android.view.newview.AtyTopLayout;
import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

import java.util.ArrayList;
import java.util.List;

@Layout(R.layout.activity_dian_zan)
public class DianZanActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter,View.OnClickListener {
    @ViewIn(R.id.set_topbar)
    AtyTopLayout set_topbar;
    @ViewIn(R.id.choice_bottom)
    LinearLayout choice_bottom;
    @ViewIn(R.id.listView_atwo)
    ListView listView_atwo;

    private List<DianZanBean> list = new ArrayList<>();
    private DianZanAdapter adapter;
    MessageUtils messageUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_topbar.setOnTopbarClickListenter(this);
        adapter = new DianZanAdapter(this, false);
        adapter.setSet_topbar(set_topbar);
        adapter.setDianZan(true);
        listView_atwo.setAdapter(adapter);
        choice_bottom.setOnClickListener(this);
        messageUtils = new MessageUtils(new SystemInformAdapter(),choice_bottom,listView_atwo,set_topbar,DianZanActivity.this);

    }

    @Override
    public void leftClick() {
       finish();
    }

    @Override
    public void rightClick() {
        String text = set_topbar.getTv_right().getText().toString();
        if("完成".equals(text)){
            messageUtils.updateData("勾选",false,View.GONE);
        }else   if("勾选".equals(text)){
            messageUtils.updateData("完成",true,View.VISIBLE);
        }else   if("删除".equals(text)){
            messageUtils.deleteMessage();
            messageUtils.updateData("删除",true,View.VISIBLE);
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choice_bottom:
                messageUtils.deleteAllMessage(5);
                messageUtils.updateData("勾选",false,View.GONE);
                break;
        }
    }
}
