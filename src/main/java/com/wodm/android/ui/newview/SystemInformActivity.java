package com.wodm.android.ui.newview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.MessageCenterAdapter;
import com.wodm.android.adapter.newadapter.SystemInformAdapter;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.json.JSONObject;

@Layout(R.layout.activity_system_inform)
public class SystemInformActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter,View.OnClickListener {
    @ViewIn(R.id.set_topbar)
    AtyTopLayout set_topbar;
    @ViewIn(R.id.choice_bottom)
    LinearLayout choice_bottom;
    @ViewIn(R.id.gridView_SystemInfo)
    ListView gridView_SystemInfo;
    SystemInformAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_topbar.setOnTopbarClickListenter(this);
        adapter = new SystemInformAdapter(this, false);
        adapter.setSet_topbar(set_topbar);
        gridView_SystemInfo.setAdapter(adapter);
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
            deleteMessage();
            updateData("删除",true,View.VISIBLE);
        }




    }

    private void deleteMessage() {
        if (Constants.CURRENT_USER==null){finish();return;}
        adapter.getData();
        String url = Constants.DELETEMESSAGE+Constants.CURRENT_USER.getData().getAccount().getId()+"&ids=";
        httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }

            @Override
            public void doRequestFailure(Exception exception, String msg) {
                super.doRequestFailure(exception, msg);
            }
        });
    }

    public  void updateData(String string,boolean flag,int visible){
        choice_bottom.setVisibility(visible);
        set_topbar.setTvRight(string);
        adapter = new SystemInformAdapter(this, flag);
        adapter.setSet_topbar(set_topbar);
        adapter.notifyDataSetChanged();
        gridView_SystemInfo.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.choice_bottom:
                deleteAllMessage();
                updateData("勾选",false,View.GONE);
                break;
        }
    }

    private void deleteAllMessage() {
        if (Constants.CURRENT_USER==null){finish();return;}
        String url = Constants.DELETEALLMESSAGE+Constants.CURRENT_USER.getData().getAccount().getId()+"&type=";
        httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
            }

            @Override
            public void doAuthFailure(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthFailure(result, obj);
            }

            @Override
            public void doRequestFailure(Exception exception, String msg) {
                super.doRequestFailure(exception, msg);
            }
        });
    }
}
