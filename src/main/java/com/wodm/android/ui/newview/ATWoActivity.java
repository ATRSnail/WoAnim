package com.wodm.android.ui.newview;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.AtWoAdapter;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.utils.MessageUtils;
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
    MessageUtils messageUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_topbar.setTvTitle("@我的");
        set_topbar.setOnTopbarClickListenter(this);
        if (Constants.CURRENT_USER==null){finish();return;}
        messageUtils = new MessageUtils(new AtWoAdapter(),choice_bottom,listView_atwo,set_topbar,ATWoActivity.this);
        choice_bottom.setOnClickListener(this);


        adapter = new AtWoAdapter(ATWoActivity.this, false);
        adapter.setUtils(messageUtils);
        messageUtils.getReplyMessageList(adapter);
        adapter.setSet_topbar(set_topbar);
        listView_atwo.setAdapter(adapter);

    }

    @Override
    public void leftClick() {
     finish();
    }

    @Override
    public void rightClick() {
        String  text = set_topbar.getTv_right().getText().toString();
        if("完成".equals(text)){
            messageUtils.updateData("勾选",false,View.GONE);
        }else   if("勾选".equals(text)){
            messageUtils.updateData("完成",true,View.VISIBLE);
        }else  if("删除".equals(text)){
            messageUtils.deleteMessage(messageUtils,adapter.ids,adapter.delete);

        }
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        messageUtils.updateData("勾选",false,View.GONE);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choice_bottom:
                messageUtils.deleteAllMessage(3,messageUtils);
                break;
        }
    }
}
