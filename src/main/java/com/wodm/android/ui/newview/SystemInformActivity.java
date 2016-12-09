package com.wodm.android.ui.newview;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;


import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.SystemInformAdapter;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.utils.MessageUtils;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

@Layout(R.layout.activity_system_inform)
public class SystemInformActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter,View.OnClickListener{
    @ViewIn(R.id.set_topbar)
    AtyTopLayout set_topbar;
    @ViewIn(R.id.choice_bottom)
    LinearLayout choice_bottom;
    @ViewIn(R.id.gridView_SystemInfo)
    ListView gridView_SystemInfo;
    SystemInformAdapter adapter;
   MessageUtils messageUtils;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_topbar.setOnTopbarClickListenter(this);
        choice_bottom.setOnClickListener(this);
        if (Constants.CURRENT_USER==null){finish();return;}
        messageUtils = new MessageUtils(new SystemInformAdapter(),choice_bottom,gridView_SystemInfo,set_topbar,SystemInformActivity.this);



//        adapter = new SystemInformAdapter(this, false);
//        adapter.setUtils(messageUtils);
//        messageUtils.getSystemMessageList(adapter);
//        adapter.setSet_topbar(set_topbar);
//        gridView_SystemInfo.setAdapter(adapter);

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


    @Override
    protected void onResume() {
        super.onResume();
        messageUtils.updateData("勾选",false,View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choice_bottom:
//                adapter.setRemoveAll(true);
                messageUtils.deleteAllMessage(1,messageUtils);
                break;
        }
    }

}
