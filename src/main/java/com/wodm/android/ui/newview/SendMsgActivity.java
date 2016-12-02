package com.wodm.android.ui.newview;

import android.os.Bundle;
import android.view.View;

import com.wodm.R;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;

@Layout(R.layout.activity_send_msg)
public class SendMsgActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter,
        View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initDatas();
    }

    private void initDatas() {

    }

    private void initViews() {
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void leftClick() {

    }

    @Override
    public void rightClick() {

    }
}
