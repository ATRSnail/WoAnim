package com.wodm.android.ui.newview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.wodm.R;
import com.wodm.android.adapter.newadapter.MessageCenterAdapter;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;
import com.wodm.android.view.newview.MyGridView;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

@Layout(R.layout.activity_message_center)
public class MessageCenterActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter{
    @ViewIn(R.id.set_topbar)
    AtyTopLayout set_topbar;
    @ViewIn(R.id.gridView_eMessageCenter)
    GridView gridView_eMessageCenter;
    MessageCenterAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        set_topbar.setOnTopbarClickListenter(this);
        adapter = new MessageCenterAdapter(this);
        gridView_eMessageCenter.setAdapter(adapter);
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }
}
