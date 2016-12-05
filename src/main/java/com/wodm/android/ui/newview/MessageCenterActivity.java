package com.wodm.android.ui.newview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.lidroid.xutils.http.ResponseInfo;
import com.wodm.R;
import com.wodm.android.Constants;
import com.wodm.android.adapter.newadapter.MessageCenterAdapter;
import com.wodm.android.bean.XiaoXiBean;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;
import com.wodm.android.view.newview.MyGridView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;
import org.eteclab.base.http.HttpCallback;
import org.json.JSONObject;

@Layout(R.layout.activity_message_center)
public class MessageCenterActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter{
    @ViewIn(R.id.set_topbar)
    AtyTopLayout set_topbar;
    @ViewIn(R.id.gridView_eMessageCenter)
    ListView gridView_eMessageCenter;
    MessageCenterAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_topbar.setOnTopbarClickListenter(this);
    }

    private void getData() {
        if (Constants.CURRENT_USER==null){finish();return;}
        adapter = new MessageCenterAdapter(this);
        String url=Constants.GETMESSAGELIST+Constants.CURRENT_USER.getData().getAccount().getId();
        httpGet(url,new HttpCallback(){
            @Override
            public void doAuthSuccess(ResponseInfo<String> result, JSONObject obj) {
                super.doAuthSuccess(result, obj);
                XiaoXiBean bean = new Gson().fromJson(obj.toString(),XiaoXiBean.class);
                adapter.setList(bean.getData());
                gridView_eMessageCenter.setAdapter(adapter);
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

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }
}
