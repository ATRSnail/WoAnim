package com.wodm.android.ui.newview;

import android.os.Bundle;

import com.wodm.R;
import com.wodm.android.ui.AppActivity;
import com.wodm.android.view.newview.AtyTopLayout;

import org.eteclab.base.annotation.Layout;
import org.eteclab.base.annotation.ViewIn;

/**
 * Created by songchenyu on 16/9/27.
 */
@Layout(R.layout.new_aty_resita)
public class ResingeActivity extends AppActivity implements AtyTopLayout.myTopbarClicklistenter{
    @ViewIn(R.id.set_topbar)
    private AtyTopLayout atyTopLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        atyTopLayout.setOnTopbarClickListenter(this);
        atyTopLayout.setRightIsVisible(false);
    }

    @Override
    public void leftClick() {
        finish();
    }

    @Override
    public void rightClick() {

    }
}
