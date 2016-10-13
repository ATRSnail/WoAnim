package com.wodm.android.ui.newview;

import android.os.Bundle;
import android.widget.ListView;

import com.wodm.R;
import com.wodm.android.adapter.newadapter.MyDealAdapter;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

/**
 * Created by songchenyu on 16/10/11.
 */
@Layout(R.layout.aty_my_medal)
public class MyMedalActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter {
    @ViewIn(R.id.back_achieve)
    AtyTopLayout atyTopLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        atyTopLayout.setOnTopbarClickListenter(this);
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }
}
